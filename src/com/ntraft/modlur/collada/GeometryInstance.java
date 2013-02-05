package com.ntraft.modlur.collada;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class GeometryInstance {

	private String targetId;
	private final Map<String, MaterialInstance> materialInstances = new HashMap<String, MaterialInstance>();

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public MaterialInstance getMaterialInstance(String materialId) {
		return materialInstances.get(materialId);
	}

	public void addMaterialInstance(MaterialInstance instance) {
		materialInstances.put(instance.getId(), instance);
	}
}
