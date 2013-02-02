package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class GeometriesHandler implements SubHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private final Map<String, ColladaMesh> geometries = new HashMap<String, ColladaMesh>();
	private int[] upAxis;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case GEOMETRY:
				currentHandler = new GeometryHandler();
				break;
			case UP_AXIS:
				currentHandler = new AxisHandler(upAxis);
				break;
			}
		}

		if (currentHandler != null) {
			currentHandler.startElement(uri, localName, qName, attributes);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		if (currentHandler != null) {
			currentHandler.characters(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (currentHandler != null) {
			currentHandler.endElement(uri, localName, qName);

			if (currentElement.is(localName)) {
				switch (currentElement) {
				case GEOMETRY:
					ColladaMesh geom = ((GeometryHandler) currentHandler).build();
					geometries.put(geom.getId(), geom);
					break;
				case UP_AXIS:
					upAxis = ((AxisHandler) currentHandler).build();
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	public Map<String, ColladaMesh> build() {
		for (ColladaMesh geom : geometries.values()) {
			if (geom.getUpAxis() == null) {
				geom.setUpAxis(upAxis);
			}
		}
		return geometries;
	}
}
