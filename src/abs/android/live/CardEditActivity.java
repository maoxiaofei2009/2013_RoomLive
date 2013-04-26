package abs.android.live;

import abs.android.live.utils.LogUtils;
import abs.android.live.utils.ResolutionManager;
import abs.android.live.utils.Rotate3dAnimation;
import abs.android.live.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CardEditActivity extends BaseActivity {
	private static final String TAG = "CardEditActivity";
	private static final float RATIO_WIDTH_TO_HEIGHT = 1.5f;
	private View mRootView;
	private View mContainer;
	private ViewGroup mCardFrontView;
	private ViewGroup mCardBehindView;
	private DisplayNextView mDisplayNextView;
	private int mContainerWidth = 0;
	private int mContainerHeight = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        
        initUI();
    }
    
    private void initUI(){
    	mRootView = findViewById(R.id.card_root);
    	mContainer = findViewById(R.id.card_view);
    	mContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCardFrontView.getVisibility() == View.VISIBLE){
					applyRotation(mCardFrontView, 0, 90, true);
				}else{
					applyRotation(mCardBehindView, 0, 90, true);
				}
			}
		});
    	mCardFrontView = (ViewGroup) findViewById(R.id.card_front_page);
    	mCardFrontView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    	mCardBehindView = (ViewGroup) findViewById(R.id.card_behind_page);
    	mCardBehindView.setVisibility(View.INVISIBLE);
    	mCardBehindView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    	adjustCardArea(mRootView, mContainer, this);
    	adjustCardContent();
    	
    	
    	mDisplayNextView = new DisplayNextView(mCardFrontView, mCardBehindView);
    }
    
    
	/**
	 * scale the DmcFileView with the same ratio with 854x480
	 */
	private void adjustCardArea(View rootView, View cardView, Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		int width  = display.getWidth();
		int height = display.getHeight();
	    LogUtils.d(TAG, "screen width = " + width + " , screen height= " + height);
	    
	    int statusBarHeight = Utils.getStatusBarHeight(activity);    
	    LogUtils.d(TAG, "statusbar top= " + statusBarHeight);
	    if (height != 752){
	    	//for some tablet, the height is the height of activity window,not the height of screen
	    	height = height - statusBarHeight;
	    }  
	    LogUtils.d(TAG, "screen width = " + width + " , screen height= " + height);
	    //must set oritation landscape in AndroidManifest.xml;
	    //sometimes the oritation don't change immediatelly when call function bellow: 
	    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		    RelativeLayout.LayoutParams params = (LayoutParams) cardView.getLayoutParams();
		    float screenScale = 1.0f * width / height;
	    	float scale = Math.min(screenScale, RATIO_WIDTH_TO_HEIGHT);	
	    	if (width > height){
		    	if (screenScale > RATIO_WIDTH_TO_HEIGHT){
		    		params.width = (int) (height * scale);
				    params.height = height;
		    	}else{
		    		params.width = width;
		    		params.height = (int) (width / scale);
		    	}
	    	}else{
	    		params.width = width;
	    		params.height = (int) (width / RATIO_WIDTH_TO_HEIGHT);
	    	}
	    	
	    	mContainerWidth = params.width;
	    	mContainerHeight = params.height;
		    cardView.setLayoutParams(params);
		    LogUtils.d(TAG, "params1.width = " + params.width + " , params.height = " + params.height);
	}
	
    private void adjustCardContent(){
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
			
		int w = display.getWidth() < display.getHeight()? display.getHeight() :  display.getWidth();
		int h = display.getWidth() < display.getHeight()? display.getWidth() :  display.getHeight();
    
		/** Get Android DPI from system*/
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
    	ResolutionManager manager = new ResolutionManager(mContainerWidth, mContainerHeight, dm.densityDpi, 720, 480, 240);
    	scaleViewById(R.id.card_postal_code, manager);
    	scaleViewById(R.id.card_postal_code_1, manager);
    	scaleViewById(R.id.card_postal_code_2, manager);
    	scaleViewById(R.id.card_postal_code_3, manager);
    	scaleViewById(R.id.card_postal_code_4, manager);
    	scaleViewById(R.id.card_postal_code_5, manager);
    	scaleViewById(R.id.card_postal_code_6, manager);
    	scaleViewById(R.id.card_description, manager);
    	
    	scaleViewById(R.id.card_receiver_address, manager);
    	//scaleViewById(R.id.card_receiver_name, manager);
    	//scaleViewById(R.id.card_postal_code_receiver, manager);
    	scaleViewById(R.id.card_stamp_area, manager);
    	scaleViewById(R.id.card_postal_code_sender, manager);
    }

    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    private void applyRotation(View view, float start, float end, boolean reversed) {
        // Find the center of the container
        final float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, reversed);
        rotation.setDuration(350);
        //rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(mDisplayNextView);

        view.startAnimation(rotation);
    }
	
	  /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final View mFrontView;
        private final View mBehindView;
        private boolean mViewReversed = false;
        private boolean mViewRotated = false;
        
        private DisplayNextView(View frontView, View behindView) {
        	mFrontView = frontView;
        	mBehindView = behindView;
        }

        public void onAnimationStart(Animation animation) {
        	
        }

        public void onAnimationEnd(Animation animation) {
        	if (mViewRotated){
        		mViewRotated = false;
        		return;
        	}
        	
        	if (!mViewReversed){
        		mCardBehindView.setVisibility(View.VISIBLE);
        		mCardFrontView.setVisibility(View.INVISIBLE);
        		applyRotation(mCardBehindView, -90, 0, false);
        		mViewReversed = true;
        		mViewRotated = true;
        	}else{
        		mCardFrontView.setVisibility(View.VISIBLE);
        		mCardBehindView.setVisibility(View.INVISIBLE);
        		applyRotation(mCardFrontView, -90, 0, false);
        		mViewReversed = false;
        		mViewRotated = true;
        	}
            //mContainer.post(new SwapViews(mPosition));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
    private final class SwapViews implements Runnable {
        private final int mPosition;

        public SwapViews(int position) {
            mPosition = position;
        }

        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;
            
            if (mPosition > -1) {
            	mCardFrontView.setVisibility(View.GONE);
            	mCardBehindView.setVisibility(View.VISIBLE);
            	mCardBehindView.requestFocus();

                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
            	mCardBehindView.setVisibility(View.GONE);
                mCardFrontView.setVisibility(View.VISIBLE);
                mCardFrontView.requestFocus();

                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            mContainer.startAnimation(rotation);
        }
    }
}