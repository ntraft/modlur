package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class MaterialsHandler implements SubHandler {

	private Element currentElement = Element.NONE;

	private final Map<String, Material> materials = new HashMap<String, Material>();
	private String currentMaterial;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		currentElement = Element.findElementByTag(localName);
		switch (currentElement) {
		case MATERIAL:
			currentMaterial = attributes.getValue("id");
			materials.put(currentMaterial, new Material());
			break;
		case INSTANCE_EFFECT:
			String effectId = ColladaUtil.dereference(attributes.getValue("url"));
			materials.get(currentMaterial).setEffectId(effectId);
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (currentElement.is(localName)) {
			switch (currentElement) {
				case MATERIAL:
					currentMaterial = null;
					break;
			}
			currentElement = Element.NONE;
		}
	}

	public Map<String, Material> build() {
		return materials;
	}
}
