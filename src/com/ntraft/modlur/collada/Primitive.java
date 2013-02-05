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
public final class Primitive {

	private final Element primType;
	private int count;
	private String materialId;
	private final List<Input> inputs = new ArrayList<Input>();
	private IntBuffer indices;

	public Primitive(Element primType) {
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

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public int getNumVertices() {
		return numVerticesPerPrimitive();
	}

	public int getNumIndices() {
		return getNumVertices() * numIndexSets();
	}

	public void addInput(Input input) {
		inputs.add(input);
	}

	public void setIndices(IntBuffer indices) {
		this.indices = indices;
	}

	public Map<Semantic, DataSink> build(Set<String> sources, Map<String, Vertices> vertices) {
		Map<Semantic, DataSink> sinks = new HashMap<Semantic, DataSink>();
		IntBuffer[] buffers = partitionIndices(numIndexSets());
		for (Input input : inputs) {
			String srcId = findSource(input.getSrcId(), input.getSemantic(), sources, vertices);
			IntBuffer in = buffers[input.getOffset()];
			sinks.put(input.getSemantic(), new DataSink(srcId, in));
		}
		return sinks;
	}

	private int numIndexSets() {
		int maxOffset = 0;
		for (Input input : inputs) {
			maxOffset = Math.max(maxOffset, input.getOffset());
		}
		return maxOffset + 1;
	}

	private int numVerticesPerPrimitive() {
		switch (primType) {
		case LINES:
			return count * 2;
		case LINESTRIPS:
			return count + 1;
		case TRIANGLES:
			return count * 3;
		case TRIFANS:
		case TRISTRIPS:
			return count + 2;
		case POLYGONS:
		case POLYLIST:
		default:
			throw new UnsupportedOperationException("Unsupported geometry type: " + primType);
		}
	}

	private String findSource(String srcId, Semantic semantic, Set<String> sources, Map<String, Vertices> vertices) {
		if (!sources.contains(srcId)) {
			Vertices vertexInputs = vertices.get(srcId);
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
			for (IntBuffer buf : buffers) {
				buf.rewind();
			}
		}
		return buffers;
	}
}
