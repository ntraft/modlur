package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

import java.nio.Buffer;

/**
 * @author Neil Traft
 */
public abstract class AbstractArrayHandler implements SubHandler {

	private final StringBuilder sb = new StringBuilder(10);

	protected abstract void addValue(String value);

	public abstract Buffer build();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) { }

	@Override
	public void characters(char[] ch, int start, int length) {
		int limit = start + length;
		for (int i = start; i < limit; i++) {
			char c = ch[i];
			if (Character.isWhitespace(c)) {
				addValue(sb.toString());
				sb.setLength(0); // does not shrink capacity
			} else {
				sb.append(c);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) { }
}
