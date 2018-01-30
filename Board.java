package theChessBot;

/**
 * 
 * @author Daniel Wiktorczyk
 * 
 * The Board class is the main board of TheChessBot application
 */

public class Board {

	private Piece[] squares;
	//private Piece[][] square2;
	
	public Board()
	{
		
		squares = new Piece[64];
		
		squares[0] = new Rook(true);
		squares[1] = new Knight(true);
		squares[2] = new Bishop(true);
		squares[3] = new Queen(true);
		squares[4] = new King(true);
		squares[5] = new Bishop(true);
		squares[6] = new Knight(true);
		squares[7] = new Rook(true);
				
		squares[8] = new Pawn(true);
		squares[9] = new Pawn(true);
		squares[10] = new Pawn(true);
		squares[11] = new Pawn(true);
		squares[12] = new Pawn(true);
		squares[13] = new Pawn(true);
		squares[14] = new Pawn(true);
		squares[15] = new Pawn(true);
		
		for ( int i = 16; i < 56; i++) {
			squares[i] = null;
		}
		
		squares[48] = new Pawn(false);
		squares[49] = new Pawn(false);
		squares[50] = new Pawn(false);
		squares[51] = new Pawn(false);
		squares[52] = new Pawn(false);
		squares[53] = new Pawn(false);
		squares[54] = new Pawn(false);
		squares[55] = new Pawn(false);
		
		squares[56] = new Rook(false);
		squares[57] = new Knight(false);
		squares[58] = new Bishop(false);
		squares[59] = new Queen(false);
		squares[60] = new King(false);
		squares[61] = new Bishop(false);
		squares[62] = new Knight(false);
		squares[63] = new Rook(false);
		
		
		
		
		//pieces2[0][0] = new Pawn(1);
	}
	
	
	
	public void move( int initialPosition , int finalPosition ) {

		if ( squares[initialPosition] == null) {
			System.out.println("Cannot move a piece that's not there!");
		} else if ( squares[initialPosition].getType().equals("Pawn") ) {
			
			/**
			 * Have the Pawn movement logic in here
			 */
			
		} else if ( squares[initialPosition].getType().equals("Rook") ) {
			
			/**
			 * Have the Rook movement logic in here
			 */
			
		} else if ( squares[initialPosition].getType().equals("Knight") ) {
			
			/**
			 * Have the Knight movement logic in here
			 */
			
		} else if ( squares[initialPosition].getType().equals("Bishop") ) {
			
			/**
			 * Have the Bishop movement logic in here
			 */
			
		} else if ( squares[initialPosition].getType().equals("Queen") ) {
			
			/**
			 * Have the Queen movement logic in here
			 */
			
		} else if ( squares[initialPosition].getType().equals("King") ) {
			
			/**
			 * Have the Pawn movement logic in here
			 */
			
		} else  {
			System.out.println("Weird error");
		}
		
	}
	
	public String pieceAt( int location ) {
		if ( location < 0 || location > 63 ) {
			return "Error";
		}
		else if (squares[location] == null) {
			return "no piece";
		} else {
			return squares[location].toString();

		}
			
	}
	
	
	
	
	
}
