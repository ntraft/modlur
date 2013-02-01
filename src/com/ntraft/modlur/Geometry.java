package com.ntraft.modlur;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;
import java.nio.FloatBuffer;

public final class Geometry {

	private final FloatBuffer vertices;
	private final FloatBuffer normals;
	private final int drawMode;
	private final int[] upAxis;
	private final int size;

	public Geometry(FloatBuffer vertices, FloatBuffer normals, int drawMode, int[] upAxis, int size) {
		this.vertices = vertices;
		this.normals = normals;
		this.drawMode = drawMode;
		this.upAxis = upAxis;
		this.size = size;
	}

	public void draw(GL10 gl) {
		// TODO Support different access methods.
        GLU.gluLookAt(gl, 5, 0, 3, 0, 0, 0, upAxis[0], upAxis[1], upAxis[2]);
		if (vertices != null) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
		} else {
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		if (normals != null) {
			gl.glNormalPointer(GL10.GL_FLOAT, 0, normals);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		} else {
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glPointSize(2f);
    	gl.glColor4f(1.0f, 0, 1.0f, 1.0f);
    	gl.glDrawArrays(drawMode, 0, size);
    }

}
