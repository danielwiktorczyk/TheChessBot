package theChessBot;

public class Piece {

	protected int position; // goes from 0 to 63
	protected boolean isWhite;
	
	public Piece ( boolean isWhite) {
		this.isWhite = isWhite;
//		System.out.println("Creating a " +(isWhite?"white":"black")+ " piece");
	}
	
	public String getType () {
		return "Piece";
	}
		
}
