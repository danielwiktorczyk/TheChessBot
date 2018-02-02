package theChessBot;

/**
 * 
 * @author Daniel Wiktorczyk
 * 
 * The Board class is the main board of TheChessBot application
 */

public class Board {

	//private Piece[] squares;
	private Piece[][] squares = new Piece[8][8];
	private Piece[][] squaresAtPreviousTurn = new Piece [8][8];
	private boolean isWhitesTurn;
	private boolean flag;
	
	public Board()
	{
		
		//squares = new Piece[64];
		squares[0][0] = new Rook(true);
		squares[1][0] = new Knight(true);
		squares[2][0] = new Bishop(true);
		squares[3][0] = new Queen(true);
		squares[4][0] = new King(true);
		squares[5][0] = new Bishop(true);
		squares[6][0] = new Knight(true);
		squares[7][0] = new Rook(true);
		for ( int i = 0; i < 8; i++) {
			squares[i][1] = new Pawn(true);
		}
		for ( int i = 0; i < 8; i++) {
			for ( int j = 2; j < 5; j++) {
				squares[i][j] = null;
			}
		}
		for ( int i = 0; i < 8; i++) {
			squares[i][6] = new Pawn(false);
		}
		squares[0][7] = new Rook(false);
		squares[1][7] = new Knight(false);
		squares[2][7] = new Bishop(false);
		squares[3][7] = new Queen(false);
		squares[4][7] = new King(false);
		squares[5][7] = new Bishop(false);
		squares[6][7] = new Knight(false);
		squares[7][7] = new Rook(false);

		isWhitesTurn = true;
		
	}
	
	
	
	public void move( String initialPosition , String finalPosition ) {

		// Let's get the casts first....
		
		int initialFile = ((int) initialPosition.charAt(0)) - 65;
		int initialRank = ((int) initialPosition.charAt(1)) - 49;
		int finalFile = ((int) finalPosition.charAt(0)) - 65;
		int finalRank = ((int) finalPosition.charAt(1)) - 49;
		
		// and now the flag for error spotting
		
		flag = false;

		// then ... bunch of checks that describe themselves
		
		if ( initialPosition.length() != 2 || finalPosition.length() != 2 ) {
			System.out.println("Error for location entry (expecting two characters for file and rank)");
		} else if ( initialFile < 0 || initialFile > 7 || finalFile < 0 || finalFile > 7 ) {
			System.out.println("Error for file entry (expecting A to H)");
		} else if ( initialRank < 0 || initialRank > 7 || finalRank < 0 || finalRank > 7 ) {
			System.out.println("Error for rank entry (expecting 1 to 8)");
		} else if ( squares[ initialFile][initialRank] == null ) {
			System.out.println("Error: There is no piece at " +initialPosition+ "!");
		} else if ( squares[initialFile][initialRank].getColor() != isWhitesTurn ) {
			System.out.println("It is not " +(squares[initialFile][initialRank].getColor()?"white":"black")+ "'s turn! Cannot move opponent's piece.");
		} else if ( initialFile == finalFile && initialRank == finalRank ) {
			System.out.println("Error: the piece would not be moving! (Same location)");
		} else if ( squares[finalFile][finalRank] == null // e.g. trying to move
				|| squares[ initialFile][initialRank].getColor() != squares[ finalFile][finalRank].getColor()) { // e.g. trying to attack
	

			/**
			 * Pawn Movement
			 */
			if ( squares[ initialFile][initialRank].getType().equals("Pawn") ) {
				
				movePawn ( initialFile , initialRank , finalFile , finalRank );
				
			}
				
			/**
			 * Rook Movement
			 */
			else if ( squares[initialFile][initialRank].getType().equals("Rook") ) {	
				moveRook ( initialFile , initialRank , finalFile , finalRank );	
			}
			
			/**
			 * Knight Movement
			 */
			else if ( squares[ initialFile][initialRank].getType().equals("Knight") ) {
				moveKnight ( initialFile , initialRank , finalFile , finalRank );
			}
			
			/**
			 * Bishop Movement
			 */
			else if ( squares[ initialFile][initialRank].getType().equals("Bishop") ) {
				moveBishop ( initialFile , initialRank , finalFile , finalRank );		
			}
			
			/**
			 * Queen Movement
			 */
			else if ( squares[ initialFile][initialRank].getType().equals("Queen") ) {
				moveQueen ( initialFile , initialRank , finalFile , finalRank );				
			}
			
			/**
			 * King Movement
			 */
			else if ( squares[ initialFile][initialRank].getType().equals("King") ) {
				moveKing ( initialFile , initialRank , finalFile , finalRank );			
			}
			
		} else if ( squares[ initialFile][initialRank].getColor() == squares[ finalFile][finalRank].getColor() ) {
			System.out.println("Error: Cannot attack one's own piece!");	
		} else {		
			System.out.println("Weird error: not a movement....");
		}		
		// need to research universal movement commands, then translate them into this method's input
	}
	
	/**
	 * Applies movement logic for a pawn
	 * @param initialFile
	 * @param initialRank
	 * @param finalFile
	 * @param finalRank
	 */
	private void movePawn ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		if (squares[ initialFile][initialRank].getColor()) {
			
			// pawn movement for white pawns
			System.out.println("Got here!");

			if ( (initialFile == finalFile && initialRank == finalRank-1)
					|| ( initialFile == finalFile && initialRank == 1 && (finalRank - initialRank) == 2 ) ) { // bottom logic for first move
				System.out.println("Got here too!");
				completeMovement ( initialFile , initialRank , finalFile , finalRank );
				
			} else {
				System.out.println("Illegal pawn movement!");
			}
		} else {
			
			// pawn movement for black pawns
			
			if ( (initialFile == finalFile && initialRank == finalRank+1)
					|| ( initialFile == finalFile && initialRank == 6 && (initialRank - finalRank) == 2 ) ) { // bottom logic for first move
				
				completeMovement ( initialFile , initialRank , finalFile , finalRank );
				
			} else {
				System.out.println("Illegal pawn movement!");
			}
		}
		
	}
	
	/**
	 * Applies movement logic for a Rook
	 * @param initialFile
	 * @param initialRank
	 * @param finalFile
	 * @param finalRank
	 */
	private void moveRook ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		if ( initialFile == finalFile ) {
			//horizontal movement for rook
			if ( initialRank < finalRank ) {
				
				for ( int i = initialRank+1 ; i < finalRank ; i++ ) {
					if (squares[initialFile][i] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("Cannot move rook through another piece");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
									
			} else  if ( initialRank > finalRank){
				
				for ( int i = initialRank-1 ; i > finalRank ; i-- ) {
					if (squares[initialFile][i] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("Cannot move rook through another piece");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
				
			}
			
		} else if ( initialRank == finalRank ) {
			
			//vertical movement for rook
			
			if ( initialFile < finalFile ) {
				
				for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
					if (squares[i][initialRank] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("Cannot move rook through another piece");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
									
			} else  if ( initialFile > finalFile){
				
				for ( int i = initialFile-1 ; i > finalFile ; i-- ) {
					if (squares[i][initialRank] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("Cannot move rook through another piece");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			
			}
			
			
		} else {
			System.out.println("A rook doesn't move like that!");
		}

	}
	
	/**
	 * Applies movement logic for a Knight
	 * @param initialFile
	 * @param initialRank
	 * @param finalFile
	 * @param finalRank
	 */
	private void moveKnight ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		if ( ( Math.abs(initialFile - finalFile)) == Math.abs(initialRank - finalRank) 
				|| Math.abs(initialFile - finalFile) > 2 || Math.abs(initialFile - finalFile) < 1 
				|| Math.abs(initialRank - finalRank) > 2 || Math.abs(initialRank - finalRank) < 1 ) {
			
			System.out.println("A knight doesn't move like that!");
			
		} else {
			completeMovement ( initialFile , initialRank , finalFile , finalRank );
		}
		
	}
	
	/**
	 * Applies movement logic for a bishop
	 * @param initialFile
	 * @param initialRank
	 * @param finalFile
	 * @param finalRank
	 */
	private void moveBishop ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		if ( Math.abs(initialFile - finalFile) != Math.abs(initialRank - finalRank) ) {
			System.out.println("A bishop doesn't move like that!");
		} else if ( initialFile - finalFile < 0) {
			if ( initialRank - finalRank < 0 ) {
				// case 1/4: NE direction
				for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
					if ( squares[i][initialRank+(i-initialFile)] != null ) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A bishop cannot jump over pieces!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
				
			} else {
				// case 2/4: SE direction
				for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
					if ( squares[i][initialRank-(i-initialFile)] != null ) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A bishop cannot jump over pieces!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			}
		} else {
			if ( initialRank - finalRank < 0 ) {
				// case 3/4: NW direction
				for ( int i = initialFile-1 ; i > finalFile ; i-- ) {
					if ( squares[i][initialRank+(initialFile-i)] != null ) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A bishop cannot jump over pieces!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			} else {
				// case 4/4: SW direction
				for ( int i = initialFile-1 ; i > finalFile ; i-- ) {
					if ( squares[i][initialRank-(initialFile-i)] != null ) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A bishop cannot jump over pieces!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			} 
		}
		
	}
	
	/**
	 * Applies movement logic for a queen
	 * @param initialFile
	 * @param initialRank
	 * @param finalFile
	 * @param finalRank
	 */
	private void moveQueen ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		if (Math.abs(finalRank-initialRank) == Math.abs(finalFile-initialFile) ) {
			// diagonal movement
			System.out.println("Diagonal Movement");
			
			
			if ( initialFile - finalFile < 0) {
				if ( initialRank - finalRank < 0 ) {
					// case 1/4: NE direction
					for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
						if ( squares[i][initialRank+(i-initialFile)] != null ) {
							flag = true;
						}
					}
					if (flag) {
						System.out.println("A queen cannot jump over pieces!");
					} else {
						completeMovement ( initialFile , initialRank , finalFile , finalRank );
					}
					
				} else {
					// case 2/4: SE direction
					for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
						if ( squares[i][initialRank-(i-initialFile)] != null ) {
							flag = true;
						}
					}
					if (flag) {
						System.out.println("A queen cannot jump over pieces!");
					} else {
						completeMovement ( initialFile , initialRank , finalFile , finalRank );
					}
				}
			} else {
				if ( initialRank - finalRank < 0 ) {
					// case 3/4: NW direction
					for ( int i = initialFile-1 ; i > finalFile ; i-- ) {
						if ( squares[i][initialRank+(initialFile-i)] != null ) {
							flag = true;
						}
					}
					if (flag) {
						System.out.println("A queen cannot jump over a piece!");
					} else {
						completeMovement ( initialFile , initialRank , finalFile , finalRank );
					}
				} else {
					// case 4/4: SW direction
					for ( int i = initialFile-1 ; i > finalFile ; i-- ) {
						if ( squares[i][initialRank-(initialFile-i)] != null ) {
							flag = true;
						}
					}
					if (flag) {
						System.out.println("A queen cannot jump over a piece!");
					} else {
						completeMovement ( initialFile , initialRank , finalFile , finalRank );
					}
				} 
			}
			
		} else if ( Math.abs(finalRank-initialRank) == 0 ) {
			
			// horizontal movement for queen
			System.out.println("Horizontal Movement");
			
			if ( initialFile < finalFile ) {
				
				for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
					if (squares[i][initialRank] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A queen cannot jump over a piece!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
									
			} else  if ( initialFile > finalFile){
				
				for ( int i = initialFile-1 ; i > finalFile ; i-- ) {
					if (squares[i][initialRank] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A queen cannot jump over a piece!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
				
			}
			
		} else if ( Math.abs(finalFile-initialFile) == 0 ) {
			
			// vertical movement for queen
			System.out.println("Vertical Movmeent ");
								
			if ( initialRank < finalRank ) {
				
				for ( int i = initialRank+1 ; i < finalRank ; i++ ) {
					if (squares[initialFile][i] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A queen cannot jump over a piece!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
									
			} else {
				
				for ( int i = initialRank-1 ; i > finalRank ; i-- ) {
					if (squares[initialFile][i] != null) {
						flag = true;
					}
				}
				if (flag) {
					System.out.println("A queen cannot jump over a piece!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			
			}
			
		} else {
			System.out.println("A queen doesn't move like that!");
		}
			
	}
	
	/**
	 * Applies movement logic for a king
	 * @param initialFile
	 * @param initialRank
	 * @param finalFile
	 * @param finalRank
	 */
	private void moveKing ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		// DOES NOT account for castling yet!
		
		if ( Math.abs(finalRank-initialRank) > 1 || Math.abs(finalFile-initialFile) > 1 ) {
			System.out.println("A king doesn't move like that!");
		} else {
			completeMovement ( initialFile , initialRank , finalFile , finalRank );
		}
		
	}

	private void completeMovement ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		// let's first save the previous position of the board 
		// in the case of a desired "UNDO"
		squaresAtPreviousTurn = squares; 
		
		if ( squares[finalFile][finalRank] == null) {
			System.out.println("Moving!");
			isWhitesTurn = !isWhitesTurn;
			squares[ finalFile ][ finalRank] = squares[ initialFile ][ initialRank ];
			squares[ initialFile ][ initialRank ] = null;
		} else {
			System.out.println("Attacking!");
			isWhitesTurn = !isWhitesTurn;
			squares[ finalFile ][ finalRank] = squares[ initialFile ][ initialRank ];
			squares[ initialFile ][ initialRank ] = null;
			// update Dead Pieces array in the future and other stuff
		}
				
	}



	/**
	 * 
	 * Checks if the given player (white or black) is in check
	 * 
	 * @param isWhite
	 * @return depending on which side is desired to be checked for check, will respond with 
	 * either true of false.
	 */
	private boolean isCheck ( boolean isWhite ) {
		
		return false; // for now
		
	}

	
	/**
	 * Slightly obsolete method... 
	 * @param location
	 * @return
	 */
	public String pieceAt( String location ) {
		
		if ( location.length() != 2) {
			return "Error for location entry (expecting two characters for file and rank)";
		}
		else if ( (int) location.charAt(0) < 65 || (int) location.charAt(0) > 72) {
			return "Error for file entry (expecting A to H)";
		}
		else if ( ((int) location.charAt(1))-48 < 1 || ((int) location.charAt(1))-48 > 8 ) {
			return "Error for rank entry (expecting 1 to 8)";
		} else if ( squares[ ((int) location.charAt(0)) - 65][location.charAt(1) - 49] == null ) {
			return " empty ";
		}
		else {
			return squares[ ((int) location.charAt(0)) - 65][location.charAt(1) - 49].toString();
		}
			
	}
	
	/**
	 * Displays the current board to the console as a simple ASCII map! Cool!
	 */
	public void display() {
		System.out.println("                             ");
		System.out.println( (isWhitesTurn ? "  " : "=>") 
				+"+-A--B--C--D--E--F--G--H-+  ");
		
		for ( int i = 8 ; i > 0 ; i-- ) {
			for ( int j = 0 ; j < 10 ; j++ ) {
				if ( j == 0 ) {
					System.out.print( i + " |");
				} else if ( j == 9) {
					System.out.print( "| " + i );
				} else if ( squares[j-1][i-1] == null )    {	
					
					if ( (i+j) % 2 == 0) {
						System.out.print(":::");
					} else {
						System.out.print("   ");
					}	
					
				} else {
					
					if ( squares[j-1][i-1].getColor() ) {
						System.out.print("<");
					} else {
						System.out.print("[");
					}
					
					if ( squares[j-1][i-1].getType().equals("Pawn") && squares[j-1][i-1].getColor()) {
						System.out.print("P");
					} else if ( squares[j-1][i-1].getType().equals("Pawn") && !squares[j-1][i-1].getColor()) {
						System.out.print("p");
					}
					else if ( squares[j-1][i-1].getType().equals("Rook") && squares[j-1][i-1].getColor()) {
						System.out.print("R");
					} else if ( squares[j-1][i-1].getType().equals("Rook") && !squares[j-1][i-1].getColor()) {
						System.out.print("r");
					}
					else if ( squares[j-1][i-1].getType().equals("Knight") && squares[j-1][i-1].getColor()) {
						System.out.print("N");
					} else if ( squares[j-1][i-1].getType().equals("Knight") && !squares[j-1][i-1].getColor()) {
						System.out.print("n");
					}
					else if ( squares[j-1][i-1].getType().equals("Bishop") && squares[j-1][i-1].getColor()) {
						System.out.print("B");
					} else if ( squares[j-1][i-1].getType().equals("Bishop") && !squares[j-1][i-1].getColor()) {
						System.out.print("b");
					}
					else if ( squares[j-1][i-1].getType().equals("Queen") && squares[j-1][i-1].getColor()) {
						System.out.print("Q");
					} else if ( squares[j-1][i-1].getType().equals("Queen") && !squares[j-1][i-1].getColor()) {
						System.out.print("q");
					}
					else if ( squares[j-1][i-1].getType().equals("King") && squares[j-1][i-1].getColor()) {
						System.out.print("K");
					} else if ( squares[j-1][i-1].getType().equals("King") && !squares[j-1][i-1].getColor()) {
						System.out.print("k");
					}
					
					if ( squares[j-1][i-1].getColor() ) {
						System.out.print(">");
					} else {
						System.out.print("]");
					}
					
				}
				
			}
			System.out.println("");
		}
		System.out.println( (isWhitesTurn ? "=>" : "  ") 
				+"+-A--B--C--D--E--F--G--H-+  ");


		
	}

	/**
	 * Undo undoes the last move applied
	 */
	public void undo ( ) {
		squares = squaresAtPreviousTurn;
	}

	public Piece[][] getSquaresAtPreviousTurn() {
		return squaresAtPreviousTurn;
	}



	public void setSquaresAtPreviousTurn(Piece[][] squaresAtPreviousTurn) {
		this.squaresAtPreviousTurn = squaresAtPreviousTurn;
	}



	public Piece[][] getSquares() {
		return squares;
	}



	public void setSquares(Piece[][] squares) {
		this.squares = squares;
	}



	public boolean isWhitesTurn() {
		return isWhitesTurn;
	}



	public void setWhitesTurn(boolean isWhitesTurn) {
		this.isWhitesTurn = isWhitesTurn;
	}
	
}
