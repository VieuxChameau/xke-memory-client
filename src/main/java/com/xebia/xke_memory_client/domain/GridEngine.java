package com.xebia.xke_memory_client.domain;

import com.xebia.xke_memory_client.api.in.PlayRequest;
import com.xebia.xke_memory_client.api.out.PlayResponse;
import com.xebia.xke_memory_client.api.out.TurnCard;
import com.xebia.xke_memory_client.service.MemoryClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GridEngine {
	private static final Logger LOGGER = LoggerFactory.getLogger(GridEngine.class);

	private static final float GAME_COMPLETE = 100;

	private static final int DELAY_TIME = 0;

	private static final int JACKPOT = 10;

	private final MemoryClientService service;

	private final String email;

	private final LinkedList<Point> availablePositions;

	private float gameProgress = 0;

	private int gameId = 0;

	private Map<GameCard, GridCell> validCards = new HashMap<>();

	private Stack<MatchingCell> priorityMoves = new Stack<>();

	public GridEngine(final int gridSize, final MemoryClientService service, final String email) {
		this.service = service;
		this.email = email;

		availablePositions = new LinkedList<>();
		for (int x = 0; x < gridSize; ++x) {
			for (int y = 0; y < gridSize; ++y) {
				availablePositions.add(new Point(x, y));
			}
		}
		Collections.shuffle(availablePositions);
	}

	public void startGaming() {
		service.register(email);

		while (shouldKeepPlaying()) {
			final PlayRequest nextPlay = getNextPlay();
			delayPlay();
			final PlayResponse playResponse = service.play(nextPlay);
			updateStatus(nextPlay, playResponse);
		}

		displayFinalRoundScore();
	}

	private void delayPlay() {
		if (DELAY_TIME > 0) {
			try {
				Thread.sleep(DELAY_TIME);
			} catch (InterruptedException e) {
				LOGGER.error("Fail to sleep", e);
			}
		}
	}


	private boolean shouldKeepPlaying() {
		LOGGER.debug("Game Progress {}%, Moves {}, Availables Positions {}", gameProgress, priorityMoves.size(), availablePositions.size());
		return gameProgress < GAME_COMPLETE && (!priorityMoves.empty() || !availablePositions.isEmpty());
	}

	private PlayRequest getNextPlay() {
		if (!priorityMoves.empty()) {
			final MatchingCell cells = priorityMoves.pop();
			return new PlayRequest(cells.getFirstCell().getPoint(), cells.getSecondCell().getPoint());
		}
		return new PlayRequest(availablePositions.pollFirst(), availablePositions.pollLast());
	}

	private void updateStatus(final PlayRequest request, final PlayResponse response) {
		this.gameId = response.getGameId();
		this.gameProgress = response.getProgress();
		final List<TurnCard> cards = response.getTurn().getCards();
		final GridCell firstCell = createGridCell(request.getFirstPoint(), cards.get(0));
		final GridCell secondCell = createGridCell(request.getSecondPoint(), cards.get(1));

		compute(response, firstCell, secondCell);
	}

	private void compute(final PlayResponse response, final GridCell firstCell, final GridCell secondCell) {
		LOGGER.debug("Current Turn score {} - Message {}", response.getTurn().getTurnScore(), response.getTurn().getMessage());
		displayCellFound(firstCell);
		displayCellFound(secondCell);
		if (response.getTurn().getTurnScore() != JACKPOT) {
			analyze(firstCell);
			analyze(secondCell);
		}
	}

	private void analyze(final GridCell cell) {
		final GameCard gameCard = cell.getGameCard();
		if (cell.isAlreadyFound()) { // Carte déja trouvée
			validCards.remove(gameCard); // On supprime si on a déjà trouvé la première
		} else {
			final GridCell matchingCell = validCards.get(gameCard);
			if (matchingCell != null) { // on a déjà son doublon
				validCards.remove(gameCard);
				priorityMoves.push(new MatchingCell(matchingCell, cell));
			} else {
				validCards.put(gameCard, cell);
			}
		}
	}

	private GridCell createGridCell(final Point point, final TurnCard card) {
		final GameCard gameCard = new GameCard(card.getSymbol(), card.getColor());
		return new GridCell(gameCard, point, card.isFound());
	}

	private void displayCellFound(final GridCell cell) {
		final GameCard gameCard = cell.getGameCard();
		final Point point = cell.getPoint();
		LOGGER.debug("Card found \"{} {}\" at ({};{}) ? ({})", gameCard.getColor(), gameCard.getSymbol(), point.getX(), point.getY(), cell.isAlreadyFound());
	}

	private void displayFinalRoundScore() {
		final Map<String, Integer> scorePerPlayers = service.gameScore(String.valueOf(gameId));
		for (Map.Entry<String, Integer> playerScore : scorePerPlayers.entrySet()) {
			LOGGER.debug("{} : {} pts", playerScore.getKey(), playerScore.getValue());
		}
	}
}
