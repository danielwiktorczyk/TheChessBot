package theChessBot;

public class Queen extends Piece {

	public Queen ( boolean isWhite) {
		super(isWhite);
		System.out.println("Creating a " +(isWhite?"white":"black")+ " queen");

	}
	
	public String toString () {
		return (isWhite?"white":"black") +" queen";
	}
	
	public String getType () {
		return "Queen";
	}
//	public Queen ( int position ) {
//		this.position = position;
//	}
}
