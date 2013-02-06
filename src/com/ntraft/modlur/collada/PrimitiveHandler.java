package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public final class PrimitiveHandler implements SubHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private final Primitive prim;

	public PrimitiveHandler(Element primType) {
		prim = new Primitive(primType);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			if (currentElement == prim.getPrimType()) {
				prim.setCount(Integer.valueOf(attributes.getValue("count")));
				prim.setMaterialId(attributes.getValue("material"));
			} else if (currentElement == Element.INPUT) {
				prim.addInput(new Input(attributes, true));
			} else if (currentElement == Element.P) {
				currentHandler = new IntArrayHandler(prim.getNumIndices());
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
				case P:
					prim.setIndices(((IntArrayHandler) currentHandler).build());
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	public Primitive build() {
		return prim;
	}
}
