package theChessBot;

public class King extends Piece {

  public King ( boolean isWhite) {
		super( isWhite );
		//System.out.println("Creating a " +(isWhite?"white":"black")+ " knight");
	}
	
	public String toString () {
		return (isWhite?"white":"black") +" knight";
	}
	
	public String getType () {
		return "Knight";
	}

}
