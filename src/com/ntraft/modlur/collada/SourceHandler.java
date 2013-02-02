package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public final class SourceHandler implements SubHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private final Source source = new Source();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case SOURCE:
				source.setId(attributes.getValue("id"));
				break;
			case FLOAT_ARRAY:
				int count = Integer.valueOf(attributes.getValue("count"));
				currentHandler = new FloatArrayHandler(count);
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
				case FLOAT_ARRAY:
					source.setData(((FloatArrayHandler) currentHandler).build());
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	public Source build() {
		return source;
	}
}
