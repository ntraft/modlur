package com.ntraft.modlur;

import android.opengl.GLSurfaceView;
import com.ntraft.modlur.collada.ColladaParser;
import org.xml.sax.SAXException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Neil Traft
 */
public final class ModelRenderer implements GLSurfaceView.Renderer {

	public float mAngleX;
	public float mAngleY;

	private final Scene scene;
	private final float zDist;

	public ModelRenderer(InputStream modelFile) throws IOException, SAXException {
		scene = new ColladaParser().parseFile(modelFile);
		zDist = calculateGoodDist(scene);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -zDist);
		gl.glRotatef(mAngleX, 0, 1, 0);
		gl.glRotatef(mAngleY, 1, 0, 0);

		scene.draw(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		float ratio = width / (float) height;

//		float zNear = 1.0f, zFar = 100.0f, fieldOfView = 65.0f;
//		GLU.gluPerspective(gl, fieldOfView, ratio, zNear, zFar);
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
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
	}

	private static final float calculateGoodDist(Scene scene) {
		return scene.getLargestDistFromOrigin() * 2;
	}
}
