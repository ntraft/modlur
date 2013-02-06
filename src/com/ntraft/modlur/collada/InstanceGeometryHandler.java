package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

/**
 * @author Neil Traft
 */
public final class InstanceGeometryHandler implements SubHandler {

	private final GeometryInstance instance = new GeometryInstance();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		Element currentElement = Element.findElementByTag(localName);
		switch (currentElement) {
		case INSTANCE_GEOMETRY:
			instance.setTargetId(ColladaUtil.dereference(attributes.getValue("url")));
			break;
		case INSTANCE_MATERIAL:
			String symbol = attributes.getValue("symbol");
			String target = ColladaUtil.dereference(attributes.getValue("target"));
			instance.addMaterialInstance(new MaterialInstance(symbol, target));
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {}

	@Override
	public void endElement(String uri, String localName, String qName) {}

	public GeometryInstance build() {
		return instance;
	}
}
