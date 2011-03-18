package com.infectosaurus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = "MyActivity";
	private InfectaThread mThread;
	private Bitmap fBitmap;
	public GraphicsObject infector;
	public int color;
	
	
	public Panel(Context context) {
	    super(context);
	    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
	    infector = new GraphicsObject(bitmap);
	    fBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stonefloor);
	    color = Color.BLUE;
	    
	    getHolder().addCallback(this);
	    mThread = new InfectaThread(this);
	}
	
	public void doDraw(Canvas canvas) {
	    // Draws background
	    int fWidth = fBitmap.getWidth();
	    int fHeight = fBitmap.getHeight();
	    int tilesX = getWidth()/fWidth;
	    int tilesY = getHeight()/fHeight;
    	for(int i = 0; i < tilesX; i++){
	    	for(int j = 0; j < tilesY; j++){
	    		canvas.drawBitmap(fBitmap, i*fWidth + 1, j*fHeight + 1, null);
	    	}
	    }
    	// end
    	Bitmap bitmap = infector.getBitmap();
    	Coordinates coor = infector.getCoordinates();
    	canvas.drawBitmap(bitmap, 
    					  coor.getX() - bitmap.getWidth()/2, 
    					  coor.getY() - bitmap.getHeight()/2, null);
    	Paint mp = new Paint();
    	mp.setStrokeWidth(3);
    	mp.setColor(color);
    	canvas.drawCircle(getWidth()/2, getHeight()/2, 10, mp);
	}
	 
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    if (!mThread.isAlive()) {
	        mThread = new InfectaThread(this);
	        mThread.setRunning(true);
	        mThread.start();
	    }
	}
	 
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	    if (mThread.isAlive()) {
	        mThread.setRunning(false);
	    }
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	Coordinates coor = infector.getCoordinates();
    	coor.setX((int) event.getX());
    	coor.setY((int) event.getY());
    	mThread.doTouchEvent(event);
        return super.onTouchEvent(event);
    }
}