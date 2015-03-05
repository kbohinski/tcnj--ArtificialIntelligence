//*********************************************
// Arbiter.java         
//
// Konane arbiter. 
// To run: java Arbiter
// it should be pretty self-explanatory
//
// Base code provided by Dr. Salgian
//*********************************************

import java.util.*;
import java.text.NumberFormat;

public class Arbiter {
	
	static String firstName;
	static String secondName;
	
	public static void main(String[] args) {

		int n_games; // number of games played
		int first_wins = 0; // number of wins for first player
		int second_wins = 0; // number of wins for second player
		boolean display; // display the board or not

		// Get info from user
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter board size: ");
		int board_size = scan.nextInt();

		System.out.println("Display board after each move? (y/n)");
		String disp = scan.next();
		display = (disp.toLowerCase()).equals("y");

		System.out.println("How many games? ");
		n_games = scan.nextInt();

		// Start playing games
		for (int k = 0; k < n_games; k++) {
			
			System.out.println(k);

			// initialize board
			Board board = new Board(board_size);

			// initialize players
			// feel free to replace your own types in here
			firstName = "S";
			secondName = "MM";
			// color of piece (1 for black, 2 for white)
			SmartPlayer first = new SmartPlayer(board, 1);
			MinimaxPlayer second = new MinimaxPlayer(board, 2);

			int winner = 0; // no winner yet

			if (display){
				System.out.println("Start:");
				board.displayBoard();
			}
			
			while (winner == 0) {
				// wait, to give the user a chance to read the display
				if (display) {
					System.out.println("");
					System.out.println(firstName + " player's turn");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
				}

				// let first play
				if (first.play()) {
					// he was able to make a move
					if (display) {
						board.displayBoard();
						System.out.println("");
						System.out.println(secondName + " player's turn");
					}
					// wait, to give the user a chance to read the display
					if (display) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
						}
					}

					// let second play
					if (second.play()) {
						// he was able to make a move
						if (display)
							board.displayBoard();
					} else {
						// second couldn't move
						winner = 1;
					}
				} else {
					// first couldn't move
					winner = 2;
				}
			}

			// we have a winner
			if (winner == 1) {
				System.out.println(secondName + " can't move. " + firstName + " wins!");
				first_wins++;
			} else {
				second_wins++;
				System.out.println(firstName + " can't move. " + secondName + " wins!");
			}
		}

		// Print number of wins, formatted
		NumberFormat fmt = NumberFormat.getPercentInstance();
		System.out.println(firstName + " won " + first_wins + " times, that is "
				+ fmt.format((double) first_wins / n_games));
		System.out.println(secondName + " won " + second_wins + " times, that is "
				+ fmt.format((double) second_wins / n_games));

		scan.close();

	}

}
