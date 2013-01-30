package com.ntraft.modlur.collada;

import com.ntraft.modlur.Geometry;
import com.ntraft.modlur.Scene;
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
import java.util.ConcurrentModificationException;
import java.util.HashMap;

public final class ColladaParser extends DefaultHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private HashMap<String, ColladaObject> geometries = new HashMap<String, ColladaObject>();
	/* Collada Spec defines the default to be Y_UP. */
	private int[] upAxis = {0, 1, 0};

	public Scene parseFile(InputStream input) throws SAXException, IOException {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(this);
			xr.parse(new InputSource(input));
			return buildScene();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case LIBRARY_GEOMETRIES:
				currentHandler = new GeometriesHandler();
				break;
			case ASSET:
				currentHandler = new AssetHandler(upAxis);
				break;
			}
		}

		if (currentHandler != null) {
			currentHandler.startElement(uri, localName, qName, attributes);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);

		if (currentHandler != null) {
			currentHandler.characters(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);

		if (currentHandler != null) {
			currentHandler.endElement(uri, localName, qName);

			if (currentElement.getTag().equalsIgnoreCase(localName)) {
				switch (currentElement) {
				case LIBRARY_GEOMETRIES:
					geometries.putAll(((GeometriesHandler) currentHandler).build());
					break;
				case ASSET:
					upAxis = ((AssetHandler) currentHandler).build();
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	private Scene buildScene() {
		Geometry[] geoms = new Geometry[geometries.size()];
		int i = 0;
		for (ColladaObject cGeom : geometries.values()) {
			int[] upAxis = cGeom.getUpAxis();
			if (upAxis == null) upAxis = this.upAxis;
			geoms[i++] = new Geometry(cGeom.getmVertexBuffer(), cGeom.getmIndexBuffer(), upAxis);
		}
		if (i != geoms.length) {
			throw new ConcurrentModificationException();
		}
		return new Scene(geoms);
	}
}
