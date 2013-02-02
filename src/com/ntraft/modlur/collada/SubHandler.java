package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public interface SubHandler {

	void startElement(String uri, String localName, String qName, Attributes attributes);

	void characters(char[] ch, int start, int length);

	void endElement(String uri, String localName, String qName);
}
