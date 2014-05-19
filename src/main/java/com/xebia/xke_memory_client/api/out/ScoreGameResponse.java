package com.xebia.xke_memory_client.api.out;

import org.codehaus.jackson.annotate.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class ScoreGameResponse {
	private Map<String, Integer> scores = new HashMap<>();

	public Map<String, Integer> getScores() {
		return scores;
	}

	public void setScores(Map<String, Integer> scores) {
		this.scores = scores;
	}

	@JsonAnySetter
	public void handleUnknown(final String key, final Object value) {
		scores.put(key, (Integer) value);
	}
}
