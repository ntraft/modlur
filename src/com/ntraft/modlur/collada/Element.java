package com.ntraft.modlur.collada;

/**
 * @author Neil Traft
 */
public enum Element {

	NONE,
	UP_AXIS,
	LIBRARY_GEOMETRIES,
	GEOMETRY,
	SOURCE,
	INT_ARRAY,
	FLOAT_ARRAY,
	VERTICES,
	LINES,
	LINESTRIPS,
	POLYGONS,
	POLYLIST,
	TRIANGLES,
	TRIFANS,
	TRISTRIPS,
	INPUT,
	P,
	;

	public static final Element findElementByTag(String tag) {
		for (Element el : values()) {
			if (el.name().equalsIgnoreCase(tag)) {
				return el;
			}
		}
		return NONE;
	}

	public boolean is(String localName) {
		return name().equalsIgnoreCase(localName);
	}
}
