/**
 * @author Kevin Bohinski <bohinsk1@tcnj.edu>
 * @version 1.0
 * @since 2015-2-26
 * 
 * CSC380-P1-Konane
 * RandomPlayer.java
 * Copyright (c) 2015 Kevin Bohinski. All rights reserved.
 */

/*
 * Implement a player that simply picks a random move from the list of all possible moves. 
 * Play it against the SmartPlayer, the DumbPlayer and yourself.
 */

/* Imports */
import java.util.Random;

public class RandomPlayer {

	/* Global Vars */
	private Board board;
	private int pColor;

	public RandomPlayer(Board board, int pColor) {
		this.pColor = pColor;
		this.board = board;

	} /* constructor */

	private int[][] getPosMoves() {

		if (board.getEmpty() < 2) {
			/* First 2 moves of game */
			int[][] a;
			a = getRemoves();
			return a;
		} else {
			int size = board.getSize();

			/* size^3 is an estimate of how many moves are possible */
			/* 4 is for startRow, startCol, endRow, endCol */
			int[][] posMoves = new int[(size * size * size)][4];

			int numPosMoves = 0;

			/* pair of for loops goes thru board */
			for (int rows = 0; rows < size; rows++) {
				for (int cols = 0; cols < size; cols++) {

					/* these 4 if statements find if a move is possible */
					/* in the order up, down, left, right */
					/* need +/- 2 as the game piece "hops" another */

					if (board.validMove(rows, cols, rows, (cols - 2), pColor)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = rows;
						posMoves[numPosMoves][3] = (cols - 2);
						numPosMoves++;
					}
					if (board.validMove(rows, cols, rows, (cols + 2), pColor)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = rows;
						posMoves[numPosMoves][3] = (cols + 2);
						numPosMoves++;
					}
					if (board.validMove(rows, cols, (rows - 2), cols, pColor)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = (rows - 2);
						posMoves[numPosMoves][3] = cols;
						numPosMoves++;
					}
					if (board.validMove(rows, cols, (rows + 2), cols, pColor)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = (rows + 2);
						posMoves[numPosMoves][3] = cols;
						numPosMoves++;
					}

				}
			}
			/* Copying results to a more manageable array */
			int[][] a = new int[numPosMoves][4];

			/* Going thru entries */
			for (int b = 0; b < numPosMoves; b++) {
				/* Going thru startRow, startCol, endRow, endCol */
				for (int c = 0; c < 4; c++) {
					a[b][c] = posMoves[b][c];

				}
			}

			return a;
		}

	} /* getPosMoves */

	private int[][] getRemoves() {
		int size = board.getSize();
		int[][] a = new int[(size * size * size)][2];

		int numRms = 0;
		for (int rows = 0; rows < size; rows++) {
			for (int cols = 0; cols < size; cols++) {
				if (board.validRemove(rows, cols, pColor)) {
					a[numRms][0] = rows;
					a[numRms][1] = cols;
					numRms++;
				}
			}
		}

		/* Copying results to a more manageable array */
		int[][] aa = new int[numRms][2];

		/* Going thru entries */
		for (int b = 0; b < numRms; b++) {
			/* Going thru startRow, startCol, endRow, endCol */
			for (int c = 0; c < 2; c++) {
				aa[b][c] = a[b][c];
			}
		}

		return aa;

	} /* getRemoves */

	public boolean play() {
		int[][] a = getPosMoves();
		int numMoves = a.length;

		if (numMoves == 0) {
			return false;
		} else {
			if (board.getEmpty() < 2) {
				Random randomNum = new Random();
				int n = randomNum.nextInt(numMoves);
				board.remove(a[n][0], a[n][1], pColor);
				return true;
			}
			Random randomNum = new Random();
			int n = randomNum.nextInt(numMoves);
			board.move(a[n][0], a[n][1], a[n][2], a[n][3], pColor);
			return true;
		}

	} /* play */

} /* RandomPlayer */
