package ckt.projects.acl;

import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ColladaObject {

	private FloatBuffer   mVertexBuffer;
    private IntBuffer mIndexBuffer;
    private int[] upAxis;
	
    public ColladaObject(Geometry geometry, int[] upAxis) { // add colors eventually
    	this.upAxis = upAxis;

		ByteBuffer vbb = ByteBuffer.allocateDirect(geometry.getVertices().length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(geometry.getVertices());
        mVertexBuffer.position(0);

		ByteBuffer ibb = ByteBuffer.allocateDirect(geometry.getIndices().length*4);
		mIndexBuffer = ibb.asIntBuffer();
		mIndexBuffer.put(geometry.getIndices());
        mIndexBuffer.position(0);
    }

	public void draw(GL10 gl){
        GLU.gluLookAt(gl, 5, 0, 3, 0, 0, 0, upAxis[0], upAxis[1], upAxis[2]);
    	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    	gl.glColor4f(0, 0, 1.0f, 1.0f);
    	gl.glDrawElements(GL10.GL_TRIANGLES, mIndexBuffer.limit(), GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
    }

}
