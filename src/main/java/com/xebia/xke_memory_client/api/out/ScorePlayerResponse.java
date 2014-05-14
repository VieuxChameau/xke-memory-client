package com.xebia.xke_memory_client.api.out;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScorePlayerResponse {

    private Map<Integer, Integer> scores = new HashMap<>();

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    @JsonAnySetter
    public void handleUnknown(final String key, final Object value) {
        scores.put(Integer.valueOf(key), (Integer) value);
    }
}
