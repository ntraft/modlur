package com.ntraft.modlur.collada;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author Neil Traft
 */
public final class FloatArrayHandler extends AbstractArrayHandler {

	private final FloatBuffer buf;

	public FloatArrayHandler(int count) {
		ByteBuffer bb = ByteBuffer.allocateDirect(count * 4);
		bb.order(ByteOrder.nativeOrder());
		buf = bb.asFloatBuffer();
	}

	@Override
	protected void addValue(String value) {
		buf.put(Float.valueOf(value));
	}

	@Override
	public FloatBuffer build() {
		buf.rewind();
		return buf;
	}
}
