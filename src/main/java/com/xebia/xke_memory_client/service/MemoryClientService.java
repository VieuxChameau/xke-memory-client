package com.xebia.xke_memory_client.service;

import com.xebia.xke_memory_client.api.in.PlayRequest;
import com.xebia.xke_memory_client.api.out.PlayResponse;
import com.xebia.xke_memory_client.api.out.ScoreGameResponse;
import com.xebia.xke_memory_client.api.out.ScorePlayerResponse;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.Map;

import static javax.ws.rs.client.Entity.json;
import static javax.ws.rs.core.Response.Status.OK;

public class MemoryClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryClientService.class);

    private WebTarget target;

    public MemoryClientService(final String hostname, final String port) {
        LOGGER.debug("Connecting to http://{}:{}", hostname, port);
        target = ClientBuilder.newClient(new ClientConfig()).register(JacksonFeature.class).target(MessageFormat.format("http://{0}:{1}", hostname, port));
    }

    public PlayResponse play(final PlayRequest playRequest) {
        return target.path("/play").request(MediaType.APPLICATION_JSON_TYPE).post(json(playRequest.asArray()), PlayResponse.class);
    }

    public Map<String, Integer> gameScore(final String gameId) {
        LOGGER.debug("Score Game {}", gameId);

        final ScoreGameResponse score = target.path("/scores/game").path(gameId).request(MediaType.APPLICATION_JSON_TYPE).get(ScoreGameResponse.class);
        return score.getScores();
    }

    public Map<Integer, Integer> playerScore(final String ip) {
        LOGGER.debug("Score Player {}", ip);
        final ScorePlayerResponse score = target.path("/scores/player").path(ip).request(MediaType.APPLICATION_JSON_TYPE).get(ScorePlayerResponse.class);
        return score.getScores();
    }

    public boolean register(final String email) {
        LOGGER.debug("Register {}", email);
        final Response result = target.path("/scores/register").request(MediaType.APPLICATION_JSON_TYPE).post(json(email));
        return result.getStatus() == OK.getStatusCode();
    }
}
