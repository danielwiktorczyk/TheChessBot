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
	public int moveNumber;

	private int[] kingLocation = new int[2];
	
	// attributes for castling rights
	private boolean hasLeftWhiteRookMoved=false;
	private boolean hasRightWhiteRookMoved=false;
	private boolean hasLeftBlackRookMoved=false;
	private boolean hasRightBlackRookMoved=false;
	private boolean hasWhiteKingMoved=false;
	private boolean hasBlackKingMoved=false;

	// attributes for pawn promotion
//	private String promotionType;
	
	
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

		
		// let's get the format right
		
		initialPosition = initialPosition.toUpperCase(); 
		finalPosition = finalPosition.toUpperCase(); 
		
		
		// Let's get the casts first....
		
		int initialFile = ((int) initialPosition.charAt(0)) - 65;
		int initialRank = ((int) initialPosition.charAt(1)) - 49;
		int finalFile = ((int) finalPosition.charAt(0)) - 65;
		int finalRank = ((int) finalPosition.charAt(1)) - 49;
		
		// and now the flag for error spotting
		
		flag = false;

		// then ... bunch of checks that describe themselves
		
		if ( initialPosition.length() != 2 || finalPosition.length() != 2 ) {
			//System.out.println("Error for location entry (expecting two characters for file and rank)");
		} else if ( initialFile < 0 || initialFile > 7 || finalFile < 0 || finalFile > 7 ) {
			//System.out.println("Error for file entry (expecting A to H)");
		} else if ( initialRank < 0 || initialRank > 7 || finalRank < 0 || finalRank > 7 ) {
			//System.out.println("Error for rank entry (expecting 1 to 8)");
		} else if ( squares[ initialFile][initialRank] == null ) {
			//System.out.println("Error: There is no piece at " +initialPosition+ "!");
		} else if ( squares[initialFile][initialRank].getColor() != isWhitesTurn ) {
			//System.out.println("It is not " +(squares[initialFile][initialRank].getColor()?"white":"black")+ "'s turn! Cannot move opponent's piece.");
		} else if ( initialFile == finalFile && initialRank == finalRank ) {
			//System.out.println("Error: the piece would not be moving! (Same location)");
		} else if (squares[finalFile][finalRank] == null) {
			moveTypeSort ( initialFile , initialRank , finalFile , finalRank );
		} else if (squares[finalFile][finalRank] != null) {
			if (isWhitesTurn) {
				if (!squares[finalFile][finalRank].getColor()) {
					moveTypeSort ( initialFile , initialRank , finalFile , finalRank );
				}
			} else {
				if (squares[finalFile][finalRank].getColor()) {
					moveTypeSort ( initialFile , initialRank , finalFile , finalRank );
				}
			}

		} 	
		
	}
	
	private void moveTypeSort ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
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
			if (initialFile == finalFile && initialRank == finalRank-1) { // for standard advancement
				if ( squares[finalFile][finalRank] == null) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			} else if ( initialFile == finalFile && initialRank == 1 && (finalRank - initialRank) == 2 ) { // for double advancement
				if ( squares[finalFile][finalRank] == null && squares[finalFile][finalRank-1] == null ) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			} else if ( (initialFile == (finalFile-1)) && (initialRank == (finalRank-1)) ) { // logic for attacking right
				if ( squares[finalFile][finalRank] != null ) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				} else if ( finalRank < 7
						&& squaresAtPreviousTurn[finalFile][finalRank+1] != null ) { // logic for EN PASSENT right
				
					if ( 
							(squaresAtPreviousTurn[finalFile][finalRank+1].getColor() != isWhitesTurn)
							&& squaresAtPreviousTurn[finalFile][finalRank+1].getType().equals("Pawn") 
							&& squaresAtPreviousTurn[finalFile][finalRank] == null
							&& squaresAtPreviousTurn[finalFile][finalRank-1] == null
							&& squares[finalFile][finalRank-1] != null
							&& (squares[finalFile][finalRank-1].getColor() != isWhitesTurn)
							&& squares[finalFile][finalRank-1].getType().equals("Pawn") ) {
						
						completeEnPassent ( initialFile , initialRank , finalFile , finalRank );
					}
				}
			} else if ( (initialFile == (finalFile+1)) && (initialRank == (finalRank-1)) ) { // logic for attacking left
				if ( squares[finalFile][finalRank] != null ) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				} else if ( finalRank < 7 
						&& squaresAtPreviousTurn[finalFile][finalRank+1] != null ) { // logic for EN PASSENT left
					
					if ( 
							(squaresAtPreviousTurn[finalFile][finalRank+1].getColor() != isWhitesTurn)
							&& squaresAtPreviousTurn[finalFile][finalRank+1].getType().equals("Pawn") 
							&& squaresAtPreviousTurn[finalFile][finalRank] == null
							&& squaresAtPreviousTurn[finalFile][finalRank-1] == null
							&& squares[finalFile][finalRank-1] != null
							&& (squares[finalFile][finalRank-1].getColor() != isWhitesTurn)
							&& squares[finalFile][finalRank-1].getType().equals("Pawn") ) {
						
						completeEnPassent ( initialFile , initialRank , finalFile , finalRank );
					}
				}
			} else {
				//System.out.println("Illegal pawn movement!");
			}
			
		} else {
			
			// pawn movement for black pawns
			if (initialFile == finalFile && initialRank == finalRank+1) { // for standard advancement
				if ( squares[finalFile][finalRank] == null) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			} else if ( initialFile == finalFile && initialRank == 6 && (finalRank - initialRank) == -2 ) { // for double advancement
				if ( squares[finalFile][finalRank] == null && squares[finalFile][finalRank+1] == null ) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			} else if ( (initialFile == (finalFile-1)) && (initialRank == (finalRank+1)) ) { // logic for attacking right
				if ( squares[finalFile][finalRank] != null ) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				} else if ( finalRank > 0
						&& squaresAtPreviousTurn[finalFile][finalRank-1] != null ){
				
					if ( 
							(squaresAtPreviousTurn[finalFile][finalRank-1].getColor() != isWhitesTurn)
							&& squaresAtPreviousTurn[finalFile][finalRank-1].getType().equals("Pawn") 
							&& squaresAtPreviousTurn[finalFile][finalRank] == null
							&& squaresAtPreviousTurn[finalFile][finalRank+1] == null
							&& squares[finalFile][finalRank+1] != null
							&& (squares[finalFile][finalRank+1].getColor() != isWhitesTurn)
							&& squares[finalFile][finalRank+1].getType().equals("Pawn") ) {
						
						completeEnPassent ( initialFile , initialRank , finalFile , finalRank );
					}
				}
			} else if ( (initialFile == (finalFile+1)) && (initialRank == (finalRank+1)) ) { // logic for attacking left
				if ( squares[finalFile][finalRank] != null ) {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				} else if ( finalRank > 0
						&& squaresAtPreviousTurn[finalFile][finalRank-1] != null ){
					
					if ( 
							(squaresAtPreviousTurn[finalFile][finalRank-1].getColor() != isWhitesTurn)
							&& squaresAtPreviousTurn[finalFile][finalRank-1].getType().equals("Pawn") 
							&& squaresAtPreviousTurn[finalFile][finalRank] == null
							&& squaresAtPreviousTurn[finalFile][finalRank+1] == null
							&& squares[finalFile][finalRank+1] != null
							&& (squares[finalFile][finalRank+1].getColor() != isWhitesTurn)
							&& squares[finalFile][finalRank+1].getType().equals("Pawn") ) {
						
						completeEnPassent ( initialFile , initialRank , finalFile , finalRank );
					}
				}
			} else {
				//System.out.println("Illegal pawn movement!");
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
					//System.out.println("Cannot move rook through another piece");
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
					//System.out.println("Cannot move rook through another piece");
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
					//System.out.println("Cannot move rook through another piece");
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
					//System.out.println("Cannot move rook through another piece");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			
			}
			
			
		} else {
			//System.out.println("A rook doesn't move like that!");
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
			
			//System.out.println("A knight doesn't move like that!");
			
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
			//System.out.println("A bishop doesn't move like that!");
		} else if ( initialFile - finalFile < 0) {
			
			if ( initialRank - finalRank < 0 ) {
				
				// case 1/4: NE direction
				for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
					if ( squares[i][initialRank+(i-initialFile)] != null ) {
						flag = true;
					}
				}
				if (flag) {
					//System.out.println("A bishop cannot jump over pieces!");
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
					//System.out.println("A bishop cannot jump over pieces!");
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
					//System.out.println("A bishop cannot jump over pieces!");
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
					//System.out.println("A bishop cannot jump over pieces!");
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
			//System.out.println("Diagonal Movement");
			
			
			if ( initialFile - finalFile < 0) {
				if ( initialRank - finalRank < 0 ) {
					// case 1/4: NE direction
					for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
						if ( squares[i][initialRank+(i-initialFile)] != null ) {
							flag = true;
						}
					}
					if (flag) {
						//System.out.println("A queen cannot jump over pieces!");
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
						//System.out.println("A queen cannot jump over pieces!");
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
						//System.out.println("A queen cannot jump over a piece!");
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
						//System.out.println("A queen cannot jump over a piece!");
					} else {
						completeMovement ( initialFile , initialRank , finalFile , finalRank );
					}
				} 
			}
			
		} else if ( Math.abs(finalRank-initialRank) == 0 ) {
			
			// horizontal movement for queen
			
			if ( initialFile < finalFile ) {
				
				for ( int i = initialFile+1 ; i < finalFile ; i++ ) {
					if (squares[i][initialRank] != null) {
						flag = true;
					}
				}
				if (flag) {
					//System.out.println("A queen cannot jump over a piece!");
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
					//System.out.println("A queen cannot jump over a piece!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
				
			}
			
		} else if ( Math.abs(finalFile-initialFile) == 0 ) {
			
			// vertical movement for queen							
			if ( initialRank < finalRank ) {
				
				for ( int i = initialRank+1 ; i < finalRank ; i++ ) {
					if (squares[initialFile][i] != null) {
						flag = true;
					}
				}
				if (flag) {
					//System.out.println("A queen cannot jump over a piece!");
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
					//System.out.println("A queen cannot jump over a piece!");
				} else {
					completeMovement ( initialFile , initialRank , finalFile , finalRank );
				}
			
			}
			
		} else {
			//System.out.println("A queen doesn't move like that!");
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
		
		
		if ( initialRank == finalRank 
				&& ( initialFile == finalFile+2 || initialFile == finalFile-2 ) 
				&& (initialRank == 0 || initialRank == 7 ) ) {
			//castling
			//System.out.println("You're trying to castle....");
			completeCastling( initialFile , initialRank , finalFile , finalRank );	
		} else if ( Math.abs(finalRank-initialRank) < 2 && Math.abs(finalFile-initialFile) < 2 ) {
			completeMovement ( initialFile , initialRank , finalFile , finalRank );
		} else {
			//System.out.println("A king doesn't move like that!");
		}
		
	}

	private void completeMovement ( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		// let's first save the previous position of the board 
		// in the case of a desired "UNDO"
		for ( int i = 0 ; i < 7 ;  i++ ) {
			for ( int j = 0 ; j < 7 ; j++ ) {
				squaresAtPreviousTurn[i][j] = squares[i][j];
			}
		}
		
		// let's see if it causes a check for the player (e.g. illegal move?)
		
		Piece finalPieceHolder = squares[ finalFile ][ finalRank];
		Piece initialPieceHolder = squares[ initialFile ][ initialRank ];
		squares[ finalFile ][ finalRank] = squares[ initialFile ][ initialRank ];
		squares[ initialFile ][ initialRank ] = null;
		
		if ( isCheck( isWhitesTurn ) ) {
			
			//System.out.println("Cannot complete movement: would be in check!");
			
			squares[ finalFile ][ finalRank] = finalPieceHolder;
			squares[ initialFile ][ initialRank ] = initialPieceHolder;
			
		} else {	
			
			if ( isPawnPromotion( initialFile , initialRank , finalFile , finalRank ) ){
				
				//System.out.println("Pawn promotion! Default: a new queen");
				pawnPromotion( initialFile , initialRank , finalFile , finalRank );
			
			}
			
			castlingRightsUpdate( initialFile , initialRank , finalFile , finalRank );
					
			
			isWhitesTurn = !isWhitesTurn;
			moveNumber += 1;
			if ( isCheck( isWhitesTurn ) ) {
				//System.out.println( "   Check!   ");
				
			}
			
		}
				
	}

	/**
	 * 
	 * Will complete a en passent capture
	 * 
	 * @param initialFile
	 * @param initialRank
	 * @param finalFile
	 * @param finalRank
	 */
	private void completeEnPassent(int initialFile, int initialRank, int finalFile, int finalRank) {

		// let's first save the previous position of the board 
		// in the case of a desired "UNDO"
		for ( int i = 0 ; i < 7 ;  i++ ) {
			for ( int j = 0 ; j < 7 ; j++ ) {
				squaresAtPreviousTurn[i][j] = squares[i][j];
			}
		}
		
		// let's see if it causes a check for the player (e.g. illegal move?)
		Piece initialPieceHolder = squares[ initialFile ][ initialRank ];
		Piece finalPieceHolder = null;
		if ( isWhitesTurn) {
			finalPieceHolder = squares[ finalFile ][ finalRank-1 ];
			squares[ finalFile ][ finalRank-1 ] = null;
		} else {
			finalPieceHolder = squares[ finalFile ][ finalRank+1 ];
			squares[ finalFile ][ finalRank+1 ] = null;
		}
		squares[ finalFile ][ finalRank ] = squares[ initialFile ][ initialRank ];
		squares[ initialFile ][ initialRank ] = null;
		
		if ( isCheck( isWhitesTurn ) ) {
			
			//System.out.println("Cannot complete movement: would be in check!");
			
			squares[ initialFile ][ initialRank ] = initialPieceHolder;
			squares[ finalFile ][ finalRank ] = null;
			if ( isWhitesTurn) {
				squares[ finalFile ][ finalRank-1 ] = finalPieceHolder;
			} else {
				squares[ finalFile ][ finalRank+1 ] = finalPieceHolder;
			}
			
			
		} else {
			////System.out.println("Moving!");
			isWhitesTurn = !isWhitesTurn;
			moveNumber += 1;
			if ( isCheck( isWhitesTurn )) {
				//System.out.println( "   Check!   ");
			}
			
		}
		
	}
	
	private void completeCastling( int initialFile , int initialRank , int finalFile , int finalRank ) {
		
		flag = true;
		
		// let's validate if castling opportunities remain
		
		if ( isWhitesTurn && hasWhiteKingMoved ) {
			flag = false;
		} else if ( !isWhitesTurn && hasBlackKingMoved ) {
			flag = false;
		}
		
		if ( isWhitesTurn && initialFile - finalFile == 2 && !hasWhiteKingMoved) {
			
			if ( (squares[0][0] != null && !squares[0][0].getType().equals("Rook") )
					|| hasLeftWhiteRookMoved 
					|| squares[1][0] != null 
					|| squares[2][0] != null
					|| squares[3][0] != null ) {
				flag = false;
			}
			
		} else if ( isWhitesTurn && finalFile - initialFile == 2 && !hasWhiteKingMoved ) { 
			
			if ( (squares[7][0] != null && !squares[7][0].getType().equals("Rook") )
					|| hasRightWhiteRookMoved 
					|| squares[6][0] != null 
					|| squares[5][0] != null ) {
				flag = false;
			}
			
		} else if ( !isWhitesTurn && initialFile - finalFile == 2 && !hasBlackKingMoved ) { 
			
			if ( (squares[0][7] != null && !squares[0][7].getType().equals("Rook") )
					|| hasLeftBlackRookMoved 
					|| squares[1][7] != null 
					|| squares[2][7] != null
					|| squares[3][7] != null ) {
				flag = false;
			}
			
		} else if ( !isWhitesTurn && finalFile - initialFile == 2 && !hasBlackKingMoved ) { 
			
			if ( (squares[7][7] != null && !squares[7][7].getType().equals("Rook") )
					|| hasRightBlackRookMoved 
					|| squares[6][7] != null 
					|| squares[5][7] != null) {
				flag = false;
			}
			
		}
		
		// now that we confirmed, let's check for checks
		if (flag) {
			
			flag = false;

			// let's see if it causes a check for the player (e.g. illegal move?)
			// ... for where the king is right now
			if ( isCheck(isWhitesTurn) ){
				
				//System.out.println("Cannot castle while in check!");
				
				
			} else {
				
				// let's first save the previous position of the board 
				// in the case of a desired "UNDO"
				for ( int i = 0 ; i < 7 ;  i++ ) {
					
					for ( int j = 0 ; j < 7 ; j++ ) {
						
						squaresAtPreviousTurn[i][j] = squares[i][j];
						
					}
					
				}
				
				// then we check to see if the king would be moving through a check
				
				if ( finalFile - initialFile < 0 ) { // E.G. left side castling
					
					squares[initialFile-1][initialRank] = squares[initialFile][initialRank];
					squares[initialFile][initialRank] = null;
					
					if ( isCheck( isWhitesTurn ) ) {
						
						//System.out.println("Cannot castle through a check!");
						for ( int i = 0 ; i < 7 ;  i++ ) {
							
							for ( int j = 0 ; j < 7 ; j++ ) {
								
								squares[i][j] = squaresAtPreviousTurn[i][j];
								
							}
							
						}
						
					} else {
						
						squares[initialFile-2][initialRank] = squares[initialFile-1][initialRank];
						squares[initialFile-1][initialRank] = null;
						
						if ( isCheck( isWhitesTurn ) ) {
							
							//System.out.println("Cannot castle, would be a check!");
							
							for ( int i = 0 ; i < 7 ;  i++ ) {
								
								for ( int j = 0 ; j < 7 ; j++ ) {
									
									squares[i][j] = squaresAtPreviousTurn[i][j];
									
								}
								
							}
						
						} else {
							
							// perform the castling
							squares[3][initialRank] = squares[0][initialRank];
							squares[0][initialRank] = null;
							
							if ( isWhitesTurn ) {
								hasLeftWhiteRookMoved = true;
								hasRightWhiteRookMoved = true;
								hasWhiteKingMoved = true;
							} else {
								hasLeftBlackRookMoved = true;
								hasRightBlackRookMoved = true;
								hasBlackKingMoved = true;
							}
							
							isWhitesTurn = !isWhitesTurn;
							moveNumber += 1;
							if ( isCheck( isWhitesTurn ) ) {
								//System.out.println( "   Check!   ");
							}
							
						}
						
					} 	
					
				} else { //E.G. right side castling
					
					squares[initialFile+1][initialRank] = squares[initialFile][initialRank];
					squares[initialFile][initialRank] = null;
					
					if ( isCheck( isWhitesTurn ) ) {
						
						//System.out.println("Cannot castle through a check!");
						for ( int i = 0 ; i < 7 ;  i++ ) {
							
							for ( int j = 0 ; j < 7 ; j++ ) {
								
								squares[i][j] = squaresAtPreviousTurn[i][j];
								
							}
							
						}
						
					} else {
						
						squares[initialFile+2][initialRank] = squares[initialFile+1][initialRank];
						squares[initialFile+1][initialRank] = null;
						
						if ( isCheck( isWhitesTurn ) ) {
							
							//System.out.println("Cannot castle, would be a check!");
							
							for ( int i = 0 ; i < 7 ;  i++ ) {
								
								for ( int j = 0 ; j < 7 ; j++ ) {
									
									squares[i][j] = squaresAtPreviousTurn[i][j];
									
								}
								
							}
						
						} else {
							
							// perform the castling
							squares[5][initialRank] = squares[7][initialRank];
							squares[7][initialRank] = null;
							
							if ( isWhitesTurn ) {
								hasLeftWhiteRookMoved = true;
								hasRightWhiteRookMoved = true;
								hasWhiteKingMoved = true;
							} else {
								hasLeftBlackRookMoved = true;
								hasRightBlackRookMoved = true;
								hasBlackKingMoved = true;
							}
							
							
							isWhitesTurn = !isWhitesTurn;
							moveNumber += 1;
							if ( isCheck( isWhitesTurn ) ) {
								//System.out.println( "   Check!   ");
							}
							
						}
						
					} 
					
				}
					
			}		
			
		}
		
	}

	private void castlingRightsUpdate (int initialFile, int initialRank, int finalFile, int finalRank) {
		
		if ( squares[finalFile][finalRank].getType().equals("Rook") 
				&& squares[finalFile][finalRank].getColor() 
				&& initialFile == 0 && initialRank == 0 ) {
			
			hasLeftWhiteRookMoved = true;
			
		}
		
		if ( squares[finalFile][finalRank].getType().equals("Rook") 
				&& squares[finalFile][finalRank].getColor() 
				&& initialFile == 7 && initialRank == 0 ) {
			
			hasRightWhiteRookMoved = true;
			
		}
		
		if ( squares[finalFile][finalRank].getType().equals("Rook") 
				&& !squares[finalFile][finalRank].getColor() 
				&& initialFile == 0 && initialRank == 0 ) {
			
			hasLeftBlackRookMoved = true;
			
		}
		
		if ( squares[finalFile][finalRank].getType().equals("Rook") 
				&& !squares[finalFile][finalRank].getColor() 
				&& initialFile == 7 && initialRank == 0 ) {
			
			hasRightBlackRookMoved = true;
			
		}
		
		if ( squares[finalFile][finalRank].getType().equals("King") 
				&& squares[finalFile][finalRank].getColor() ) {
			
			hasWhiteKingMoved = true;
			
		}
		
		if ( squares[finalFile][finalRank].getType().equals("King") 
				&& !squares[finalFile][finalRank].getColor() ) {
			
			hasBlackKingMoved = true;
			
		}
		
	}
	
	private boolean isPawnPromotion (int initialFile, int initialRank, int finalFile, int finalRank) {
		
		return ( (finalRank == 0 || finalRank == 7 ) && ( squares[finalFile][finalRank].getType().equals("Pawn") ) );
		
	}
	
	private void pawnPromotion (int initialFile, int initialRank, int finalFile, int finalRank) {
		
//		//System.out.println("Too dee too dee! Pawn Promotion! Enter Q for a queen, K for a knight, "
//				+ "B for a bishop, or R for a rook: ");
		squares[finalFile][finalRank] = new Queen(isWhitesTurn); // default for now... 
		
	}
	
	/**
	 * 
	 * Checks if the given player (white or black) is in check
	 * 
	 * @param isWhite
	 * @return depending on which side is desired to be checked for check, will respond with 
	 * either true of false.
	 */
	public boolean isCheck ( boolean isWhite ) {
		
		// let's locate the isWhite king
		if ( isWhite ) {
			
			for ( int i = 0 ; i < 8 ; i++ ) {
				
				for ( int j = 0 ; j < 8 ; j++ ) {
					
					if ( squares[i][j] != null
							&& squares[i][j].getType().equals("King") 
							&& squares[i][j].getColor() ) {
						kingLocation[0] = i ; 
						kingLocation[1] = j ;
						break;
					}
					
				}
			
			}
			
		} else {
			
			for ( int i = 0 ; i < 8 ; i++ ) {
				
				for ( int j = 0 ; j < 8 ; j++ ) {
					
					if ( squares[i][j] != null
							&& squares[i][j].getType().equals("King") 
							&& !squares[i][j].getColor() ) {
						kingLocation[0] = i ; 
						kingLocation[1] = j ;
						break;
					}
					
				}
			
			}
			
		}
		
		// now let's check for any attacking knights....
		if ( isCheckByKnight( isWhite ) )
			flag = true ;
		
		// ... attacking rooks, bishops or queens
		if ( isCheckBySlider( isWhite ) ) {
			flag = true ;
		}
		
		// ... attacking pawns
		if ( isCheckByPawn( isWhite ) ) {
			flag = true ;
		}
		
		// ... another king too close for comfort
		if ( isCheckByKing( ) ) {
			flag = true ;
		}
		
		if (flag)
			return true;
		else return false;
		
	}
	
	private boolean isCheckByKnight ( boolean isWhite ) {
		
		// for attacking knights far left of the king
		if ( kingLocation[0] - 2 >= 0 ) {
			
			// ... and above
			if ( kingLocation[1] + 1 <= 7 ) {
				
				if ( squares[ kingLocation[0] - 2 ][kingLocation[1] + 1 ] != null
						&& squares[ kingLocation[0] - 2 ][kingLocation[1] + 1 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] - 2 ][kingLocation[1] + 1 ].getColor() != isWhite ) {
				
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			} 
			
			// ... and below
			if ( kingLocation[1] - 1 >= 0 ) {
				
				if ( squares[ kingLocation[0] - 2 ][kingLocation[1] - 1 ] != null
						&& squares[ kingLocation[0] - 2 ][kingLocation[1] - 1 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] - 2 ][kingLocation[1] - 1 ].getColor() != isWhite ) {
					
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			}	
			
		}
		
		// for attacking knights far right of the king
		if ( kingLocation[0] + 2 <= 7 ) {
			
			// ... and above
			if ( kingLocation[1] + 1 <= 7 ) {
				
				if ( squares[ kingLocation[0] + 2 ][kingLocation[1] + 1 ] != null
						&& squares[ kingLocation[0] + 2 ][kingLocation[1] + 1 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] + 2 ][kingLocation[1] + 1 ].getColor() != isWhite ) {
				
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			}
			
			// ... and below
			if ( kingLocation[1] - 1 >= 0 ) {
				
				if ( squares[ kingLocation[0] + 2 ][kingLocation[1] - 1 ] != null
						&& squares[ kingLocation[0] + 2 ][kingLocation[1] - 1 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] + 2 ][kingLocation[1] - 1 ].getColor() != isWhite ) {
					
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			}	
			
		}
		
		// for attacking knights above the king
		if ( kingLocation[1] + 2 <= 7 ) {
			
			// ... below and to the right
			if ( kingLocation[0] + 1 <= 7 ) {
				
				if ( squares[ kingLocation[0] + 1 ][kingLocation[1] + 2 ] != null
						&& squares[ kingLocation[0] + 1 ][kingLocation[1] + 2 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] + 1 ][kingLocation[1] + 2 ].getColor() != isWhite ) {
				
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			} 
			// ... below and to the left
			if ( kingLocation[0] - 1 >= 0 ) {
				
				if ( squares[ kingLocation[0] - 1 ][kingLocation[1] + 2 ] != null
						&& squares[ kingLocation[0] - 1 ][kingLocation[1] + 2 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] - 1 ][kingLocation[1] + 2 ].getColor() != isWhite ) {
				
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			}	
			
		}
		
		// for attacking knights below the king
		if ( kingLocation[1] - 2 >= 0 ) {
			
			// ... below and to the right
			if ( kingLocation[0] + 1 <= 7 ) {
				
				if ( squares[ kingLocation[0] + 1 ][kingLocation[1] - 2 ] != null
						&& squares[ kingLocation[0] + 1 ][kingLocation[1] - 2 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] + 1 ][kingLocation[1] - 2 ].getColor() != isWhite ) {
				
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			} 
			// ... below and to the left
			if ( kingLocation[0] - 1 >= 0 ) {
				
				if ( squares[ kingLocation[0] - 1 ][kingLocation[1] - 2 ] != null
						&& squares[ kingLocation[0] - 1 ][kingLocation[1] - 2 ].getType().equals("Knight") 
						&& squares[ kingLocation[0] - 1 ][kingLocation[1] - 2 ].getColor() != isWhite ) {
				
					////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky knight!");
					return true;
					
				}
				
			}	
			
		}
		
		return false;
		
	}

	private boolean isCheckBySlider ( boolean isWhite ) {
		
		// let's check the left side of the king
		for ( int i = 1 ; kingLocation[0] - i >= 0 ; i++ ) {
			if ( squares[ kingLocation[0]-i ][ kingLocation[1] ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0]-i ][ kingLocation[1] ].getType().equals("Rook") 
					&& squares[ kingLocation[0]-i ][ kingLocation[1] ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky rook!");
				return true;
			} else if ( squares[ kingLocation[0]-i ][ kingLocation[1] ].getType().equals("Queen") 
					&& squares[ kingLocation[0]-i ][ kingLocation[1] ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}
			
		// let's check the right side of the king
		for ( int i = 1 ; kingLocation[0] + i <= 7 ; i++ ) {
			if ( squares[ kingLocation[0]+i ][ kingLocation[1] ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0]+i ][ kingLocation[1] ].getType().equals("Rook") 
					&& squares[ kingLocation[0]+i ][ kingLocation[1] ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky rook!");
				return true;
			} else if ( squares[ kingLocation[0]+i ][ kingLocation[1] ].getType().equals("Queen") 
					&& squares[ kingLocation[0]+i ][ kingLocation[1] ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}	
		
		// let's check above the king
		for ( int i = 1 ; kingLocation[1] + i <= 7 ; i++ ) {
			if ( squares[ kingLocation[0] ][ kingLocation[1]+i ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0] ][ kingLocation[1]+i ].getType().equals("Rook") 
					&& squares[ kingLocation[0] ][ kingLocation[1]+i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky rook!");
				return true;
			} else if ( squares[ kingLocation[0] ][ kingLocation[1]+i ].getType().equals("Queen") 
					&& squares[ kingLocation[0] ][ kingLocation[1]+i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}	
		
		// let's check below the king
		for ( int i = 1 ; kingLocation[1] - i >= 0 ; i++ ) {
			if ( squares[ kingLocation[0] ][ kingLocation[1]-i ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0] ][ kingLocation[1]-i ].getType().equals("Rook") 
					&& squares[ kingLocation[0] ][ kingLocation[1]-i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky rook!");
				return true;
			} else if ( squares[ kingLocation[0] ][ kingLocation[1]-i ].getType().equals("Queen") 
					&& squares[ kingLocation[0] ][ kingLocation[1]-i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}	
		
		// let's check the NW diagonal 
		for ( int i = 1 ; (kingLocation[0]-i >= 0) && (kingLocation[1]+i <= 7) ; i++ ) {
			if ( squares[ kingLocation[0]-i ][ kingLocation[1]+i ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0]-i ][ kingLocation[1]+i ].getType().equals("Bishop") 
					&& squares[ kingLocation[0]-i ][ kingLocation[1]+i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky bishop!");
				return true;
			} else if ( squares[ kingLocation[0]-i ][ kingLocation[1]+i ].getType().equals("Queen") 
					&& squares[ kingLocation[0]-i ][ kingLocation[1]+i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}	
		
		// let's check the NE diagonal 
		for ( int i = 1 ; (kingLocation[0]+i <= 7 ) && (kingLocation[1]+i <= 7) ; i++ ) {
			if ( squares[ kingLocation[0]+i ][ kingLocation[1]+i ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0]+i ][ kingLocation[1]+i ].getType().equals("Bishop") 
					&& squares[ kingLocation[0]+i ][ kingLocation[1]+i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky bishop!");
				return true;
			} else if ( squares[ kingLocation[0]+i ][ kingLocation[1]+i ].getType().equals("Queen") 
					&& squares[ kingLocation[0]+i ][ kingLocation[1]+i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}	
		
		// let's check the SE diagonal 
		for ( int i = 1 ; (kingLocation[0]+i <= 7 ) && (kingLocation[1]-i >= 0 ) ; i++ ) {
			if ( squares[ kingLocation[0]+i ][ kingLocation[1]-i ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0]+i ][ kingLocation[1]-i ].getType().equals("Bishop") 
					&& squares[ kingLocation[0]+i ][ kingLocation[1]-i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky bishop!");
				return true;
			} else if ( squares[ kingLocation[0]+i ][ kingLocation[1]-i ].getType().equals("Queen") 
					&& squares[ kingLocation[0]+i ][ kingLocation[1]-i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}	
		
		// let's check the SW diagonal 
		for ( int i = 1 ; (kingLocation[0]-i >= 0 ) && (kingLocation[1]-i >= 0 ) ; i++ ) {
			if ( squares[ kingLocation[0]-i ][ kingLocation[1]-i ] == null ) {
				continue;
			} else if ( squares[ kingLocation[0]-i ][ kingLocation[1]-i ].getType().equals("Bishop") 
					&& squares[ kingLocation[0]-i ][ kingLocation[1]-i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky bishop!");
				return true;
			} else if ( squares[ kingLocation[0]-i ][ kingLocation[1]-i ].getType().equals("Queen") 
					&& squares[ kingLocation[0]-i ][ kingLocation[1]-i ].getColor() != isWhite ) {
				////System.out.println("Check on the " +(isWhite?"white":"black")+ " king by a pesky queen!");
				return true;
			} else {
				break;
			}
		}	
		
		return false;
		
	}
	
	private boolean isCheckByPawn ( boolean isWhite ) {
		
		if ( isWhite ) {
			
			if ( kingLocation[1]+1 <= 7 ) {
				
				if ( kingLocation[0]-1 >= 0
						&& squares[ kingLocation[0]-1 ][ kingLocation[1]+1 ] != null 
						&& squares[ kingLocation[0]-1 ][ kingLocation[1]+1 ].getType().equals("Pawn")
						&& squares[ kingLocation[0]-1 ][ kingLocation[1]+1 ].getColor() != isWhite ) {
					
					////System.out.println("Check by a pesky pawn");
					return true;
					
				}
				
				if ( kingLocation[0]+1 <= 7 
						&& squares[ kingLocation[0]+1 ][ kingLocation[1]+1 ] != null 
						&& squares[ kingLocation[0]+1 ][ kingLocation[1]+1 ].getType().equals("Pawn")
						&& squares[ kingLocation[0]+1 ][ kingLocation[1]+1 ].getColor() != isWhite ) {
					
					////System.out.println("Check by a pesky pawn");
					return true;
					
				}
				
			}
				
		} else {
			
			if ( kingLocation[1]-1 >= 0 ) {
				
				if ( kingLocation[0]-1 >= 0
						&& squares[ kingLocation[0]-1 ][ kingLocation[1]-1 ] != null 
						&& squares[ kingLocation[0]-1 ][ kingLocation[1]-1 ].getType().equals("Pawn")
						&& squares[ kingLocation[0]-1 ][ kingLocation[1]-1 ].getColor() != isWhite ) {
					
					////System.out.println("Check by a pesky pawn");
					return true;
					
				}
				
				if ( kingLocation[0]+1 <= 7 
						&& squares[ kingLocation[0]+1 ][ kingLocation[1]-1 ] != null 
						&& squares[ kingLocation[0]+1 ][ kingLocation[1]-1 ].getType().equals("Pawn")
						&& squares[ kingLocation[0]+1 ][ kingLocation[1]-1 ].getColor() != isWhite ) {
					
					////System.out.println("Check by a pesky pawn");
					return true;
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
	private boolean isCheckByKing (  ) {
		
		
		if ( kingLocation[0]-1 >= 0 ) { //check 3 squares on left
			
			// check above, below, and immediate left
			if ( kingLocation[1]+1 <= 7 
					&& squares[ kingLocation[0]-1 ][ kingLocation[1]+1 ] != null 
					&& squares[ kingLocation[0]-1 ][ kingLocation[1]+1 ].getType().equals("King")) {
				
				////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
				return true;
				
			} else if ( kingLocation[1]-1 >= 0 
					&& squares[ kingLocation[0]-1 ][ kingLocation[1]-1 ] != null 
					&& squares[ kingLocation[0]-1 ][ kingLocation[1]-1 ].getType().equals("King")) {
				
				////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
				return true;
				
			} else if ( squares[ kingLocation[0]-1 ][ kingLocation[1] ] != null 
					&& squares[ kingLocation[0]-1 ][ kingLocation[1] ].getType().equals("King")) {
				
				////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
				return true;
				
			}
			
		} 
		
		if ( kingLocation[0]+1 <= 7 ) { //check 3 squares on right
			
			// check above, below, and immediate right
			if ( kingLocation[1]+1 <= 7 
					&& squares[ kingLocation[0]+1 ][ kingLocation[1]+1 ] != null 
					&& squares[ kingLocation[0]+1 ][ kingLocation[1]+1 ].getType().equals("King")) {
				
				////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
				return true;
				
			} else if ( kingLocation[1]-1 >= 0 
					&& squares[ kingLocation[0]+1 ][ kingLocation[1]-1 ] != null 
					&& squares[ kingLocation[0]+1 ][ kingLocation[1]-1 ].getType().equals("King")) {
				
				////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
				return true;
				
			} else if ( squares[ kingLocation[0]+1 ][ kingLocation[1] ] != null 
					&& squares[ kingLocation[0]+1 ][ kingLocation[1] ].getType().equals("King")) {
				
				////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
				return true;
				
			}
			
		} 
		
		// check 2 squares above and below
			
		// check above, below
		if ( kingLocation[1]+1 <= 7 
				&& squares[ kingLocation[0] ][ kingLocation[1]+1 ] != null 
				&& squares[ kingLocation[0] ][ kingLocation[1]+1 ].getType().equals("King")) {
			
			////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
			return true;
			
		} else if ( kingLocation[1]-1 >= 0 
				&& squares[ kingLocation[0] ][ kingLocation[1]-1 ] != null 
				&& squares[ kingLocation[0] ][ kingLocation[1]-1 ].getType().equals("King")) {
			
			////System.out.println("Kings are getting too talkitive (Can't be next to eachother)!");
			return true;
			
		}
			
		return false;
				
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
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println( (isWhitesTurn ? "  " : "=>") 
				+"+-A--B--C--D--E--F--G--H-+     Move: " +moveNumber);
		
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
				+"+-A--B--C--D--E--F--G--H-+  "+ (isCheck(isWhitesTurn)?"   Check!":"   ") );
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

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



	public boolean getIsWhitesTurn() {
		return isWhitesTurn;
	}



	public void setWhitesTurn(boolean isWhitesTurn) {
		this.isWhitesTurn = isWhitesTurn;
	}
	
	public int getMoveNumber() {
		return moveNumber;
	}


	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}
	
}

