package ckt.projects.acl;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class ColladaObject {

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

	public void draw(GL10 gl){
        GLU.gluLookAt(gl, 5, 0, 3, 0, 0, 0, upAxis[0], upAxis[1], upAxis[2]);
    	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glPointSize(2f);
    	gl.glColor4f(1.0f, 0, 1.0f, 1.0f);
    	gl.glDrawElements(GL10.GL_LINE_STRIP, mIndexBuffer.limit(), GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
    }

}
