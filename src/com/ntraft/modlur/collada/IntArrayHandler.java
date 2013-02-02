package com.ntraft.modlur.collada;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * @author Neil Traft
 */
public final class IntArrayHandler extends AbstractArrayHandler {

	private final IntBuffer buf;

	public IntArrayHandler(int count) {
		ByteBuffer bb = ByteBuffer.allocateDirect(count * 4);
		bb.order(ByteOrder.nativeOrder());
		buf = bb.asIntBuffer();
	}

	@Override
	protected void addValue(String value) {
		buf.put(Integer.valueOf(value));
	}

	@Override
	public IntBuffer build() {
		buf.rewind();
		return buf;
	}
}
