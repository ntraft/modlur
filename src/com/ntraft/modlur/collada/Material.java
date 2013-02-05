package com.ntraft.modlur.collada;

/**
 * @author Neil Traft
 */
public final class Material {

	private final String id;
	private final String effectId;

	public Material(String id, String effectId) {
		this.id = id;
		this.effectId = effectId;
	}

	public String getId() {
		return id;
	}

	public String getEffectId() {
		return effectId;
	}

}
