package com.ntraft.modlur.collada;

import javax.microedition.khronos.opengles.GL10;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class ColladaPrimitive {

	private final Element primType;
	private int count;
	private final List<Input> inputs = new ArrayList<Input>();
	private IntBuffer indices;

	public ColladaPrimitive(Element primType) {
		this.primType = primType;
	}

	public Element getPrimType() {
		return primType;
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

	public void addInput(Input input) {
		inputs.add(input);
	}

	public void setIndices(IntBuffer indices) {
		this.indices = indices;
	}

	public Map<Semantic, DataSink> build() {
		// TODO
		return null;
	}
}
