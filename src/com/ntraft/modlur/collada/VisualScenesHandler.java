package com.ntraft.modlur.collada;

import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class VisualScenesHandler implements SubHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private final Map<String, VisualScene> scenes = new HashMap<String, VisualScene>();
	private String currentScene;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case VISUAL_SCENE:
				currentScene = attributes.getValue("id");
				scenes.put(currentScene, new VisualScene());
				break;
			case INSTANCE_GEOMETRY:
				currentHandler = new InstanceGeometryHandler();
				break;
			}
		}

		if (currentHandler != null) {
			currentHandler.startElement(uri, localName, qName, attributes);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		if (currentHandler != null) {
			currentHandler.characters(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (currentHandler != null) {
			currentHandler.endElement(uri, localName, qName);

			if (currentElement.is(localName)) {
				switch (currentElement) {
				case VISUAL_SCENE:
					currentScene = null;
					break;
				case INSTANCE_GEOMETRY:
					scenes.get(currentScene).addGeometryInstance(((InstanceGeometryHandler) currentHandler).build());
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	public Map<String, VisualScene> build() {
		return scenes;
	}
}
