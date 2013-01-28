package ckt.projects.acl;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ColladaHandler extends DefaultHandler {

	private final List<Geometry> geometries = new ArrayList<Geometry>();
	private final StringBuilder verticesBuilder = new StringBuilder();
	private final StringBuilder indicesBuilder = new StringBuilder();
	private float[] vertices;
	private short[] indices;
	private int[] upAxis = {0, 0, 0};

	private boolean inAxis = false;
	private boolean inVertices = false;
	private boolean inTriangles = false;
	private boolean inP = false;

	public ArrayList<ColladaObject> parseFile(InputStream input) throws SAXException, IOException {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(this);
			xr.parse(new InputSource(input));
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}

		ArrayList<ColladaObject> result = new ArrayList<ColladaObject>();
		for (Geometry g : geometries) {
			result.add(new ColladaObject(g, upAxis));
		}
		return result;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException {
		super.startElement(uri, localName, name, atts);

		if (localName.equalsIgnoreCase("float_array") && vertices == null && !atts.getValue("count").equals("2682")) {
			inVertices = true;
		} else if (localName.equalsIgnoreCase("triangles") && vertices != null) {
			inTriangles = true;
		} else if (localName.equalsIgnoreCase("p") && inTriangles) {
			inP = true;
		} else if (localName.equalsIgnoreCase("up_axis"))
			inAxis = true;
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);

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

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (inVertices && vertices == null) {
			verticesBuilder.append(ch, start, length);
		} else if (inP && indices == null) {
			indicesBuilder.append(ch, start, length);
		} else if (inAxis) {
			upAxis[ch[start] - 'X'] = 1;
		}
	}

}
