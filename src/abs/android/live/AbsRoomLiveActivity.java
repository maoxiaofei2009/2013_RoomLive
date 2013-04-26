package abs.android.live;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AbsRoomLiveActivity extends Activity implements OnClickListener{
	private Button mBtnNewCard;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initUI();
    }
    
    private void initUI(){
    	mBtnNewCard = (Button) findViewById(R.id.main_new_card);
    	mBtnNewCard.setOnClickListener(this);
    }

    private void startNewCard(){
    	Intent intent = new Intent(this, CardEditActivity.class);
    	startActivity(intent);
    }
    
	@Override
	public void onClick(View v) {
		if (v == mBtnNewCard){
			startNewCard();
		}
		
	}
}