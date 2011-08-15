package com.liongrid.infectosaurus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ISurfaceAnimation extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceRenderThread myThread;
	private Bitmap bmp;
	private SurfaceHolder holder;
	private Paint paint;

	public ISurfaceAnimation(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ISurfaceAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ISurfaceAnimation(Context context){
		super(context);
		init();
	}

	private void init() {
		holder = getHolder();
		holder.addCallback(this);
		paint = new Paint();            
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		myThread = new SurfaceRenderThread(holder, this);
		myThread.setFlag(true);
		myThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;

	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		super.onDraw(canvas);                   
		canvas.drawBitmap(bmp, 0, 0, paint);

	}

	class SurfaceRenderThread extends Thread {         
		boolean flag ;
		SurfaceHolder myHolder;
		ISurfaceAnimation myDraw;
		public SurfaceRenderThread(SurfaceHolder holder , ISurfaceAnimation drawMain) {
			myHolder = holder;
			myDraw = drawMain;
		}

		public void setFlag (boolean myFlag) {
			flag = myFlag;
		}
		@Override
		public void run(){
			Canvas canvas = null;
			while(flag) {
				try {
					canvas = myHolder.lockCanvas(null);
					myDraw.onDraw(canvas);
				}
				finally{
					if(canvas != null){
						myHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}


}
