package com.ntraft.modlur.collada;

import com.ntraft.modlur.Geometry;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public final class ColladaObject {

	private String id;
	private int[] upAxis;
	private final Map<String, FloatBuffer> floatArrays = new HashMap<String, FloatBuffer>();

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

	public Geometry build() {
		// TODO
		return null;
	}
}
