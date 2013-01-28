package ckt.projects.acl;

import android.util.Log;

public class Geometry {

	private final float[] vertices;
	private final short[] indices;

	public Geometry(float[] vertices, short[] indices) {
		this.vertices = vertices;
		this.indices = indices;
		Log.d("Collada", String.format("New Geometry: %d vertices and %d indices%n", vertices.length, indices.length));
		Log.d("Collada", String.format("Vertices: %s%n", toString(vertices)));
		Log.d("Collada", String.format("Indices: %s%n", toString(indices)));
	}

	public float[] getVertices() {
		return vertices;
	}

	public short[] getIndices() {
		return indices;
	}

	private String toString(byte[] arr) {
		if (arr == null) {
			return null;
		} else if (arr.length == 0) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder("[");
		for (byte b : arr) {
			sb.append(b).append(", ");
		}
		sb.setLength(sb.length()-2);
		sb.append(']');
		return sb.toString();
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

	private String toString(short[] arr) {
		if (arr == null) {
			return null;
		} else if (arr.length == 0) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder("[");
		for (short s : arr) {
			sb.append(s).append(", ");
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
