package ckt.projects.acl;

import android.util.Log;

public class Geometry {
	private final float[] vertices;
	private final int[] indices;

	public Geometry(float[] vertices, int[] indices) {
		Log.d("Collada", String.format("New Geometry:%nVertices: %s%nIndices: %s%n", toString(vertices), toString(indices)));
		this.vertices = vertices;
		this.indices = indices;
		if (vertices == null) {
			throw new RuntimeException("Indices = " + toString(indices));
		}
	}

	public float[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	private String toString(int[] arr) {
		if (arr == null) {
			return null;
		} else if (arr.length == 0) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder("[");
		for (int i : arr) {
			sb.append(i).append(", ");
		}
		sb.setLength(sb.length()-2);
		sb.append(']');
		return sb.toString();
	}

	private String toString(float[] arr) {
		if (arr == null) {
			return null;
		} else if (arr.length == 0) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder("[");
		for (float f : arr) {
			sb.append(f).append(", ");
		}
		sb.setLength(sb.length()-2);
		sb.append(']');
		return sb.toString();
	}
}
