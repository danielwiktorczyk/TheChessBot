package theChessBot;

import java.util.Scanner;

public class BoardDriverAutoMoveByMove {

public static void main(String[] args) {
		
		Board mainBoard = new Board();
		Scanner keys = new Scanner(System.in);
		
		String initialLocation="";
		String finalLocation="";
		
		int moveNumberOfLastTurn = 0;
		boolean go = true;
		int randomOcto = 0;

		System.out.println();
		System.out.print("\\\\~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~//\n" 
				+ "      Welcome to Steve, the Chess Program! \n"
				+ "//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~\\\\\n");
		
		mainBoard.display();
		
		System.out.println("Since this is a move by move auto game,"
				+ "\n   just press enter to progress the game!");

		while (go) {
			
			moveNumberOfLastTurn = mainBoard.getMoveNumber();
			
			randomOcto = (int)((8*Math.random())+1);
			switch (randomOcto) {
			case 1:  initialLocation += "A";
				break;
			case 2:  initialLocation += "B";
				break;
			case 3:  initialLocation += "C";
				break;
			case 4:  initialLocation += "D";
				break;
			case 5:  initialLocation += "E";
				break;
			case 6:  initialLocation += "F";
				break;
			case 7:  initialLocation += "G";
				break;
			case 8:  initialLocation += "H";
				break;
			default: System.out.println("Wasn't an octo int!");
			
			}
			
			initialLocation += (int)((8*Math.random())+1);
			
			randomOcto = (int)((8*Math.random())+1);
			switch (randomOcto) {
			case 1:  finalLocation += "A";
				break;
			case 2:  finalLocation += "B";
				break;
			case 3:  finalLocation += "C";
				break;
			case 4:  finalLocation += "D";
				break;
			case 5:  finalLocation += "E";
				break;
			case 6:  finalLocation += "F";
				break;
			case 7:  finalLocation += "G";
				break;
			case 8:  finalLocation += "H";
				break;
			default: System.out.println("Wasn't an octo int!");
			
			}
			
			finalLocation += (int)((8*Math.random())+1);
			
			mainBoard.move( initialLocation , finalLocation );
			
			if ( mainBoard.getMoveNumber() != moveNumberOfLastTurn ) {
				go = false;
				mainBoard.display();
				System.out.println("\nResult of move " +initialLocation+ " to " +finalLocation);
				keys.nextLine();
				go = true;

			}
			
			
			
			initialLocation = "";
			finalLocation = "";
			
		}
			
 
		keys.close();
		
	}
	
}
