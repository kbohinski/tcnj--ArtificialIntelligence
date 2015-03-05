/**
 * @author Kevin Bohinski <bohinsk1@tcnj.edu>
 * @version 1.0
 * @since 2015-2-26
 * 
 * CSC380-P1-Konane
 * MinimaxPlayer.java
 * Copyright (c) 2015 Kevin Bohinski. All rights reserved.
 */

/*
 * Finally, implement a player using minimax search with alpha-beta pruning. For
 * boards that are larger than 4x4, this algorithm will take a very long time to
 * run. So you will have to use a cutoff test and an evaluation function. Your
 * evaluator will largely determine the success of your program, so it is worth
 * spending time testing various methods. The maximum depth should be set to 4.
 */

public class MinimaxPlayer {

	/* Global Vars */
	private Board board;
	private int pColor;
	private int bestMove = 0;

	public MinimaxPlayer(Board board, int pColor) {
		this.pColor = pColor;
		this.board = board;

	} /* constructor */

	private int[][] getPosMoves(Board tempBoard, int color) {

		if (tempBoard.getEmpty() < 2) {
			/* First 2 moves of game */
			int[][] a;
			a = getRemoves(color);
			return a;
		} else {
			int size = tempBoard.getSize();

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

					if (tempBoard
							.validMove(rows, cols, rows, (cols - 2), color)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = rows;
						posMoves[numPosMoves][3] = (cols - 2);
						numPosMoves++;
					}
					if (tempBoard
							.validMove(rows, cols, rows, (cols + 2), color)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = rows;
						posMoves[numPosMoves][3] = (cols + 2);
						numPosMoves++;
					}
					if (tempBoard
							.validMove(rows, cols, (rows - 2), cols, color)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = (rows - 2);
						posMoves[numPosMoves][3] = cols;
						numPosMoves++;
					}
					if (tempBoard
							.validMove(rows, cols, (rows + 2), cols, color)) {
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

	private Board[] getPosMovesB(Board tempBoard, int color) {

		if (tempBoard.getEmpty() < 2) {
			/* First 2 moves of game */
			Board[] a;
			a = getRemovesB(tempBoard, color);
			return a;

		} else {
			int size = tempBoard.getSize();

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

					if (tempBoard
							.validMove(rows, cols, rows, (cols - 2), color)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = rows;
						posMoves[numPosMoves][3] = (cols - 2);
						numPosMoves++;
					}
					if (tempBoard
							.validMove(rows, cols, rows, (cols + 2), color)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = rows;
						posMoves[numPosMoves][3] = (cols + 2);
						numPosMoves++;
					}
					if (tempBoard
							.validMove(rows, cols, (rows - 2), cols, color)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = (rows - 2);
						posMoves[numPosMoves][3] = cols;
						numPosMoves++;
					}
					if (tempBoard
							.validMove(rows, cols, (rows + 2), cols, color)) {
						posMoves[numPosMoves][0] = rows;
						posMoves[numPosMoves][1] = cols;
						posMoves[numPosMoves][2] = (rows + 2);
						posMoves[numPosMoves][3] = cols;
						numPosMoves++;
					}

				}
			}

			/* Copying results to an array of boards */
			Board[] a = new Board[numPosMoves];

			/* Going thru entries */
			for (int b = 0; b < numPosMoves; b++) {
				/* Going thru startRow, startCol, endRow, endCol */
				Board tempBoardFor = new Board(tempBoard);
				tempBoardFor.move(posMoves[b][0], posMoves[b][1],
						posMoves[b][2], posMoves[b][3], color);
				a[b] = tempBoardFor;
			}

			return a;
		}

	} /* getPosMovesB */

	private Board[] getRemovesB(Board tempBoard, int pColor) {
		int size = tempBoard.getSize();
		int[][] a = new int[(size * size * size)][2];

		int numRms = 0;
		for (int rows = 0; rows < size; rows++) {
			for (int cols = 0; cols < size; cols++) {
				if (tempBoard.validRemove(rows, cols, this.pColor)) {
					a[numRms][0] = rows;
					a[numRms][1] = cols;
					numRms++;
				}
			}
		}

		/* Copying results to a more manageable array */
		Board[] aa = new Board[numRms];

		/* Going thru entries */
		for (int b = 0; b < numRms; b++) {
			/* Going thru startRow, startCol, endRow, endCol */
			Board tempBoardFor = new Board(tempBoard);
			tempBoardFor.remove(a[b][0], a[b][1], this.pColor);
			aa[b] = tempBoardFor;
		}

		return aa;

	} /* getRemoves */

	private int[][] getRemoves(int pColor) {
		int size = board.getSize();
		int[][] a = new int[(size * size * size)][2];

		int numRms = 0;
		for (int rows = 0; rows < size; rows++) {
			for (int cols = 0; cols < size; cols++) {
				if (board.validRemove(rows, cols, this.pColor)) {
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

	/**
	 * Minimax Function 
	 * Recursively searches possible moves to find the best
	 * move for the maximizing player, and the worst for the minimizing player.
	 * 
	 * To find these moves, this function uses the heuristic of the number of
	 * possible moves max player can make - number of possible moves min player can make.
	 * 
	 * @param currState
	 *            : Current state of the game as a board
	 * @param depth
	 *            : How many times minimax has called itself
	 * @param playersTurn
	 *            : Who's move it is
	 * @param alpha
	 *            : For alpha beta pruning to increase efficiency
	 * @param beta
	 *            : For alpha beta pruning to increase efficiency
	 * @return : Returns the heuristical value for the move
	 */
	private int minimax(Board currState, int depth, int playersTurn, int alpha,
			int beta) {
		int playerColor = 0;
		if (playersTurn == 1) {
			playerColor = pColor;
		} else {
			if (pColor == 1) {
				playerColor = 2;
			} else if (pColor == 2) {
				playerColor = 1;
			}
		}
		/*
		 * Base Case As per spec, if the depth is greater than 4 or there are no
		 * moves left, stop searching and return the heuristic.
		 */
		if ((getPosMoves(currState, playerColor).length) == 0) {
			return (-playersTurn);
		}
		if (depth > 4) {
			int h = 0;
			int a = 0;
			int b = 0;
			int bColor = 0;
			a = (getPosMoves(currState, playerColor).length);
			if (playerColor == 1) {
				bColor = 2;
			} else {
				bColor = 1;
			}
			b = (getPosMoves(currState, bColor).length);
			h = (a - b);
			return (h);
		}
		/*
		 * Recursive Search
		 * 
		 * Searches recursively to find the best move for the maximizing player,
		 * and the worst move for the minimizing player.
		 */
		if (playersTurn > 0) {
			int max = Integer.MIN_VALUE;
			int temp = max;
			for (int i = 0; i < getPosMoves(currState, playerColor).length; i++) {
				Board bd = getPosMovesB(currState, playerColor)[i];
				/* recurse over bottom nodes */
				max = Math.max(max,
						minimax(bd, (depth + 1), (-playersTurn), alpha, beta));
				alpha = Math.max(alpha, max);
				if (temp != max && depth == 1) {
					bestMove = i;
					temp = max;
				}
				if (beta <= alpha) {
					break;
				}
			}
			return max;
		} else {
			int min = Integer.MAX_VALUE;
			for (Board bd : getPosMovesB(currState, playerColor)) {
				/* recurse over bottom nodes */
				min = Math.min(min,
						minimax(bd, (depth + 1), (-playersTurn), alpha, beta));
				beta = Math.min(beta, min);
				if (beta <= alpha) {
					break;
				}
			}
			return min;
		}
	} /* minimax */

	public boolean play() {
		minimax(board, 1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
		int a[][] = getPosMoves(board, pColor);

		if (a.length == 0) {
			return false;
		} else {
			if (board.getEmpty() < 2) {
				board.remove(a[bestMove][0], a[bestMove][1], pColor);
				return true;
			}
			board.move(a[bestMove][0], a[bestMove][1], a[bestMove][2],
					a[bestMove][3], pColor);
			return true;
		}

	} /* play */

} /* RandomPlayer */
