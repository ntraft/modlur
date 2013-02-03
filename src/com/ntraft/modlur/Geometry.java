package com.ntraft.modlur;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public final class Geometry {

	private final FloatBuffer vertices;
	private final FloatBuffer normals;
	private final FloatBuffer colors;
	private final int drawMode;
	private final int[] upAxis;
	private final int size;

	private static final float[] triangleColors = new float[] {
		1f, 0f, 0f, 1f,
		1f, 0f, 0f, 1f,
		1f, 0f, 0f, 1f,

		0f, 1f, 0f, 1f,
		0f, 1f, 0f, 1f,
		0f, 1f, 0f, 1f,

		0f, 0f, 1f, 1f,
		0f, 0f, 1f, 1f,
		0f, 0f, 1f, 1f,
	};

	public Geometry(FloatBuffer vertices, FloatBuffer normals, int drawMode, int[] upAxis, int size) {
		this.vertices = vertices;
		this.normals = normals;
		this.drawMode = drawMode;
		this.upAxis = upAxis;
		this.size = size;

		ByteBuffer bb = ByteBuffer.allocateDirect(size * 4 * 4);
		bb.order(ByteOrder.nativeOrder());
		colors = bb.asFloatBuffer();
		if (drawMode == GL10.GL_TRIANGLES) {
			for (int i = 0; i < size * 4; i++) {
				colors.put(triangleColors[i % triangleColors.length]);
			}
		} else {
			for (int i = 0; i < size; i++) {
				colors.put(1f);
				colors.put(0f);
				colors.put(1f);
				colors.put(1f);
			}
		}
		colors.rewind();
	}

	public void draw(GL10 gl) {
		if (vertices != null) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
		} else {
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		if (normals != null) {
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, normals);
		} else {
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
		if (colors != null) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colors);
		} else {
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}

		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);

		gl.glLineWidth(2f);
    	gl.glDrawArrays(drawMode, 0, size);
    }

	public float getLargestDistFromOrigin() {
		float max = 0;
		while (vertices.remaining() > 2) {
			float dist = distFromOrigin(vertices.get(), vertices.get(), vertices.get());
			max = Math.max(max, dist);
		}
		vertices.rewind();
		return max;
	}

	private static final float distFromOrigin(float x, float y, float z) {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
}
