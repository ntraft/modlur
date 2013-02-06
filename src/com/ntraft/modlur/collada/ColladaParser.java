package com.ntraft.modlur.collada;

import com.ntraft.modlur.Geometry;
import com.ntraft.modlur.Scene;
import com.ntraft.util.DebuggingContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ColladaParser extends DefaultHandler {

	private Element currentElement = Element.NONE;
	private SubHandler currentHandler;

	private HashMap<String, VisualScene> scenes = new HashMap<String, VisualScene>();
	private HashMap<String, Mesh> meshes = new HashMap<String, Mesh>();
	private HashMap<String, Effect> effects = new HashMap<String, Effect>();
	private HashMap<String, Material> materials = new HashMap<String, Material>();

	/* Collada Spec defines the default to be Y_UP. */
	private int[] upAxis = {0, 1, 0};

	public Scene parseFile(InputStream input) throws SAXException, IOException {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(new DebuggingContentHandler(this));
			xr.parse(new InputSource(input));
			return buildScene();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		if (currentHandler == null) {
			currentElement = Element.findElementByTag(localName);
			switch (currentElement) {
			case LIBRARY_VISUAL_SCENES:
				currentHandler = new VisualScenesHandler();
				break;
			case LIBRARY_GEOMETRIES:
				currentHandler = new GeometriesHandler();
				break;
			case UP_AXIS:
				currentHandler = new AxisHandler(upAxis);
				break;
			}
		}

		if (currentHandler != null) {
			currentHandler.startElement(uri, localName, qName, attributes);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);

		if (currentHandler != null) {
			currentHandler.characters(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);

		if (currentHandler != null) {
			currentHandler.endElement(uri, localName, qName);

			if (currentElement.is(localName)) {
				switch (currentElement) {
				case LIBRARY_VISUAL_SCENES:
					scenes.putAll(((VisualScenesHandler) currentHandler).build());
					break;
				case LIBRARY_GEOMETRIES:
					meshes.putAll(((GeometriesHandler) currentHandler).build());
					break;
				case UP_AXIS:
					upAxis = ((AxisHandler) currentHandler).build();
					break;
				}
				currentElement = Element.NONE;
				currentHandler = null;
			}
		}
	}

	private Scene buildScene() {
		List<Geometry> geoms = new ArrayList<Geometry>();
		for (VisualScene vScene : scenes.values()) {
			for (GeometryInstance instance : vScene.getGeometryInstances()) {
				Mesh mesh = meshes.get(instance.getTargetId());
				if (mesh != null) {
					geoms.addAll(mesh.build(bindMaterialsTo(instance)));
				}
			}
		}
		return new Scene(geoms, upAxis);
	}

	private Map<String, Effect> bindMaterialsTo(GeometryInstance instance) {
		Map<String, Effect> bound = new HashMap<String, Effect>();
		for (MaterialInstance materialInstance : instance.getAllMaterialInstances()) {
			Material material = materials.get(materialInstance.getTargetId());
			if (material != null) {
				Effect effect = effects.get(material.getEffectId());
				if (effect != null) {
					bound.put(materialInstance.getId(), effect);
				}
			}
		}
		return bound;
	}

}
