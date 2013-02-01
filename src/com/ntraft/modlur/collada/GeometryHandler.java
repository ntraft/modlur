package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

public final class GeometryHandler implements SubHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private final ColladaObject geom = new ColladaObject();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case GEOMETRY:
				geom.setId(attributes.getValue("id"));
				break;
			case ASSET:
				currentHandler = new AssetHandler(null);
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

			if (currentElement.getTag().equalsIgnoreCase(localName)) {
				switch (currentElement) {
				case ASSET:
					geom.setUpAxis(((AssetHandler) currentHandler).build());
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	public ColladaObject build() {
		return geom;
	}
}
