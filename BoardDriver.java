package theChessBot;

import java.util.Scanner;

public class BoardDriver {

	public static void main(String[] args) {
		
		Board mainBoard = new Board();
		Scanner keys = new Scanner(System.in);
		
		
//		System.out.println((int) 'A');
//		System.out.println((int) 'H');
//		System.out.println((int) '1');
//		
//		
//		System.out.println(((int) "A1".charAt(1))-48);
//		
//		System.out.println(mainBoard.pieceAt("A1"));
//		System.out.println(mainBoard.pieceAt("A2"));
//		System.out.println(mainBoard.pieceAt("C4"));
//		System.out.println(mainBoard.pieceAt("H8"));
//		
//		mainBoard.display();
//		
////		mainBoard.move("#2","A3");
////		mainBoard.move("I2","A3");
////		mainBoard.move("A2","A9");
//		
//		mainBoard.move("A2","A3");
//		mainBoard.display();
//		
//		mainBoard.move("A7","A6");
//		mainBoard.display();
//		
////		mainBoard.move("A6","A4");
//
//		
//		mainBoard.display();
//		
//		mainBoard.move("A3","A4");
//		mainBoard.display();
//		
//		mainBoard.move("A1","A3");
//		mainBoard.display();
//		
//		mainBoard.move("A3","A7");
		mainBoard.display();
		
		boolean go = true;
		String initialLocation;
		String finalLocation;
		
		System.out.println();
		System.out.println("Welcome!");
		System.out.println();
		
		while (go) {
			
			System.out.println("Enter initial location of piece to move:");
			initialLocation = keys.next();
			System.out.println("Enter final location of piece to move:");
			finalLocation = keys.next();
			
			mainBoard.move( initialLocation , finalLocation );
			mainBoard.display();
			
			System.out.println("Continue? y / n");
			if (keys.next().equalsIgnoreCase("n"))
				go = false;
			
			
			
		}

		keys.close();
		
	}
	
	
}
