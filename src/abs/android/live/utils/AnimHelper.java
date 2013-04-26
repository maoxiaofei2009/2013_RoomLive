package abs.android.live.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;


public class AnimHelper{
	private final static int ANIM_DURATION = 600;
	public static enum AnimType{
		SCALE_IN,
		SCALE_OUT,
		TRANS_UP,
		TRANS_DOWN
	}
	
	public static void startTransferAnim(View view, AnimType type){
		float fromYValue = 0;
		float toYValue = 0;
		if (type == AnimType.TRANS_UP){
			fromYValue = 1.0f;
			toYValue = 0.0f;
		}else if(type == AnimType.TRANS_DOWN){
			fromYValue = 0.0f;
			toYValue = 1.0f;
		}	
		
		TranslateAnimation transAnim = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, fromYValue, 
				Animation.RELATIVE_TO_SELF, toYValue);
		
		transAnim.setInterpolator(new AccelerateInterpolator()); 
		AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(transAnim);  
        animSet.setFillAfter(true);
        animSet.setDuration(ANIM_DURATION);
        
        view.startAnimation(animSet);
		
	}
	
	
	public static class Rotate3d extends Animation {
		private final float mFromDegrees;
		private final float mToDegrees;
		private final float mCenterX;
		private final float mCenterY;
		private final float mDepthZ;
		private final boolean mReverse;
		private Camera mCamera;

		public Rotate3d(float fromDegrees, float toDegrees, float centerX,
				float centerY, float depthZ, boolean reverse) {
			mFromDegrees = fromDegrees;
			mToDegrees = toDegrees;
			mCenterX = centerX;
			mCenterY = centerY;
			mDepthZ = depthZ;
			mReverse = reverse;
		}

		@Override
		public void initialize(int width, int height, int parentWidth,
				int parentHeight) {
			super.initialize(width, height, parentWidth, parentHeight);
			mCamera = new Camera();
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			final float fromDegrees = mFromDegrees;
			float degrees = fromDegrees
					+ ((mToDegrees - fromDegrees) * interpolatedTime);
			final float centerX = mCenterX;
			final float centerY = mCenterY;
			final Camera camera = mCamera;
			final Matrix matrix = t.getMatrix();
			camera.save();
			if (mReverse) {
				camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
			} else {
				camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
			}

			camera.rotateY(degrees);
			camera.getMatrix(matrix);
			camera.restore();

			//matrix.preTranslate(-centerX, -centerY);
			//matrix.postTranslate(centerX, centerY);
			matrix.preTranslate(-centerX, -centerY);
			matrix.postTranslate(centerX, centerY);
		}
	}
}