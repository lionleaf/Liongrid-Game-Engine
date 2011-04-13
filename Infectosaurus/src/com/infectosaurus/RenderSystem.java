package com.infectosaurus;


public class RenderSystem {
	private ObjectHandler[] renderQueues;
	private int queueIndex;
	private ObjectHandler[] renderBGQueues;
	private ObjectPool<RenderElement> rElementPool;
	
	
	private final static int QUEUE_SIZE = 64;
	private final static int DRAW_QUEUE_COUNT = 2;
    private final static int MAX_BG_TILES = 128;
	
    public RenderSystem() {
        renderQueues = new ObjectHandler[DRAW_QUEUE_COUNT];
        renderBGQueues = new ObjectHandler[DRAW_QUEUE_COUNT];
        for (int i = 0; i < DRAW_QUEUE_COUNT; i++) {
            renderQueues[i] = new ObjectHandler(QUEUE_SIZE);
            renderBGQueues[i] = new ObjectHandler(MAX_BG_TILES);
        }
        
        int poolSize = (QUEUE_SIZE+MAX_BG_TILES) * 2;
        rElementPool = new ObjectPool<RenderElement>(poolSize, RenderElement.class);
        
        queueIndex = 0;
    }
    
    public void scheduleForDraw(Drawable object, Vector2 pos) {
        RenderElement element = rElementPool.allocate();
        if(element == null) return;
        //Since this is done a lot, we want max speed, so we change
        //the public variables instead of calling set
        element.drawable = object;
        element.x = pos.x;
        element.y = pos.y;   	
    	renderQueues[queueIndex].add(element);
    }
    
    public void scheduleForBGDraw(Drawable object, int x, int y) {
    	RenderElement element = rElementPool.allocate();
        
    	//Since this is done a lot, we want max speed, so we change
        //the public variables instead of calling set
        element.drawable = object;
        element.x = x;
        element.y = y;  
    	
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
		RenderElement rElement;
		for (int i = count - 1; i >= 0; i--) {
			rElement = (RenderElement) objects.removeLast();
			rElementPool.release(rElement);
		}
	}
}
