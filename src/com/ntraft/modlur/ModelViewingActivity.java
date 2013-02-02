package com.ntraft.modlur;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import com.ntraft.modlur.collada.ColladaParser;
import com.ntraft.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public final class ModelViewingActivity extends Activity implements GLSurfaceView.Renderer {

	static { Log.setTag("modlur"); }

    private GLSurfaceView glView;
	private ColladaParser handler;
	private Scene scene;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        glView = new GLSurfaceView(this);
    	glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    	glView.setRenderer(this);
        setContentView(glView);

		handler = new ColladaParser();
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

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		scene.draw(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		final float zNear = 1.0f, zFar = 100.0f, fieldOfView = 65.0f;
		float ratio = (float)width/(float)height;
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
		GLU.gluPerspective(gl, fieldOfView, ratio, zNear, zFar);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(0,0,0,1);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glClearDepthf(1.0f);
        
        try {
			scene = handler.parseFile(getResources().getAssets().open("cube.dae"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}