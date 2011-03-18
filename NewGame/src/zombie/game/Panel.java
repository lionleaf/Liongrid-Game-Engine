package zombie.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	private InfectaThread mThread;
	private int mX;
	private int mY;
	private Bitmap mBitmap;
	private Bitmap fBitmap;
	
	public void doDraw(Canvas canvas) {
	    canvas.drawBitmap(fBitmap, 0, 0, null);
	    
	    int fWidth = fBitmap.getWidth();
	    int fHeight = fBitmap.getHeight();
	    
	    int tilesX = getWidth()/fWidth;
	    int tilesY = getHeight()/fHeight;
	    
	    for(int i = 0; i < tilesX; i++){
	    	for(int j = 0; j < tilesY; j++){
	    		canvas.drawBitmap(fBitmap, i*fWidth + 1, j*fHeight + 1, null);
	    	}
	    }
	    canvas.drawBitmap(mBitmap, mX, mY, null);
	}
	
	public Panel(Context context) {
	    super(context);
	    mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
	    fBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stonefloor);
	    
	    getHolder().addCallback(this);
	    mThread = new InfectaThread(this);
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
        mX = (int) event.getX() - mBitmap.getWidth() / 2;
        mY = (int) event.getY() - mBitmap.getHeight() / 2;
        return super.onTouchEvent(event);
    }
}