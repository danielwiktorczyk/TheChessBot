package theChessBot;

public class Bishop extends Piece{

	public Bishop ( boolean isWhite) {
		super( isWhite );
		//System.out.println("Creating a " +(isWhite?"white":"black")+ " knight");
	}
	
	public String toString () {
		return (isWhite?"white":"black") +" bishop";
	}
	
	public String getType () {
		return "Bishop";
	}
}
