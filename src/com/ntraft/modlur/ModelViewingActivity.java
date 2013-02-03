package com.ntraft.modlur;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import com.ntraft.util.Log;

public final class ModelViewingActivity extends Activity {

	static { Log.setTag("modlur"); }

    private GLSurfaceView glView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		try {
			glView = new TouchRotateView(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
//		glView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR | GLSurfaceView.DEBUG_LOG_GL_CALLS);
        setContentView(glView);
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
        glView.onResume();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
   		glView.onPause();
    }

}