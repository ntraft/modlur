package com.ntraft.modlur.collada;

/**
 * @author Neil Traft
 */
public final class Effect {

	private float[] color;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return color.length;
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}
}
