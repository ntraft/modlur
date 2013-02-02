package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

public final class GeometryHandler implements SubHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private final ColladaMesh geom = new ColladaMesh();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case GEOMETRY:
				geom.setId(attributes.getValue("id"));
				break;
			case SOURCE:
				currentHandler = new SourceHandler();
				break;
			case LINES: case LINESTRIPS: case TRIANGLES: case TRIFANS: case TRISTRIPS: case POLYGONS: case POLYLIST:
				currentHandler = new PrimitiveHandler(currentElement);
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

			if (currentElement.is(localName)) {
				switch (currentElement) {
				case SOURCE:
					geom.addSource(((SourceHandler) currentHandler).build());
					break;
				case LINES: case LINESTRIPS: case TRIANGLES: case TRIFANS: case TRISTRIPS: case POLYGONS: case POLYLIST:
					geom.addPrimitive(((PrimitiveHandler) currentHandler).build());
					break;
				case ASSET:
					geom.setUpAxis(((AssetHandler) currentHandler).build());
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	public ColladaMesh build() {
		return geom;
	}
}
