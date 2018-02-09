package theChessBot;

public class King extends Piece {

  public King ( boolean isWhite) {
		super( isWhite );
		//System.out.println("Creating a " +(isWhite?"white":"black")+ " king");
	}
	
	public String toString () {
		return (isWhite?"white":"black") +" king";
	}
	
	public String getType () {
		return "King";
	}

}
