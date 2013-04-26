package abs.android.live;

import abs.android.live.utils.LogUtils;
import abs.android.live.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CardEditActivity extends Activity {
	private static final String TAG = "CardEditActivity";
	private static final float RATIO_WIDTH_TO_HEIGHT = 1.5f;
	private View mRootView;
	private View mCardView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        
        initUI();
    }
    
    private void initUI(){
    	mRootView = findViewById(R.id.card_root);
    	mCardView = findViewById(R.id.card_view);
    	adjustViewArea(mRootView, mCardView, this);
    }
    
    
	/**
	 * scale the DmcFileView with the same ratio with 854x480
	 */
	private void adjustViewArea(View rootView, View cardView, Activity activity){
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
	    if (width > height){
		    RelativeLayout.LayoutParams params = (LayoutParams) cardView.getLayoutParams();
		    float screenScale = 1.0f * width / height;
	    	float scale = Math.min(screenScale, RATIO_WIDTH_TO_HEIGHT);	
	    	if (screenScale > RATIO_WIDTH_TO_HEIGHT){
	    		params.width = (int) (height * scale);
			    params.height = height;
	    	}else{
	    		params.width = width;
	    		params.height = (int) (width / scale);
	    	}
		    cardView.setLayoutParams(params);
		    LogUtils.d(TAG, "params1.width = " + params.width + " , params.height = " + params.height);
	    }
	}
}