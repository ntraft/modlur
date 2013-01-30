package com.ntraft.modlur.collada;

/**
 * @author Neil Traft
 */
public enum Element {

	NONE(""),
	ASSET("asset"),
	LIBRARY_GEOMETRIES("library_geometries");

	private final String tag;

	private Element(String tag) {
		this.tag = tag;
	}

	public final String getTag() {
		return tag;
	}

	public static final Element findElementByTag(String tag) {
		for (Element el : values()) {
			if (el.getTag().equalsIgnoreCase(tag)) {
				return el;
			}
		}
		return NONE;
	}
}
