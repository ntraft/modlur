package com.ntraft.modlur.collada;

import android.opengl.GLU;
import com.ntraft.modlur.Geometry;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public final class ColladaObject {

	private final String id;
	private final FloatBuffer   mVertexBuffer;
	private final ShortBuffer mIndexBuffer;
	private final int[] upAxis;

	public ColladaObject(Geometry geometry, int[] upAxis) { // add colors eventually
    	this.upAxis = upAxis;

		ByteBuffer vbb = ByteBuffer.allocateDirect(geometry.getVertices().length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(geometry.getVertices());
        mVertexBuffer.rewind();

		ByteBuffer ibb = ByteBuffer.allocateDirect(geometry.getIndices().length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndexBuffer = ibb.asShortBuffer();
		mIndexBuffer.put(geometry.getIndices());
		mIndexBuffer.rewind();
	}

	public FloatBuffer getmVertexBuffer() {
		return mVertexBuffer;
	}

	public ShortBuffer getmIndexBuffer() {
		return mIndexBuffer;
	}

	public int[] getUpAxis() {
		return upAxis;
	}

	public String getId() {
		return id;
	}
}
