package abs.android.live.utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GestrueHelper implements OnTouchListener, OnGestureListener{
	public enum FLING_DIRECTION{
		LEFT,
		RIGHT
	};
	
	public interface FlingCallback{
		public void onFling(FLING_DIRECTION direction);
	}
	
	private static final int FLING_MIN_DISTANCE = 120 * 2;
	private static final int FLING_THRESHOLD_VELOCITY = 200 * 2;
	private GestureDetector mGuesture;
	private FlingCallback mFlingCallback;
	
	public GestrueHelper(Context context){
		mGuesture = new GestureDetector(context, this);
	}
	
	
	
	/**
	 * Set the View which detect the Gestrue
	 * @param view
	 */
	public void setFlingCallback(View view, FlingCallback callback) {
		if (view != null) {
			view.setOnTouchListener(this);
			mFlingCallback = callback;
		}
	}
	
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		mGuesture.onTouchEvent(event);
		return true;
	}
	
	@Override
	public boolean onDown(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_THRESHOLD_VELOCITY) {
			if (mFlingCallback != null){
				mFlingCallback.onFling(FLING_DIRECTION.RIGHT);
			}
		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_THRESHOLD_VELOCITY) {
			if (mFlingCallback != null){
				mFlingCallback.onFling(FLING_DIRECTION.LEFT);
			}
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	
}