package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public class AssetHandler implements SubHandler {

	private int[] upAxis;

	public AssetHandler(int[] dflt) {
		this.upAxis = dflt;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes atts) { }

	@Override
	public void characters(char[] ch, int start, int length) {
		if (length > 0) {
			upAxis = new int[3];
			upAxis[ch[start] - 'X'] = 1;
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) { }

	public int[] build() {
		return upAxis;
	}
}
