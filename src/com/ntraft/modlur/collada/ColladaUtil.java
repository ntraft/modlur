package com.ntraft.modlur.collada;

import javax.microedition.khronos.opengles.GL10;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author Neil Traft
 */
public final class ColladaUtil {

	private ColladaUtil() {}

	public static final String dereference(String idRef) {
		URI uri = URI.create(idRef);
		String part = uri.getSchemeSpecificPart();
		if (part != null && part.length() > 0) {
			throw new UnsupportedOperationException("modlur does not support external sources (" + uri.toString() + ")");
		}
		return uri.getFragment();
	}

	private static final float[] triangleColors = new float[] {
		1f, 0f, 0f, 1f,
		1f, 0f, 0f, 1f,
		1f, 0f, 0f, 1f,

		0f, 1f, 0f, 1f,
		0f, 1f, 0f, 1f,
		0f, 1f, 0f, 1f,

		0f, 0f, 1f, 1f,
		0f, 0f, 1f, 1f,
		0f, 0f, 1f, 1f,
	};

	private static final FloatBuffer createDebugColors(int drawMode, int size) {
		ByteBuffer bb = ByteBuffer.allocateDirect(size * 4 * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer colors = bb.asFloatBuffer();
		if (drawMode == GL10.GL_TRIANGLES) {
			for (int i = 0; i < size * 4; i++) {
				colors.put(triangleColors[i % triangleColors.length]);
			}
		} else {
			for (int i = 0; i < size; i++) {
				colors.put(1f);
				colors.put(0f);
				colors.put(1f);
				colors.put(1f);
			}
		}
		colors.rewind();
		return colors;
	}
}
