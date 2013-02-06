package com.ntraft.modlur.collada;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Traft
 */
public final class VisualScene {

	private final List<GeometryInstance> geometryInstances = new ArrayList<GeometryInstance>();

	public List<GeometryInstance> getGeometryInstances() {
		return geometryInstances;
	}

	public void addGeometryInstance(GeometryInstance instance) {
		geometryInstances.add(instance);
	}
}
