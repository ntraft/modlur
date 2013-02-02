package com.ntraft.modlur.collada;

import com.ntraft.modlur.Geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ColladaMesh {

	private static final int[] READ_BUF = new int[1024];

	private String id;
	private int[] upAxis;
	private final Map<String, FloatBuffer> floatArrays = new HashMap<String, FloatBuffer>();
	private final Map<String, Map<Semantic, String>> vertices = new HashMap<String, Map<Semantic, String>>();
	private final List<ColladaPrimitive> primitives = new ArrayList<ColladaPrimitive>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int[] getUpAxis() {
		return upAxis;
	}

	public void setUpAxis(int[] upAxis) {
		this.upAxis = upAxis;
	}

	public void addPrimitive(ColladaPrimitive primitive) {
		primitives.add(primitive);
	}

	public List<Geometry> build() {
		List<Geometry> built = new ArrayList<Geometry>();
		for (ColladaPrimitive primitive : primitives) {
			Map<Semantic, DataSink> dataSinks = primitive.build(floatArrays.keySet(), vertices);
			FloatBuffer vertices = consume(dataSinks, Semantic.VERTEX);
			FloatBuffer normals = consume(dataSinks, Semantic.NORMAL);
			built.add(new Geometry(vertices, normals, primitive.getDrawMode(), upAxis, primitive.getCount()));
		}
		return built;
	}

	private FloatBuffer consume(Map<Semantic, DataSink> dataSinks, Semantic semantic) {
		DataSink sink = dataSinks.get(semantic);
		if (sink == null) {
			return null;
		}

		FloatBuffer src = floatArrays.get(sink.getSourceId());
		if (src == null) {
			return null;
		}

		// TODO Hard-coded for now. In the future, the source determines the stride.
		int stride = 3;

		IntBuffer ib = sink.getIndices();
		ByteBuffer bb = ByteBuffer.allocateDirect(ib.limit());
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer dest = bb.asFloatBuffer();

		int remaining = ib.remaining();
		while (remaining > 0) {
			ib.get(READ_BUF, 0, remaining);
			for (int i : READ_BUF) {
				int index = i * stride;
				for (int j = 0; j < index + stride; j++) {
					dest.put(src.get(j));
				}
			}
		}

		dest.rewind();
		return dest;
	}
}
