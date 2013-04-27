package abs.android.live;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class AbsRoomLiveActivity extends Activity implements OnClickListener{
	private GridView mGridView;
	private GridAdapter mGridAdapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initUI();
        initObject();
    }
    
    private void initUI(){ 	
    	mGridView = (GridView) findViewById(R.id.main_grid_view);
    	//mGridView.setColumnWidth(230);
		
    	mGridAdapter = new GridAdapter(this);
    	mGridView.setAdapter(mGridAdapter);
    }

    private void initObject(){
    	
    }
    
    private void startNewCard(){
    	Intent intent = new Intent(this, CardEditActivity.class);
    	startActivity(intent);
    }
    
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.main_item_photo_area){
			startNewCard();
		}
	}
	
	
	private class GridAdapter extends BaseAdapter{

		private class ViewHolder{
			public View photoArea;
			public ImageView photo;
			public TextView text;
		}
		
		private Context mContext;
		public GridAdapter(Context context){
			mContext = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item, null, false);
				holder = new ViewHolder();
				holder.photoArea = convertView.findViewById(R.id.main_item_photo_area);
				holder.photo = (ImageView) convertView.findViewById(R.id.main_item_photo);
				holder.text = (TextView) convertView.findViewById(R.id.main_item_text);
				
				holder.photoArea.setOnClickListener(AbsRoomLiveActivity.this);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			if (position == 0){
				holder.photo.setImageResource(R.drawable.main_card_thumb_new);
				holder.text.setText("New card");
			}else if (position == 1){
				holder.photo.setImageResource(R.drawable.main_card_thumb_receive);
				holder.text.setText("Inbox card");
			}
			
			return convertView;
		}	
	}
}