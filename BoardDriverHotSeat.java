package theChessBot;

import java.util.Scanner;

/**
 * 
 * The BoardDriverHotSeat drives the Board class: 
 * It requires the user to enter both player's inputs
 * manually. Not the best for debugging.... 
 * 
 * @author Daniel Wiktorczyk
 *
 */
public class BoardDriverHotSeat {

	public static void main(String[] args) {
		
		Board mainBoard = new Board();
		Scanner keys = new Scanner(System.in);
		String initialLocation="";
		String finalLocation="";
		int moveNumberOfLastTurn = 0;
		boolean go = true;

		System.out.println();
		System.out.print("\\\\~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~//\n" 
				+ "      Welcome to Steve, the Chess Program! \n"
				+ "//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~\\\\\n");
		mainBoard.display();

		while (go) {
			
			moveNumberOfLastTurn = mainBoard.getMoveNumber();
			System.out.println("\nEnter initial location of piece to move:");
			initialLocation = keys.next();
			System.out.println("Enter final location of piece to move:");
			finalLocation = keys.next();
			
			mainBoard.move( initialLocation , finalLocation );
			
			if ( mainBoard.getMoveNumber() != moveNumberOfLastTurn ) {
				mainBoard.display();
			} else {
				System.out.println("\nRemember to input something like A1 or H8, and make "
						+ "\n   sure you know how pieces move!\n");
			}
		
		}

		keys.close();
		
	}
	
}
