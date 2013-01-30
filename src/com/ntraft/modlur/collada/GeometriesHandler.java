package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class GeometriesHandler implements SubHandler {

	private final Map<String, ColladaObject> geometries = new HashMap<String, ColladaObject>();

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) {
	}

	@Override
	public void characters(char[] ch, int start, int length) {
	}

	@Override
	public void endElement(String uri, String localName, String name) {
	}

	public Map<String, ColladaObject> build() {
		return geometries;
	}
}
