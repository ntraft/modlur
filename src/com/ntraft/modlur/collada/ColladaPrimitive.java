package com.ntraft.modlur.collada;

import javax.microedition.khronos.opengles.GL10;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class ColladaPrimitive {

	private final Element primType;
	private int count;

	public ColladaPrimitive(Element primType) {
		this.primType = primType;
	}

	public int getDrawMode() {
		switch (primType) {
		case LINES:
			return GL10.GL_LINES;
		case LINESTRIPS:
			return GL10.GL_LINE_STRIP;
		case TRIANGLES:
			return GL10.GL_TRIANGLES;
		case TRIFANS:
			return GL10.GL_TRIANGLE_FAN;
		case TRISTRIPS:
			return GL10.GL_TRIANGLE_STRIP;
		case POLYGONS:
		case POLYLIST:
		default:
			throw new UnsupportedOperationException("Unsupported geometry type: " + primType);
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Map<Semantic, DataSink> build() {
		// TODO
		return null;
	}
}
