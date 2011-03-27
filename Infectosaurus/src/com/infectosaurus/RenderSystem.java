package com.infectosaurus;

import android.util.Log;

public class RenderSystem {
	private ObjectHandler[] renderQueues;
	private int queueIndex;
	private ObjectHandler[] renderBGQueues;
	
	private final static int DRAW_QUEUE_COUNT = 2;
    private final static int MAX_BG_TILES = 128;
	
    public RenderSystem() {
        renderQueues = new ObjectHandler[DRAW_QUEUE_COUNT];
        renderBGQueues = new ObjectHandler[DRAW_QUEUE_COUNT];
        for (int i = 0; i < DRAW_QUEUE_COUNT; i++) {
            renderQueues[i] = new ObjectHandler();
            renderBGQueues[i] = new ObjectHandler(MAX_BG_TILES);
        }
        queueIndex = 0;
    }
    
    public void scheduleForDraw(Drawable object, Vector2 pos) {
        RenderElement element = new RenderElement(object, pos);
        //TODO POOL THIS SHIT!    	
    	renderQueues[queueIndex].add(element);
    }
    
    public void scheduleForBGDraw(DrawableBitmap drawBitmap, int x, int y) {
		RenderElement element = new RenderElement(drawBitmap, x, y);
        //TODO POOL THIS SHIT!    	
    	renderBGQueues[queueIndex].add(element);
	}
    
    public void swap(RenderingThread renderer) {
       //renderQueues[queueIndex].commitUpdates();

        // This code will block if the previous queue is still being executed.
        renderer.setDrawQueues(renderQueues[queueIndex], renderBGQueues[queueIndex]);
        
        final int lastQueue = (queueIndex == 0) ? DRAW_QUEUE_COUNT - 1 : queueIndex - 1;

        // Clear the old queues.
        FixedSizeArray<BaseObject> objects;
        objects = renderQueues[lastQueue].getObjects();
        clearQueue(objects);
        objects = renderBGQueues[lastQueue].getObjects();
        clearQueue(objects);

        queueIndex = (queueIndex + 1) % DRAW_QUEUE_COUNT;
    }

	private void clearQueue(FixedSizeArray<BaseObject> objects) {
		 final int count = objects.getCount();
	     for (int i = count - 1; i >= 0; i--) {
	            objects.removeLast();
	        }
		
	}
}
