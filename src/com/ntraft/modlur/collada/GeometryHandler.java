package com.ntraft.modlur.collada;

import com.ntraft.modlur.Geometry;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

public final class GeometryHandler implements SubHandler {

	private final List<Geometry> geometries = new ArrayList<Geometry>();
	private final StringBuilder verticesBuilder = new StringBuilder();
	private final StringBuilder indicesBuilder = new StringBuilder();
	private final int[] upAxis = {0, 0, 0};

	private float[] vertices;
	private short[] indices;

	private boolean inAxis = false;
	private boolean inVertices = false;
	private boolean inTriangles = false;
	private boolean inP = false;

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) {
		if (localName.equalsIgnoreCase("float_array") && vertices == null) {
			inVertices = true;
		} else if (localName.equalsIgnoreCase("triangles") && vertices != null) {
			inTriangles = true;
		} else if (localName.equalsIgnoreCase("p") && inTriangles) {
			inP = true;
		} else if (localName.equalsIgnoreCase("up_axis"))
			inAxis = true;
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		if (inVertices && vertices == null) {
			verticesBuilder.append(ch, start, length);
		} else if (inP && indices == null) {
			indicesBuilder.append(ch, start, length);
		} else if (inAxis) {
			upAxis[ch[start] - 'X'] = 1;
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) {
		if (localName.equalsIgnoreCase("float_array")) {
			if (vertices == null && verticesBuilder.length() > 0) {
				String[] temp = verticesBuilder.toString().split("\\s+");
				vertices = new float[temp.length];
				for (int i = 0; i < vertices.length; i++) {
					vertices[i] = Float.valueOf(temp[i]);
				}
			}
			verticesBuilder.setLength(0);
			inVertices = false;
		} else if (localName.equalsIgnoreCase("triangles")) {
			if (indicesBuilder.length() > 0) {
				String[] temp = indicesBuilder.toString().split("\\s+");
				indices = new short[temp.length / 2 + 1];
				for (int i = 0; i < temp.length; i += 2) {
					indices[i / 2] = Short.valueOf(temp[i]);
				}
				geometries.add(new Geometry(vertices, indices));
			}
			indicesBuilder.setLength(0);
			vertices = null;
			indices = null;
			inTriangles = false;
		} else if (localName.equalsIgnoreCase("p")) {
			inP = false;
		} else if (localName.equalsIgnoreCase("up_axis"))
			inAxis = false;
	}

	public ColladaObject build() {
		// TODO
		return null;
	}
}
