//*********************************************
// Board.java         
//
// implements a konane game board 
//
// Code provided by Dr. Salgian
//*********************************************

public class Board {
	int board[][]; // actual board
	int size; // size of board
	int n_empty; // number of empty locations on board

	// ************************************************************
	// Constructor
	// initializes a board of the given size
	// ************************************************************
	public Board(int size) {
		this.size = size;

		board = new int[size][size];
		n_empty = 0;

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = (i + j) % 2 + 1;
	}

	// ************************************************************
	// Constructor
	// Creates a copy of the board given as parameter
	// ************************************************************
	public Board(Board b) {
		size = b.getSize();
		n_empty = b.getEmpty();

		board = new int[size][size];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = b.board[i][j];
	}

	// ************************************************************
	// Returns size of board
	// ************************************************************
	public int getSize() {
		return size;
	}

	// ************************************************************
	// Returns number of empty locations on board
	// ************************************************************
	public int getEmpty() {
		return n_empty;
	}

	// ************************************************************
	// Removes a piece from the board, if the move is valid
	// Parameters: row, col: position of piece to be removed
	// value: color of piece to be removed (1 for black,2 for white)
	// ************************************************************
	public void remove(int row, int col, int value) {
		// check validity
		if (validRemove(row, col, value)) {
			// remove piece
			n_empty++;
			board[row][col] = 0;
		} else
			// move is not valid
			System.out.println("That move is not valid!" + row + col + value);
	}

	// ************************************************************
	// Checks if removal is valid
	// Parameters: row, col: position of piece to be removed
	// value: color of piece to be removed (1 for black,2 for white)
	// Returns true or false
	// ************************************************************
	public boolean validRemove(int row, int col, int value) {
		// does the color match the location?
		if ((row + col) % 2 + 1 != value)
			return false;

		// is the location out of bounds?
		if (row < 0)
			return false;
		if (col < 0)
			return false;
		if (row >= size)
			return false;
		if (col >= size)
			return false;

		// is this the first move?
		if (n_empty == 0) {
			// is this on the diagonal?
			if (row != col)
				return false;
			// is this a corner?
			if (row == 0)
				return true;
			if (row == size - 1)
				return true;
			// is this the middle?
			if (row == size / 2)
				return true;
			if (row == (size - 1) / 2)
				return true;
			// all check failed
			return false;
		}

		// is this not the second move of the game?
		if (n_empty > 1)
			return false;

		// is the location adjacent to an empty location?
		if ((row > 0) && (board[row - 1][col] == 0))
			return true;
		if ((col > 0) && (board[row][col - 1] == 0))
			return true;
		if ((row < size - 1) && (board[row + 1][col] == 0))
			return true;
		if ((col < size - 1) && (board[row][col + 1] == 0))
			return true;

		// all checks failed
		return false;
	}

	// ************************************************************
	// perform a move
	// Parameters: start_row, start_col - initial position
	// end_row, end_col - end position
	// value: color of piece (1 for black, 2 for white)
	// ************************************************************
	public void move(int start_row, int start_col, int end_row, int end_col,
			int value) {
		// check if it is a valid move
		if (validMove(start_row, start_col, end_row, end_col, value)) {
			board[end_row][end_col] = value;
			board[start_row][start_col] = 0;
			int row = (start_row + end_row) / 2;
			int col = (start_col + end_col) / 2;
			board[row][col] = 0;
			n_empty++;
		} else
			System.out.println("That is not a valid move!" + start_row
					+ start_col + end_row + end_col + value);
	}

	// ************************************************************
	// checks if move is valid
	// Parameters: start_row, start_col - initial position
	// end_row, end_col - end position
	// value: color of piece (1 for black, 2 for white)
	// Returns true or false
	// ************************************************************
	public boolean validMove(int start_row, int start_col, int end_row,
			int end_col, int value) {
		// is start position out of bounds?
		if (start_row < 0)
			return false;
		if (start_row >= size)
			return false;
		if (end_row < 0)
			return false;
		if (end_row >= size)
			return false;

		// is end position out of bounds?
		if (start_col < 0)
			return false;
		if (start_col >= size)
			return false;
		if (end_col < 0)
			return false;
		if (end_col >= size)
			return false;

		// is the piece in the start position?
		if (board[start_row][start_col] != value)
			return false;

		// is the end position empty?
		if (board[end_row][end_col] != 0)
			return false;

		// is the middle position empty?
		if (board[(start_row + end_row) / 2][(start_col + end_col) / 2] == 0)
			return false;

		// are the start and end locations in the correct configuration?
		int dy = Math.abs(start_row - end_row);
		int dx = Math.abs(start_col - end_col);

		if ((dx == 0) && (dy == 2))
			return true;

		if ((dx == 2) && (dy == 0))
			return true;

		return false;
	}

	// ************************************************************
	// return true if location specified by the parameters is empty
	// ************************************************************
	public boolean isEmpty(int row, int col) {
		return (board[row][col] == 0);
	}

	// ************************************************************
	// display board
	// ************************************************************
	public void displayBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				switch (board[i][j]) {
				case 1:
					System.out.print("x ");
					break;
				case 2:
					System.out.print("o ");
					break;
				case 0:
					System.out.print(". ");
					break;
				}
			System.out.println();
		}
	}
}
