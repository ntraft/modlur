package com.ntraft.modlur;

import javax.microedition.khronos.opengles.GL10;
import java.util.List;

/**
 * @author Neil Traft
 */
public final class Scene {

	private final List<Geometry> geometries;

	public Scene(List<Geometry> geometries) {
		this.geometries = geometries;
	}

	public void draw(GL10 gl) {
		for (Geometry g : geometries) {
			g.draw(gl);
		}
	}
}
