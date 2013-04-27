package abs.android.live.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;


public class AnimHelper{
	private final static int ANIM_DURATION = 350;
	private AnimListener mAnimListener;
	public void initCardRotateViewe(View frontView, View behindView){
		if (mAnimListener == null){
			mAnimListener = new AnimListener(frontView, behindView);
		}
	}
    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    public void applyRotationFirst(View view, float start, float end, boolean reversed) {
        // Find the center of the container
        final float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, reversed);
        rotation.setDuration(ANIM_DURATION);
        //rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(mAnimListener);

        view.startAnimation(rotation);
    }
    
	  /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class AnimListener implements Animation.AnimationListener {
        private final View mFrontView;
        private final View mBehindView;
        private boolean mViewReversed = false;
        
        private AnimListener(View frontView, View behindView) {
        	mFrontView = frontView;
        	mBehindView = behindView;
        }

        public void onAnimationStart(Animation animation) {
        	
        }

        public void onAnimationEnd(Animation animation) {
        	if (!mViewReversed){
        		mFrontView.setVisibility(View.INVISIBLE);
        		mBehindView.setVisibility(View.VISIBLE);
        		applyRotationSecond(mBehindView, (Integer)mBehindView.getTag(), 0, false);
        		mViewReversed = true;
        	}else{
        		mFrontView.setVisibility(View.VISIBLE);
        		mBehindView.setVisibility(View.INVISIBLE);
        		applyRotationSecond(mFrontView, (Integer)mFrontView.getTag(), 0, false);
        		mViewReversed = false;
        	}
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }
    
    
    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    private void applyRotationSecond(View view, float start, float end, boolean reversed) {
        // Find the center of the container
        final float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, reversed);
        rotation.setDuration(ANIM_DURATION);
        rotation.setInterpolator(new AccelerateInterpolator());

        view.startAnimation(rotation);
    }
}