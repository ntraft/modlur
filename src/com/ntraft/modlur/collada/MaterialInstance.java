package com.ntraft.modlur.collada;

/**
 * @author Neil Traft
 */
public final class MaterialInstance {

	private final String id;
	private final String targetId;

	public MaterialInstance(String id, String targetId) {
		this.id = id;
		this.targetId = targetId;
	}

	public String getId() {
		return id;
	}

	public String getTargetId() {
		return targetId;
	}
}
