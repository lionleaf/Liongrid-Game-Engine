package com.infectosaurus;

import android.util.Log;

public class RenderSystem {
	private ObjectHandler[] renderQueues;
	private int queueIndex;
	
	private final static int DRAW_QUEUE_COUNT = 2;
    
    public RenderSystem() {
        renderQueues = new ObjectHandler[DRAW_QUEUE_COUNT];
        for (int i = 0; i < DRAW_QUEUE_COUNT; i++) {
            renderQueues[i] = new ObjectHandler();
        }
        queueIndex = 0;
    }
    
    public void scheduleForDraw(Drawable object, Vector2 pos) {
        RenderElement element = new RenderElement(object, pos);
        //TODO POOL THIS SHIT!    	
    	renderQueues[queueIndex].add(element);
    }
    
    public void swap(RenderingThread renderer) {
       //renderQueues[queueIndex].commitUpdates();

        // This code will block if the previous queue is still being executed.
        renderer.setDrawQueue(renderQueues[queueIndex]);
        int count = renderQueues[queueIndex].getObjects().getCount();
        if(renderQueues[queueIndex].getObjects().get(count-1) == null){
        	Log.d("RSys","SHIT! Sent null to renderer");
        }
        final int lastQueue = (queueIndex == 0) ? DRAW_QUEUE_COUNT - 1 : queueIndex - 1;

        // Clear the old queue.
        FixedSizeArray<BaseObject> objects = renderQueues[lastQueue].getObjects();
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
