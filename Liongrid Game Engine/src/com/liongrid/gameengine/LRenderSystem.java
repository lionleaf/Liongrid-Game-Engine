package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.gameengine.tools.LObjectPool;
import com.liongrid.gameengine.tools.LVector2;


public class LRenderSystem {
	private LObjectHandler<LRenderElement>[] renderQueues;
	private int queueIndex;
	private LObjectPool<LRenderElement> rElementPool;
	
	//TODO tie to max size of GOHandler!!
	private final static int QUEUE_SIZE = 256;
	private final static int DRAW_QUEUE_COUNT = 2;
    private final static int MAX_BG_TILES = 128;
	
    public LRenderSystem() {
        renderQueues = new LObjectHandler[DRAW_QUEUE_COUNT];
        for (int i = 0; i < DRAW_QUEUE_COUNT; i++) {
            renderQueues[i] = new LObjectHandler<LRenderElement>(QUEUE_SIZE);
            renderQueues[i].objects.setComparator(LRenderElement.comparer);
        }
        
        int poolSize = (QUEUE_SIZE+MAX_BG_TILES) * 6;
        rElementPool = new LObjectPool<LRenderElement>(poolSize, LRenderElement.class);
        
        queueIndex = 0;
    }
    
    /**
     * @deprecated 
     * use public void scheduleForDraw(LDrawableObject object, float x, float y) 
     * @param object
     * @param pos
     */
    @Deprecated
	public void scheduleForDraw(LDrawableObject object, LVector2 pos, 
    		boolean cameraRelative) {
    	scheduleForDraw(object, pos.x, pos.y, cameraRelative);
    }
    /**
     * @param object - the drawable object to be drawn.
     * @param x - position x in pixels
     * @param y - position y in pixels
     * @param cameraRelative - True sets the drawable a fixed position on the camera.
     */
    public void scheduleForDraw(LDrawableObject object, float x, float y,
		 boolean cameraRelative){
    	scheduleForDraw(object, x, y, cameraRelative, 1f, -1,-1);
    }
    
    /**
     * @param object - the drawable object to be drawn.
     * @param x - position x in pixels
     * @param y - position y in pixels
     * @param cameraRelative - True sets the drawable a fixed position on the camera.
     * @param scale - scale factor
     * @param width - width in pixels
     * @param height - height in pixels
     */
    public void scheduleForDraw(LDrawableObject object, float x, float y,
		 boolean cameraRelative, float scale, int width, int height) {
    	
    	if(!cameraRelative && LCamera.cull(x, y, object.getWidth(), object.getHeight())) return;
    	
    	LRenderElement element = rElementPool.allocate();
        if(element == null) return;
        
        //Since this is done a lot, we want max speed, so we change
        //the public variables instead of calling set
        element.scale = scale;
        element.drawable = object;
        element.x = x;
        element.y = y;
        element.cameraRelative = cameraRelative;
    	renderQueues[queueIndex].add(element);
    }
    
    
    public void swap(LRenderingThread renderer) {
        renderQueues[queueIndex].commitUpdates();

        // This code will block if the previous queue is still being executed.
        renderer.setDrawQueue(renderQueues[queueIndex]);
        
        final int lastQueue = (queueIndex == 0) ? DRAW_QUEUE_COUNT - 1 : queueIndex - 1;
        
        //Clear the old queues.
        LFixedSizeArray<LRenderElement> objects;
        objects = renderQueues[lastQueue].getObjects();
        clearQueue(objects, renderQueues);
        
        queueIndex = (queueIndex + 1) % DRAW_QUEUE_COUNT;
    }

	private void clearQueue(LFixedSizeArray<LRenderElement> objects, LObjectHandler[] queue) {
		final int count = objects.getCount();
		LRenderElement rElement;
		
		for (int i = count - 1; i >= 0; i--) {
			rElement = objects.removeLast();
			rElementPool.release(rElement);
		}
	}
}
