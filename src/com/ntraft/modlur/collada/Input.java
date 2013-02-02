package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public final class Input {

	private final int offset;
	private final int set;
	private final Semantic semantic;
	private final String srcId;

	public Input(Attributes attributes, boolean shared) {
		if (shared) {
			offset = Integer.valueOf(attributes.getValue("offset"));
			String setVal = attributes.getValue("set");
			set = setVal == null ? 0 : Integer.valueOf(setVal);
		} else {
			offset = 0;
			set = 0;
		}
		semantic = Semantic.valueOf(attributes.getValue("semantic"));
		srcId = attributes.getValue("source");
	}

	public int getOffset() {
		return offset;
	}

	public int getSet() {
		return set;
	}

	public Semantic getSemantic() {
		return semantic;
	}

	public String getSrcId() {
		return srcId;
	}
}
