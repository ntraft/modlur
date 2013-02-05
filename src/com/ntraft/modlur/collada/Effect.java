package com.ntraft.modlur.collada;

/**
 * @author Neil Traft
 */
public final class Effect {

	private final float[] color;

	public Effect(float[] color) {
		this.color = color;
	}

	public int getSize() {
		return color.length;
	}

	public float[] getColor() {
		return color;
	}
}
