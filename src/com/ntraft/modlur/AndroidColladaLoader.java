package ckt.projects.acl;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

public class AndroidColladaLoader extends Activity implements GLSurfaceView.Renderer{
    private GLSurfaceView glView;
	private ColladaHandler handler;
	private ArrayList<ColladaObject> objectArray;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        glView = new GLSurfaceView(this);
    	glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    	glView.setRenderer(this);
        setContentView(glView);

		handler = new ColladaHandler();
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

		for (ColladaObject anObjectArray : objectArray) {
			anObjectArray.draw(gl);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		final float zNear = 1.0f, zFar = 100.0f, fieldOfView = 45.0f;
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
			objectArray = handler.parseFile(getResources().getAssets().open("model.dae"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}