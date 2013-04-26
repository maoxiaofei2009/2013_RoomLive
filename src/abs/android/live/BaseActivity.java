package abs.android.live;

import abs.android.live.utils.ResolutionManager;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public class BaseActivity extends Activity{
	public View scaleViewById(int id, ResolutionManager manager){
		View view = super.findViewById(id);
		if (view == null){
			return null;
		}
		
		if (manager != null){
			ViewGroup.MarginLayoutParams  params = (ViewGroup.MarginLayoutParams)view.getLayoutParams();	
			params.width = manager.scaleX(params.width);
			params.height = manager.scaleY(params.height);
			
			int paddingBottom = manager.scaleY(view.getPaddingBottom());
			int paddingTop = manager.scaleY(view.getPaddingTop());
			int paddingLeft = manager.scaleX(view.getPaddingLeft());
			int paddingRight = manager.scaleX(view.getPaddingRight());
			view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
			
			params.leftMargin = manager.scaleX(params.leftMargin);
			params.topMargin =  manager.scaleY(params.topMargin);
			params.rightMargin = manager.scaleX(params.rightMargin);
			params.bottomMargin = manager.scaleY(params.bottomMargin);
			params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin);
			view.setLayoutParams(params);
		}

		return view;
	}
}