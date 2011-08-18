package com.liongrid.gameengine;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class LGestureDetector {
	/**
     * The listener that is used to notify when gestures occur.
     * If you want to listen for all the different gestures then implement
     * this interface. If you only want to listen for a subset it might
     * be easier to extend {@link SimpleOnGestureListener}.
     */
    public interface OnGestureListener {

        /**
         * Notified when a tap occurs with the down {@link MotionEvent}
         * that triggered it. This will be triggered immediately for
         * every down event. All other events should be preceded by this.
         *
         * @param e The down motion event.
         */
        boolean onDown(MotionEvent e);
        
        /**
         * Notified when a tap occurs with the up {@link MotionEvent}
         * that triggered it.
         *
         * @param e The up motion event that completed the first tap
         * @return true if the event is consumed, else false
         */
        boolean onSingleTapUp(MotionEvent ev);
        
        boolean onUp(MotionEvent e);

        /**
         * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
         * current move {@link MotionEvent}. The distance in x and y is also supplied for
         * convenience.
         *
         * @param e1 The first down motion event that started the scrolling.
         * @param e2 The move motion event that triggered the current onScroll.
         * @param distanceX The distance along the X axis that has been scrolled since the last
         *              call to onScroll. This is NOT the distance between {@code e1}
         *              and {@code e2}.
         * @param distanceY The distance along the Y axis that has been scrolled since the last
         *              call to onScroll. This is NOT the distance between {@code e1}
         *              and {@code e2}.
         * @return true if the event is consumed, else false
         */
        boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY);

        /**
         * Notified when a long press occurs with the initial on down {@link MotionEvent}
         * that trigged it.
         *
         * @param e The initial on down motion event that started the longpress.
         */
        void onLongPress(MotionEvent e);

        /**
         * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
         * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
         * the x and y axis in pixels per second.
         *
         * @param e1 The first down motion event that started the fling.
         * @param e2 The move motion event that triggered the current onFling.
         * @param velocityX The velocity of this fling measured in pixels per second
         *              along the x axis.
         * @param velocityY The velocity of this fling measured in pixels per second
         *              along the y axis.
         * @return true if the event is consumed, else false
         */
        boolean onFling(MotionEvent e1, MotionEvent e2,
                float velocityX, float velocityY);

    }

    /**
     * The listener that is used to notify when a double-tap or a confirmed
     * single-tap occur.
     */
    public interface OnDoubleTapListener {
        /**
         * Notified when a single-tap occurs.
         * <p>
         * Unlike {@link OnGestureListener#onSingleTapUp(MotionEvent)}, this
         * will only be called after the detector is confident that the user's
         * first tap is not followed by a second tap leading to a double-tap
         * gesture.
         *
         * @param e The down motion event of the single-tap.
         * @return true if the event is consumed, else false
         */
        boolean onSingleTapConfirmed(MotionEvent e);

        /**
         * Notified when a double-tap occurs.
         *
         * @param e The down motion event of the first tap of the double-tap.
         * @return true if the event is consumed, else false
         */
        boolean onDoubleTap(MotionEvent e);

        /**
         * Notified when an event within a double-tap gesture occurs, including
         * the down, move, and up events.
         *
         * @param e The motion event that occurred during the double-tap gesture.
         * @return true if the event is consumed, else false
         */
        boolean onDoubleTapEvent(MotionEvent e);
    }
    
    public static class LSimpleGestureDetector 
    		implements OnDoubleTapListener, OnGestureListener{

		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}
		
		public boolean onSingleTapUp(MotionEvent ev) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
		 private int mBiggerTouchSlopSquare = 20 * 20;

         private int mTouchSlopSquare;
         private int mDoubleTapSlopSquare;
         private int mMinimumFlingVelocity;
         private int mMaximumFlingVelocity;

         private static final int LONGPRESS_TIMEOUT = ViewConfiguration
                 .getLongPressTimeout();
         private static final int TAP_TIMEOUT = ViewConfiguration
                 .getTapTimeout();
         private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration
                 .getDoubleTapTimeout();

         // constants for Message.what used by GestureHandler below
         private static final int LONG_PRESS = 1;
         private static final int TAP = 2;

         private final Handler mHandler;
         private final OnGestureListener mListener;
         private OnDoubleTapListener mDoubleTapListener;

         private boolean mStillDown;
         private boolean mInLongPress;
         private boolean mAlwaysInTapRegion;
         private boolean mAlwaysInBiggerTapRegion;

         private MotionEvent mCurrentDownEvent;
         private MotionEvent mPreviousUpEvent;

         /**
          * True when the user is still touching for the second tap (down, move, and
          * up events). Can only be true if there is a double tap listener attached.
          */
         private boolean mIsDoubleTapping;

         private float mLastMotionY;
         private float mLastMotionX;

         private boolean mIsLongpressEnabled;

         /**
          * True if we are at a target API level of >= Froyo or the developer can
          * explicitly set it. If true, input events with > 1 pointer will be ignored
          * so we can work side by side with multitouch gesture detectors.
          */
         private boolean mIgnoreMultitouch;

         /**
          * Determines speed during touch scrolling
          */
         private VelocityTracker mVelocityTracker;

         private class GestureHandler extends Handler {
             GestureHandler() {
                 super ();
             }

             GestureHandler(Handler handler) {
                 super (handler.getLooper());
             }

             @Override
             public void handleMessage(Message msg) {
                 switch (msg.what) {
                 
                 case LONG_PRESS:
                     dispatchLongPress();
                     break;

                 case TAP:
                     // If the user's finger is still down, do not count it as a tap
                     if (mDoubleTapListener != null && !mStillDown) {
                         mDoubleTapListener
                                 .onSingleTapConfirmed(mCurrentDownEvent);
                     }
                     break;

                 default:
                     throw new RuntimeException("Unknown message " + msg); //never
                 }
             }
         }

         /**
          * Creates a GestureDetector with the supplied listener.
          * You may only use this constructor from a UI thread (this is the usual situation).
          * @see android.os.Handler#Handler()
          *
          * @param context the application's context
          * @param listener the listener invoked for all the callbacks, this must
          * not be null.
          *
          * @throws NullPointerException if {@code listener} is null.
          */
         public LSimpleGestureDetector(Context context, OnGestureListener listener) {
             this (context, listener, null);
         }

         /**
          * Creates a GestureDetector with the supplied listener.
          * You may only use this constructor from a UI thread (this is the usual situation).
          * @see android.os.Handler#Handler()
          *
          * @param context the application's context
          * @param listener the listener invoked for all the callbacks, this must
          * not be null.
          * @param handler the handler to use     
          *
          * @throws NullPointerException if {@code listener} is null.
          */
         public LSimpleGestureDetector(Context context, OnGestureListener listener,
                 Handler handler) {
             this (
                     context,
                     listener,
                     handler,
                     context != null
                             &&  context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.FROYO);
         }

         /**
          * Creates a GestureDetector with the supplied listener.
          * You may only use this constructor from a UI thread (this is the usual situation).
          * @see android.os.Handler#Handler()
          *
          * @param context the application's context
          * @param listener the listener invoked for all the callbacks, this must
          * not be null.
          * @param handler the handler to use
          * @param ignoreMultitouch whether events involving more than one pointer should
          * be ignored.
          *
          * @throws NullPointerException if {@code listener} is null.
          */
         public LSimpleGestureDetector(Context context, OnGestureListener listener,
                 Handler handler, boolean ignoreMultitouch) {
             if (handler != null) {
                 mHandler = new GestureHandler(handler);
             } else {
                 mHandler = new GestureHandler();
             }
             mListener = listener;
             if (listener instanceof  OnDoubleTapListener) {
                 setOnDoubleTapListener((OnDoubleTapListener) listener);
             }
             init(context, ignoreMultitouch);
         }

         private void init(Context context, boolean ignoreMultitouch) {
        	 if(context == null){
                 throw new NullPointerException(
                 "Context must not be null");
     }
             if (mListener == null) {
                 throw new NullPointerException(
                         "OnGestureListener must not be null");
             }
             mIsLongpressEnabled = true;
             mIgnoreMultitouch = ignoreMultitouch;

             // Fallback to support pre-donuts releases
             int touchSlop, doubleTapSlop;
             final ViewConfiguration configuration = ViewConfiguration
                     .get(context);
             touchSlop = configuration.getScaledTouchSlop();
             doubleTapSlop = configuration.getScaledDoubleTapSlop();
             mMinimumFlingVelocity = configuration
                     .getScaledMinimumFlingVelocity();
             mMaximumFlingVelocity = configuration
                     .getScaledMaximumFlingVelocity();
             mTouchSlopSquare = touchSlop * touchSlop;
             mDoubleTapSlopSquare = doubleTapSlop * doubleTapSlop;
         }

         /**
          * Sets the listener which will be called for double-tap and related
          * gestures.
          * 
          * @param onDoubleTapListener the listener invoked for all the callbacks, or
          *        null to stop listening for double-tap gestures.
          */
         public void setOnDoubleTapListener(
                 OnDoubleTapListener onDoubleTapListener) {
             mDoubleTapListener = onDoubleTapListener;
         }

         /**
          * Set whether longpress is enabled, if this is enabled when a user
          * presses and holds down you get a longpress event and nothing further.
          * If it's disabled the user can press and hold down and then later
          * moved their finger and you will get scroll events. By default
          * longpress is enabled.
          *
          * @param isLongpressEnabled whether longpress should be enabled.
          */
         public void setIsLongpressEnabled(boolean isLongpressEnabled) {
             mIsLongpressEnabled = isLongpressEnabled;
         }

         /**
          * @return true if longpress is enabled, else false.
          */
         public boolean isLongpressEnabled() {
             return mIsLongpressEnabled;
         }

         /**
          * Analyzes the given motion event and if applicable triggers the
          * appropriate callbacks on the {@link OnGestureListener} supplied.
          *
          * @param ev The current motion event.
          * @return true if the {@link OnGestureListener} consumed the event,
          *              else false.
          */
         public boolean onTouchEvent(MotionEvent ev) {
             final int action = ev.getAction();
             final float y = ev.getY();
             final float x = ev.getX();

             if (mVelocityTracker == null) {
                 mVelocityTracker = VelocityTracker.obtain();
             }
             mVelocityTracker.addMovement(ev);

             boolean handled = false;

             switch (action & MotionEvent.ACTION_MASK) {
             case MotionEvent.ACTION_POINTER_DOWN:
                 if (mIgnoreMultitouch) {
                     // Multitouch event - abort.
                     cancel();
                 }
                 break;

             case MotionEvent.ACTION_POINTER_UP:
                 // Ending a multitouch gesture and going back to 1 finger
                 if (mIgnoreMultitouch && ev.getPointerCount() == 2) {
                     int index = (((action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT) == 0) ? 1
                             : 0;
                     mLastMotionX = ev.getX(index);
                     mLastMotionY = ev.getY(index);
                     mVelocityTracker.recycle();
                     mVelocityTracker = VelocityTracker.obtain();
                 }
                 break;

             case MotionEvent.ACTION_DOWN:
                 if (mDoubleTapListener != null) {
                     boolean hadTapMessage = mHandler.hasMessages(TAP);
                     if (hadTapMessage)
                         mHandler.removeMessages(TAP);
                     if ((mCurrentDownEvent != null)
                             && (mPreviousUpEvent != null)
                             && hadTapMessage
                             && isConsideredDoubleTap(mCurrentDownEvent,
                                     mPreviousUpEvent, ev)) {
                         // This is a second tap
                         mIsDoubleTapping = true;
                         // Give a callback with the first tap of the double-tap
                         handled |= mDoubleTapListener
                                 .onDoubleTap(mCurrentDownEvent);
                         // Give a callback with down event of the double-tap
                         handled |= mDoubleTapListener.onDoubleTapEvent(ev);
                     } else {
                         // This is a first tap
                         mHandler.sendEmptyMessageDelayed(TAP,
                                 DOUBLE_TAP_TIMEOUT);
                     }
                 }

                 mLastMotionX = x;
                 mLastMotionY = y;
                 if (mCurrentDownEvent != null) {
                     mCurrentDownEvent.recycle();
                 }
                 mCurrentDownEvent = MotionEvent.obtain(ev);
                 mAlwaysInTapRegion = true;
                 mAlwaysInBiggerTapRegion = true;
                 mStillDown = true;
                 mInLongPress = false;

                 if (mIsLongpressEnabled) {
                     mHandler.removeMessages(LONG_PRESS);
                     mHandler.sendEmptyMessageAtTime(LONG_PRESS,
                             mCurrentDownEvent.getDownTime() + TAP_TIMEOUT
                                     + LONGPRESS_TIMEOUT);
                 }
                 handled |= mListener.onDown(ev);
                 break;

             case MotionEvent.ACTION_MOVE:
                 if (mInLongPress
                         || (mIgnoreMultitouch && ev.getPointerCount() > 1)) {
                     break;
                 }
                 final float scrollX = mLastMotionX - x;
                 final float scrollY = mLastMotionY - y;
                 if (mIsDoubleTapping) {
                     // Give the move events of the double-tap
                     handled |= mDoubleTapListener.onDoubleTapEvent(ev);
                 } else if (mAlwaysInTapRegion) {
                     final int deltaX = (int) (x - mCurrentDownEvent.getX());
                     final int deltaY = (int) (y - mCurrentDownEvent.getY());
                     int distance = (deltaX * deltaX) + (deltaY * deltaY);
                     if (distance > mTouchSlopSquare) {
                         handled = mListener.onScroll(mCurrentDownEvent, ev,
                                 scrollX, scrollY);
                         mLastMotionX = x;
                         mLastMotionY = y;
                         mAlwaysInTapRegion = false;
                         mHandler.removeMessages(TAP);
                         mHandler.removeMessages(LONG_PRESS);
                     }
                     if (distance > mBiggerTouchSlopSquare) {
                         mAlwaysInBiggerTapRegion = false;
                     }
                 } else if ((Math.abs(scrollX) >= 1)
                         || (Math.abs(scrollY) >= 1)) {
                     handled = mListener.onScroll(mCurrentDownEvent, ev,
                             scrollX, scrollY);
                     mLastMotionX = x;
                     mLastMotionY = y;
                 }
                 break;

             case MotionEvent.ACTION_UP:
                 mStillDown = false;
                 MotionEvent currentUpEvent = MotionEvent.obtain(ev);
                 handled |= mListener.onUp(ev);
                 if (mIsDoubleTapping) {
                     // Finally, give the up event of the double-tap
                     handled |= mDoubleTapListener.onDoubleTapEvent(ev);
                 } else if (mInLongPress) {
                     mHandler.removeMessages(TAP);
                     mInLongPress = false;
                 } else if (mAlwaysInTapRegion) {
                     handled |= mListener.onSingleTapUp(ev);
                 } else {

                     // A fling must travel the minimum tap distance
                     final VelocityTracker velocityTracker = mVelocityTracker;
                     velocityTracker.computeCurrentVelocity(1000,
                             mMaximumFlingVelocity);
                     final float velocityY = velocityTracker.getYVelocity();
                     final float velocityX = velocityTracker.getXVelocity();

                     if ((Math.abs(velocityY) > mMinimumFlingVelocity)
                             || (Math.abs(velocityX) > mMinimumFlingVelocity)) {
                         handled = mListener.onFling(mCurrentDownEvent, ev,
                                 velocityX, velocityY);
                     }
                 }
                 if (mPreviousUpEvent != null) {
                     mPreviousUpEvent.recycle();
                 }
                 // Hold the event we obtained above - listeners may have changed the original.
                 mPreviousUpEvent = currentUpEvent;
                 mVelocityTracker.recycle();
                 mVelocityTracker = null;
                 mIsDoubleTapping = false;
                 mHandler.removeMessages(LONG_PRESS);
                 break;
             case MotionEvent.ACTION_CANCEL:
                 cancel();
             }
             return handled;
         }

         private void cancel() {
             mHandler.removeMessages(LONG_PRESS);
             mHandler.removeMessages(TAP);
             mVelocityTracker.recycle();
             mVelocityTracker = null;
             mIsDoubleTapping = false;
             mStillDown = false;
             if (mInLongPress) {
                 mInLongPress = false;
             }
         }

         private boolean isConsideredDoubleTap(MotionEvent firstDown,
                 MotionEvent firstUp, MotionEvent secondDown) {
             if (!mAlwaysInBiggerTapRegion) {
                 return false;
             }

             if (secondDown.getEventTime() - firstUp.getEventTime() > DOUBLE_TAP_TIMEOUT) {
                 return false;
             }

             int deltaX = (int) firstDown.getX() - (int) secondDown.getX();
             int deltaY = (int) firstDown.getY() - (int) secondDown.getY();
             return (deltaX * deltaX + deltaY * deltaY < mDoubleTapSlopSquare);
         }

         private void dispatchLongPress() {
             mHandler.removeMessages(TAP);
             mInLongPress = true;
             mListener.onLongPress(mCurrentDownEvent);
         }
		
    }
}
