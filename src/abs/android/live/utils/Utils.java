package abs.android.live.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;



public class Utils{
	private static final String TAG = "Utils";
	public static final boolean DEBUG = true;

	public static String getFileCreateTime(File file){
		long date_modified = file.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
		Date curDate = new Date(date_modified);      
		return formatter.format(curDate);
	}
	
	public static String getFileCreateTime(long lastModified){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
		Date curDate = new Date(lastModified);      
		return formatter.format(curDate);
	}
	
	public static String getFileSize(File file){
		long size = file.length()/1024;
		if (size == 0){
			return "" + file.length() + "byte,";
		}else if (size < 1024){
			return new java.text.DecimalFormat("0").format(size) + "KB,";
		}else{
			return new java.text.DecimalFormat("0.00").format(1.0f*size/1024) + "MB,";
		}	
	}
	
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}
	
	public static boolean judgeWifiIsOK(Context context) {
		final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager == null ||
			wifiManager.getConnectionInfo() == null ||
			wifiManager.getConnectionInfo().getIpAddress() == 0) {
			return false;
		}
		return true;
	}

	public static boolean IsSdCardMounted() { 
	    try { 
	    	//for coolpad, there is udisk folder just used as internal sdcard.
	    	if (new File("/udisk").exists()){
	    		return true;
	    	}
	    	
	        return Environment.getExternalStorageState().equals( 
	                Environment.MEDIA_MOUNTED); 
	    } catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
	    return false; 
	} 
	
	public static boolean isTablet(){
		int sdk = Build.VERSION.SDK_INT;
//		Utils.LOGD(TAG, "isTablet: sdk = " + sdk);
		if (sdk == 11 || sdk == 12 || sdk == 13){
			return true;
		}
		
		return false;
	}
	
	public static void showSoftInput(final EditText editText, boolean open){
		if (editText != null){
			if (open){
				editText.setFocusable(true);
		        editText.setFocusableInTouchMode(true);
		        editText.requestFocus();
		        Timer timer = new Timer();
			    timer.schedule(new TimerTask() {
			         public void run() {
			             InputMethodManager imm =(InputMethodManager)editText.getContext()
			            		 .getSystemService(Context.INPUT_METHOD_SERVICE);
			             imm.showSoftInput(editText, 0);
			         }},  298);
			}else{
				 InputMethodManager imm = (InputMethodManager) (InputMethodManager)editText.getContext()
						 .getSystemService(Context.INPUT_METHOD_SERVICE); 
				 imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			}
		}
	}
}