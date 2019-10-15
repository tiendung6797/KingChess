package core;

import chessPieces.Pieces;

public class Move {
	private Pieces pieces;
	private int numberNext;

	public Move(Pieces pieces, int numberNext) {
		this.pieces = pieces;
		this.numberNext = numberNext;
	}

	public Pieces getPieces() {
		return pieces;
	}

	public int getNumberNext() {
		return numberNext;
	}

}
