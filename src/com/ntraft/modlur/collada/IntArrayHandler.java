package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

import java.nio.IntBuffer;

/**
 * @author Neil Traft
 */
public class IntArrayHandler implements SubHandler {

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
	}

	@Override
	public void characters(char[] ch, int start, int length) {
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
	}

	public IntBuffer build() {
		return null;
	}
}
