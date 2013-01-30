package com.ntraft.modlur;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Neil Traft
 */
public final class Scene {

	private final Geometry[] geometries;

	public Scene(Geometry[] geometries) {
		this.geometries = geometries;
	}

	public void draw(GL10 gl) {
		for (Geometry g : geometries) {
			g.draw(gl);
		}
	}
}
