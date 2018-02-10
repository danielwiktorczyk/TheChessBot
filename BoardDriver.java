package theChessBot;

import java.util.Scanner;

public class BoardDriver {

	public static void main(String[] args) {
		
		Board mainBoard = new Board();
		Scanner keys = new Scanner(System.in);
		int moveNumberOfLastTurn = 0;
		boolean go = true;
		String initialLocation;
		String finalLocation;
		
		

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
			}
		
		}

		keys.close();
		
	}
	
	
}
