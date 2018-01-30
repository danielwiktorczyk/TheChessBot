package theChessBot;

public class Pawn extends Piece {

	public Pawn ( boolean isWhite) {
		super( isWhite);
		System.out.println("Creating a " +(isWhite?"white":"black")+ " pawn");
		
	}
	
	public String toString () {
		return (isWhite?"white":"black") +" pawn";
	}
	
	public String getType () {
		return "Pawn";
	}
	
//	public Pawn ( int position ) {
//		this.position = position;
//	}
	
}
