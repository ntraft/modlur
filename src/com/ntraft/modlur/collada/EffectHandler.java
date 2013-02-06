package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

import java.nio.FloatBuffer;

/**
 * @author Neil Traft
 */
public final class EffectHandler implements SubHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private final Effect effect = new Effect();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case EFFECT:
				effect.setId(attributes.getValue("id"));
				break;
			case COLOR:
				currentHandler = new FloatArrayHandler(4);
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
				case EFFECT:
					FloatBuffer buf = ((FloatArrayHandler) currentHandler).build();
					float[] color = new float[4];
					buf.get(color);
					effect.setColor(color);
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	public Effect build() {
		return effect;
	}
}
