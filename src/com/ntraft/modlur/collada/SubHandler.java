package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public interface SubHandler {

	void startElement(String uri, String localName, String name, Attributes atts);

	void characters(char[] ch, int start, int length);

	void endElement(String uri, String localName, String name);
}
