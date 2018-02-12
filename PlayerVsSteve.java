package theChessBot;

import java.util.Scanner;

public class PlayerVsSteve {

	public static void main(String[] args) {
		
		System.out.print("\\\\~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~//\n" 
				+ "          Hi, I'm Steve: The Chess Playing Computer \n"
				+ "//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~\\\\\n\n");
		
		Board mainBoard = new Board();
		Scanner keys = new Scanner(System.in);
		String initialLocation="";
		String finalLocation="";
		int moveNumberOfLastTurn = 0;
		boolean go = true;
		boolean nextTurn = true;
		int randomOcto = 0;
		
		
		mainBoard.display();
		
		System.out.println("I'll let you be first....\n");
		
		while (nextTurn) {
			
			go = true; 
			
			// user input for WHITE
			while (go) {
				
				moveNumberOfLastTurn = mainBoard.getMoveNumber();
				System.out.println("\nEnter initial location of piece to move:");
				initialLocation = keys.next();
				System.out.println("Enter final location of piece to move:");
				finalLocation = keys.next();
				
				mainBoard.move( initialLocation , finalLocation );
				
				if ( mainBoard.getMoveNumber() != moveNumberOfLastTurn ) {
					mainBoard.display();
					go = false;
				} else {
					System.out.println("\nRemember to input something like A1 or H8, and make "
							+ "\n   sure you know how pieces move!\n");
				}
			
			}
			
			go = true;
	
			// Steve makes the response
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
					System.out.println("My turn!\n");
					mainBoard.display();
					go = false;
					System.out.println("\nResult of move " +initialLocation+ " to " +finalLocation);
	
				}

				initialLocation = "";
				finalLocation = "";
				
			}
			
			
			
			
		}

		keys.close();
		
	}
	
}
