package abs.android.live.utils;


public class ResolutionManager{
	public static int SCREEN_WIDTH 		= 800;
	public static int SCREEN_HEIGHT 	= 480;
	public static int SCREEN_DPI		= 240;
	public static int SCALE_BASE_X 		= 800;
	public static int SCALE_BASE_Y 		= 480;
	public static int SCALE_BASE_DPI	= 240;
	
	public ResolutionManager(int scrWidth, int scrHeight, int scrDPI, int baseWidth, int baseHeight, int baseDPI){		
		SCREEN_WIDTH = scrWidth;
		SCREEN_HEIGHT = scrHeight;
		SCREEN_DPI = scrDPI;
		SCALE_BASE_X = baseWidth;
		SCALE_BASE_Y = baseHeight;
		SCALE_BASE_DPI = baseDPI;
	}
	
	public float getScaleX(){
		float scaleX = 1.0f * SCREEN_WIDTH / SCALE_BASE_X;
		return scaleX;
	}
	
	public float getScaleY(){
		float scaleX = 1.0f * SCREEN_HEIGHT / SCALE_BASE_Y;
		return scaleX;
	}
	
	//public LayoutParams getLayoutParams(int width, int height){
	//	return new LayoutParams((int) (width * getScaleX()), (int) (height * getScaleY()));
	//}
	
	public int scaleX(int x){
		if (x == android.view.ViewGroup.LayoutParams.FILL_PARENT
			|| x == android.view.ViewGroup.LayoutParams.WRAP_CONTENT
			|| x == android.view.ViewGroup.LayoutParams.MATCH_PARENT){
			return x;
		}
		return (x * SCREEN_WIDTH / SCALE_BASE_X);
	}

	public int scaleXWithDPI(int x){
		if (x == android.view.ViewGroup.LayoutParams.FILL_PARENT
			|| x == android.view.ViewGroup.LayoutParams.WRAP_CONTENT
			|| x == android.view.ViewGroup.LayoutParams.MATCH_PARENT){
			return x;
		}
		return (x * SCREEN_WIDTH * SCALE_BASE_DPI / SCALE_BASE_X / SCREEN_DPI);
	}
	
	public int scaleY(int y){
		if (y == android.view.ViewGroup.LayoutParams.FILL_PARENT
			|| y == android.view.ViewGroup.LayoutParams.WRAP_CONTENT
			|| y == android.view.ViewGroup.LayoutParams.MATCH_PARENT){
			return y;
		}
		
		return (y * SCREEN_HEIGHT / SCALE_BASE_Y);
	}
	
	public int scaleYWithDPI(int y){
		if (y == android.view.ViewGroup.LayoutParams.FILL_PARENT
			|| y == android.view.ViewGroup.LayoutParams.WRAP_CONTENT
			|| y == android.view.ViewGroup.LayoutParams.MATCH_PARENT){
				return y;
		}
		return (y * SCREEN_HEIGHT * SCALE_BASE_DPI / SCALE_BASE_Y / SCREEN_DPI);
	}
}