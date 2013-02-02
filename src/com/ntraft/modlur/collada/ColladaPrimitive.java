package com.ntraft.modlur.collada;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public Map<Semantic, DataSink> build(Set<String> sources, Map<String, Map<Semantic, String>> vertices) {
		Map<Semantic, DataSink> sinks = new HashMap<Semantic, DataSink>();
		int maxOffset = 0;
		for (Input input : inputs) {
			maxOffset = Math.max(maxOffset, input.getOffset());
		}
		IntBuffer[] buffers = partitionIndices(maxOffset + 1);
		for (Input input : inputs) {
			String srcId = findSource(input.getSrcId(), input.getSemantic(), sources, vertices);
			IntBuffer in = buffers[input.getOffset()];
			sinks.put(input.getSemantic(), new DataSink(srcId, in));
		}
		return sinks;
	}

	private String findSource(String srcId, Semantic semantic, Set<String> sources, Map<String, Map<Semantic, String>> vertices) {
		if (!sources.contains(srcId)) {
			Map<Semantic, String> vertexInputs = vertices.get(srcId);
			if (vertexInputs != null) {
				srcId = vertexInputs.get(semantic);
			}
		}
		return srcId;
	}

	private IntBuffer[] partitionIndices(int numPartitions) {
		IntBuffer[] buffers = new IntBuffer[numPartitions];
		if (numPartitions == 1) {
			buffers[0] = indices;
		} else {
			int size = indices.remaining() / numPartitions;
			for (int i = 0; i < numPartitions; i++) {
				ByteBuffer bb = ByteBuffer.allocateDirect(size * 4);
				buffers[i] = bb.asIntBuffer();
			}
			// TODO This could be more optimized.
			int i = 0;
			while (indices.hasRemaining()) {
				buffers[i++].put(indices.get());
				i %= numPartitions;
			}
		}
		return buffers;
	}
}
