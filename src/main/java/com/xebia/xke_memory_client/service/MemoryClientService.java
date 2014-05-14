package com.xebia.xke_memory_client.service;

import com.xebia.xke_memory_client.api.out.PlayResponse;
import com.xebia.xke_memory_client.api.out.ScoreGameResponse;
import com.xebia.xke_memory_client.api.out.ScorePlayerResponse;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.Map;

public class MemoryClientService {

    private WebTarget target;

    public MemoryClientService(final String hostname, final String port) {
        target = ClientBuilder.newClient(new ClientConfig()).register(JacksonFeature.class).target(MessageFormat.format("http://{0}:{1}", hostname, port));
    }

    public void play() {
        int[][] playPositions = {{0, 1}, {2, 3}};
        final PlayResponse playResponse = target.path("/play").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(playPositions), PlayResponse.class);

        System.out.println(playResponse.getGameId());
        System.out.println(playResponse.getTurn().getTurnScore());
        System.out.println(playResponse.getTurn().getCards().size());
    }

    public void gameScore(final String gameId) {
        final ScoreGameResponse score = target.path("/scores/game").path(gameId).request(MediaType.APPLICATION_JSON_TYPE).get(ScoreGameResponse.class);
        System.out.println("Les scores des joueurs sur " + gameId);
        System.out.println(score.getScores().size());
        for (Map.Entry<String, Integer> playerScore : score.getScores().entrySet()) {
            System.out.println(playerScore.getKey() + " - " + playerScore.getValue());
        }
    }

    public void playerScore(final String ip) {
        final ScorePlayerResponse score = target.path("/scores/player").path(ip).request(MediaType.APPLICATION_JSON_TYPE).get(ScorePlayerResponse.class);
        System.out.println("Le scores de " + ip);

        for (Map.Entry<Integer, Integer> playerScore : score.getScores().entrySet()) {
            System.out.println(playerScore.getKey() + " - " + playerScore.getValue());
        }
    }

    public boolean register(final String email) {
        final Response result = target.path("/scores/register").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(email));
        System.out.println(result.getStatus());
        return true;
    }
}
