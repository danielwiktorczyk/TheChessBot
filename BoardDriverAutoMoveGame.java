package theChessBot;

public class BoardDriverAutoMoveGame {

public static void main(String[] args) {
		
		Board mainBoard = new Board();
		
		String initialLocation="";
		String finalLocation="";
		
		int moveNumberOfLastTurn = 0;
		int randomOcto = 0;
		
		int movesAtteptedWithoutSuccess = 0;

		System.out.println();
		System.out.print("\\\\~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~//\n" 
				+ "      Welcome to Steve, the Chess Program! \n"
				+ "//~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~\\\\\n");
		
		mainBoard.display();
		
		System.out.println("Since this is an AUTO game,"
				+ "\n   Sit back and relax!");

		while ( (movesAtteptedWithoutSuccess < 100000) && ( mainBoard.getMoveNumber() < 888 ) ) {
			
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
				mainBoard.display();
				System.out.println("\nResult of move " +initialLocation+ " to " +finalLocation);
				movesAtteptedWithoutSuccess = 0;
			} else {
				movesAtteptedWithoutSuccess += 1;
			}

			initialLocation = "";
			finalLocation = "";
			
		}
		
		if ( mainBoard.getMoveNumber() == 888 ) {
			System.out.println("\n~~~~WE HAVE OURSELVES HERE A STALEMATE!~~~~    ");
		} else if ( !mainBoard.isCheck(mainBoard.getIsWhitesTurn()) ){
			System.out.println("\n~~~~WE HAVE OURSELVES HERE A STALEMATE!~~~~    ");
		} else {
			System.out.println("\n      ~~~> CHECKMATE! <~~~    ");
			System.out.println( "           " +(!mainBoard.getIsWhitesTurn()?"White":"Black") + "wins!" );

		}
			
	}
	
}

