package theChessBot;

public class Rook extends Piece{
    public Rook ( boolean isWhite) {
        super( isWhite);
        //System.out.println("Creating a " +(isWhite?"white":"black")+ " rook");
      }

      public String toString () {
        return (isWhite?"white":"black") +" rook";
      }

      public String getType () {
        return "Rook";
      }
}
