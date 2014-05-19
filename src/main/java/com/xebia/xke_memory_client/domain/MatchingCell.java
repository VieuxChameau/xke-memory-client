package com.xebia.xke_memory_client.domain;

public class MatchingCell {
	private final GridCell firstCell;

	private final GridCell secondCell;

	public MatchingCell(final GridCell firstCell, final GridCell secondCell) {
		this.firstCell = firstCell;
		this.secondCell = secondCell;
	}

	public GridCell getFirstCell() {
		return firstCell;
	}

	public GridCell getSecondCell() {
		return secondCell;
	}
}
