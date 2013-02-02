package com.ntraft.modlur.collada;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neil Traft
 */
public final class Vertices {

	private String id;
	private final Map<Semantic, String> inputs = new HashMap<Semantic, String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String get(Semantic semantic) {
		return inputs.get(semantic);
	}

	public void addInput(Input input) {
		Semantic semantic = input.getSemantic();
		if (semantic == Semantic.POSITION)
			semantic = Semantic.VERTEX;
		inputs.put(semantic, input.getSrcId());
	}
}
