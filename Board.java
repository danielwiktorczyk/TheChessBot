package theChessBot;



/**
 * 
 * @author Daniel Wiktorczyk
 * 
 * The Board class is the main board of TheChessBot application
 */
public class Board {

	private Piece[] square;
	private Piece[][] square2;
	
	public Board()
	{
		square = new Piece[64];
		square[0] = new Rook(1);
		square[1] = new Bishop(2);
		
		//pieces2[0][0] = new Pawn(1);
	}
	
	/**
	 * Pawns
	 */
	private Pawn whitePawn1 = new Pawn(8);
	private Pawn whitePawn2 = new Pawn(9);
	private Pawn whitePawn3 = new Pawn(10);
	private Pawn whitePawn4 = new Pawn(11);
	private Pawn whitePawn5 = new Pawn(12);
	private Pawn whitePawn6 = new Pawn(13);
	private Pawn whitePawn7 = new Pawn(14);
	private Pawn whitePawn8 = new Pawn(15);
	
	private Pawn blackPawn1 = new Pawn(48);
	private Pawn blackPawn2 = new Pawn(49);
	private Pawn blackPawn3 = new Pawn(50);
	private Pawn blackPawn4 = new Pawn(51);
	private Pawn blackPawn5 = new Pawn(52);
	private Pawn blackPawn6 = new Pawn(53);
	private Pawn blackPawn7 = new Pawn(54);
	private Pawn blackPawn8 = new Pawn(55);
	
	/**
	 * Rooks
	 */
	
	private Rook whiteRook1 = new Rook(0);
	private Rook whiteRook2 = new Rook(7);
	
	private Rook blackRook1 = new Rook(56);
	private Rook blackRook2 = new Rook(63);
	
	/**
	 * Knights
	 */
	
	private Piece whiteKnight1 = new Knight(1);
	private Piece whiteKnight2 = new Knight(6);
	
	private Piece blackKnight1 = new Knight(57);
	private Knight blackKnight2 = new Knight(62);
	
	/**
	 * Bishops
	 */
	
	private Bishop whiteBishop1 = new Bishop(2);
	private Bishop whiteBishop2 = new Bishop(5);
	
	private Bishop blackBishop1 = new Bishop(58);
	private Bishop blackBishop2 = new Bishop(61);
	
	/**
	 * The Royal Family
	 */
	
	private Queen whiteQueen = new Queen(3);
	private King whiteKing = new King(4);
	
	private Queen blackQueen = new Queen(59);
	private King blackKing = new King(60);
	
	public void movePiece ( int initialPosition , int finalPosition ) {
		
		
		
	}
	
	
	
	
}
