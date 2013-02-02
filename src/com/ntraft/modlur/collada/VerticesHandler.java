package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public class VerticesHandler implements SubHandler {

	private final Vertices vertices = new Vertices();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		Element currentElement = Element.findElementByTag(localName);
		switch (currentElement) {
		case VERTICES:
			vertices.setId(attributes.getValue("id"));
			break;
		case INPUT:
			vertices.addInput(new Input(attributes, false));
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) { }

	@Override
	public void endElement(String uri, String localName, String qName) { }

	public Vertices build() {
		return vertices;
	}
}
