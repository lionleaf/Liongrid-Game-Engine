package com.infectosaurus;


public class RenderSystem {
	private ObjectHandler<RenderElement>[] renderQueues;
	private int queueIndex;
	private ObjectPool<RenderElement> rElementPool;
	
	//TODO tie to max size of GOHandler!!
	private final static int QUEUE_SIZE = 256;
	private final static int DRAW_QUEUE_COUNT = 2;
    private final static int MAX_BG_TILES = 128;
	
    public RenderSystem() {
        renderQueues = new ObjectHandler[DRAW_QUEUE_COUNT];
        for (int i = 0; i < DRAW_QUEUE_COUNT; i++) {
            renderQueues[i] = new ObjectHandler<RenderElement>(QUEUE_SIZE);
            renderQueues[i].objects.setComparator(RenderElement.comparer);
        }
        
        int poolSize = (QUEUE_SIZE+MAX_BG_TILES) * 6;
        rElementPool = new ObjectPool<RenderElement>(poolSize, RenderElement.class);
        
        queueIndex = 0;
    }
    
    public void scheduleForDraw(Drawable object, Vector2 pos) {
        RenderElement element = rElementPool.allocate();
        if(element == null) return;
        
        //Check if element is outside the screen view
        if(pos.x < Camera.cameraPosition[0])
        	if(pos.x > Camera.cameraPosition[0] + Camera.screenWidth)
        		return;
        if(pos.y < Camera.cameraPosition[1])
        	if(pos.x > Camera.cameraPosition[1] + Camera.screenHeight)
        		return;
        
        
        //Since this is done a lot, we want max speed, so we change
        //the public variables instead of calling set
        element.drawable = object;
        element.x = pos.x;
        element.y = pos.y;   	
    	renderQueues[queueIndex].add(element);
    }
    
    public void scheduleForBGDraw(Drawable object, int x, int y) {
    	RenderElement element = rElementPool.allocate();
        if(element == null) return;
    	//Since this is done a lot, we want max speed, so we change
        //the public variables instead of calling set
        element.drawable = object;
        element.x = x;
        element.y = y;  
    	
    }
    
    public void swap(RenderingThread renderer) {
        renderQueues[queueIndex].commitUpdates();

        // This code will block if the previous queue is still being executed.
        renderer.setDrawQueue(renderQueues[queueIndex]);
        
        final int lastQueue = (queueIndex == 0) ? DRAW_QUEUE_COUNT - 1 : queueIndex - 1;
        
        //Clear the old queues.
        FixedSizeArray<RenderElement> objects;
        objects = renderQueues[lastQueue].getObjects();
        clearQueue(objects, renderQueues);
        
        queueIndex = (queueIndex + 1) % DRAW_QUEUE_COUNT;
    }

	private void clearQueue(FixedSizeArray<RenderElement> objects, ObjectHandler[] queue) {
		final int count = objects.getCount();
		RenderElement rElement;
		
		for (int i = count - 1; i >= 0; i--) {
			rElement = (RenderElement) objects.removeLast();
			rElementPool.release(rElement);
		}
	}
}
