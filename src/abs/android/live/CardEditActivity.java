package abs.android.live;

import abs.android.live.utils.AnimHelper;
import abs.android.live.utils.GestrueHelper;
import abs.android.live.utils.GestrueHelper.FLING_DIRECTION;
import abs.android.live.utils.GestrueHelper.FlingCallback;
import abs.android.live.utils.LogUtils;
import abs.android.live.utils.ResolutionManager;
import abs.android.live.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CardEditActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "CardEditActivity";
	private static final float RATIO_WIDTH_TO_HEIGHT = 1.5f;
	private View mRootView;
	private ImageView mEditBtn;
	private View mContainer;
	private ViewGroup mCardFrontView;
	private ViewGroup mCardBehindView;
	
	private View mCardDetailView;
	
	
	private AnimHelper mAnimHelper;
	private GestrueHelper mGestrueHelper;
	private int mContainerWidth = 0;
	private int mContainerHeight = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        
        initUI();
        initObject();
    }
    
    private void initUI(){
    	mRootView = findViewById(R.id.card_root);
    	mEditBtn = (ImageView) findViewById(R.id.head_right_button);
    	mEditBtn.setOnClickListener(this);
    	mContainer = findViewById(R.id.card_view);
    	mCardFrontView = (ViewGroup) findViewById(R.id.card_front_page);
    	mCardFrontView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    	mCardBehindView = (ViewGroup) findViewById(R.id.card_behind_page);
    	mCardBehindView.setVisibility(View.INVISIBLE);
    	mCardBehindView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    	adjustCardArea(mRootView, mContainer, this);
    	adjustCardContent();
    	
    	mCardDetailView = findViewById(R.id.card_detail);
    }
    
    private void initObject(){
    	mAnimHelper = new AnimHelper();
    	mAnimHelper.initCardRotateViewe(mCardFrontView, mCardBehindView);
    	
    	mGestrueHelper = new GestrueHelper(this);
    	mGestrueHelper.setFlingCallback(mContainer, new FlingCallback() {	
			@Override
			public void onFling(FLING_DIRECTION direction) {
				if (direction == FLING_DIRECTION.LEFT){
					if (mCardFrontView.getVisibility() == View.VISIBLE){
						mAnimHelper.applyRotationFirst(mCardFrontView, 0, 90, true);
						mCardBehindView.setTag(-90);
					}else{
						mAnimHelper.applyRotationFirst(mCardBehindView, 0, 90, true);
						mCardFrontView.setTag(-90);
					}
				}else{
					if (mCardFrontView.getVisibility() == View.VISIBLE){
						mAnimHelper.applyRotationFirst(mCardFrontView, 0, -90, true);
						mCardBehindView.setTag(90);
					}else{
						mAnimHelper.applyRotationFirst(mCardBehindView, 0, -90, true);
						mCardFrontView.setTag(90);
					}
				}
			}
		});
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


    private void swicthCardDetail(){
		if (mCardDetailView.getVisibility() ==  View.GONE){
			mCardDetailView.setVisibility(View.VISIBLE);
			mEditBtn.setBackgroundResource(R.drawable.card_done_drawable);
		}else{
			mCardDetailView.setVisibility(View.GONE);
			mEditBtn.setBackgroundResource(R.drawable.card_edit_drawable);
		}
    }

	@Override
	public void onClick(View v) {
		if (v == mEditBtn){
			swicthCardDetail();
		}
	}
	
	
	@Override
	public void onBackPressed() {
		if (mCardDetailView.getVisibility() !=  View.GONE){
			swicthCardDetail();
			return;
		}
		super.onBackPressed();
	}
}