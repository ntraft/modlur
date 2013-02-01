package com.ntraft.modlur.collada;

import java.nio.IntBuffer;

/**
 * @author Neil Traft
 */
public final class DataSink {

	private final String sourceId;
	private final IntBuffer indices;

	public DataSink(String sourceId, IntBuffer indices) {
		this.sourceId = sourceId;
		this.indices = indices;
	}

	public String getSourceId() {
		return sourceId;
	}

	public IntBuffer getIndices() {
		return indices;
	}
}
