package csc485.pg4.backgammon.gameFramework;


public class Game 
{
	private Board gameBoard;
	private Player player1, player2;
	private Player currentPlayer;
	private Die d1,d2;
	public boolean isDoubles;
	public boolean isBeginningOfTurn;
	public boolean firstTurnOfGame;
	public boolean canSkip;
	
	public Game(Player p1, Player p2)
	{
		player1 = p1;
		player2 = p2;
		d1 = new Die(DieName.D1);
		d2 = new Die(DieName.D2);
		currentPlayer = null;
		isDoubles = false;
		isBeginningOfTurn = true;
		firstTurnOfGame = true;
		canSkip = false;
	}
	
	public String decideTurnOrder()
	{
		int d1Val,d2Val;
		String msg = "";
	
		do
		{
			rollDice();
			d1Val = d1.getValue();
			d2Val = d2.getValue();
			
			if(d1Val > d2Val)
			{
				msg += player1.getName() + " rolled " + d1Val + "\n";
				msg += player2.getName() + " rolled " + d2Val + "\n";
				msg += player1.getName() + " is red and goes first.\n";
				msg += player2.getName() + " is black and goes second.\n";
				
				player1.setColor(Checker.Red);
				player2.setColor(Checker.Black);
				gameBoard = new Board(player1,player2);
				currentPlayer = player1;
			}
			else if(d1Val < d2Val)
			{
				msg += player1.getName() + " rolled " + d1Val + "\n";
				msg += player2.getName() + " rolled " + d2Val + "\n";
				msg += player2.getName() + " is red and goes first.\n";
				msg += player1.getName() + " is black and goes second.\n";
				
				player2.setColor(Checker.Red);
				player1.setColor(Checker.Black);
				gameBoard = new Board(player2,player1);
				currentPlayer = player2;
			}
			else
			{
				msg += "Doubles rolled. Rolling again.\n";
			}
		}
		while(d1Val == d2Val);

		return msg;
	}
	
	public void swapPlayers()
	{
		if(currentPlayer == player1)
			currentPlayer = player2;
		else
			currentPlayer = player1;
		
		d1.isUsed = false;
		d2.isUsed = false;
	}
	
	public void rollDice()
	{
		d1.roll();
		d2.roll();

		isDoubles = d1.getValue() == d2.getValue();
	}
	
	public String getDice()
	{
		return "die 1 = " + d1.getValue() + "(" + (d1.isUsed ? "used" : "not used") + ")" + "\n" 
				+ "die 2 = " + d2.getValue() + "(" + (d2.isUsed ? "used" : "not used") + ")";
	}
	
	public void resetDice()
	{
		d1.isUsed = false;
		d2.isUsed = false;
	}
	
	public String getBoard()
	{
		return gameBoard.toString();
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public MoveException moveChecker(String coords)
	{
		Die dieToUse;
		Point pointToUse;
		Point destination;
		String[] temp;
		String tempDie;
		int tempPoint,dieVal;
		
		//------Get the user's selected die---------
		temp = coords.split(",");
		tempDie = temp[0];
		
		if(tempDie.equalsIgnoreCase("d1"))
			dieToUse = this.d1;
		else if(tempDie.equalsIgnoreCase("d2"))
			dieToUse = this.d2;
		else
			return new FormatException();
		
		if(dieToUse.isUsed == true)
			return new CoordinateException();
		
		//---------Get the user's selected point--------
		if(temp[1].equalsIgnoreCase("bar"))
		{
			return barMove(dieToUse);
		}
		else
		{
			//---If player has checkers on bar they must type bar-----
			if(currentPlayer.bar.getNumOfCheckers() > 0)
				return new BarException();

			try{tempPoint = Integer.parseInt(temp[1]);}
			//they didn't enter a valid number
			catch(Exception e){return new FormatException();}
			
			try{pointToUse = gameBoard.points[tempPoint];}
			//They entered a number that is not a point
			catch(Exception e){return new FormatException();}
		
			//-----Input was formatted correctly. Now try to move------
			dieVal = dieToUse.getValue();
			
			//-----Make sure the point selected is owned by currentPlayer------
			if(pointToUse.getPlayerOccupying() != this.currentPlayer)
				return new CoordinateException();
			
			//-----Check if the player can Bear off-----
	
			if(currentPlayer.canBearOff=checkBearOff())
				return bearOff(pointToUse, dieToUse, dieVal);
			
			//-----Make sure they choose a destination in bounds-----
			try{
				if(currentPlayer.getColor() == Checker.Red)
					destination = gameBoard.points[pointToUse.getCoordinates() + dieVal];
				else
					destination = gameBoard.points[pointToUse.getCoordinates() - dieVal];
				}
			catch(Exception e){return new CoordinateException();}
			
			if(destination == gameBoard.points[0] || destination == gameBoard.points[25])
				return new CoordinateException();
			
			
			if(destination.getPlayerOccupying() == null || destination.getPlayerOccupying() == currentPlayer)
			{
				pointToUse.removeChecker();
				destination.addChecker();
				destination.setPlayerOccupying(currentPlayer);
				dieToUse.isUsed = true;
			}
			else
				if(destination.getNumOfCheckers() == 1)
				{
					hit(destination, dieToUse);
					pointToUse.removeChecker();
				}
				else
					return new CoordinateException();
		}
		
		
		//----move successful. no exceptions----------
		return new NoException(this.gameBoard);
			
	}
	
	public boolean bothDieUsed()
	{
		return d1.isUsed && d2.isUsed;
	}
	
	private void hit(Point destination, Die dieToUse)
	{
		if(currentPlayer.getColor() == Checker.Red)
		{
			if(player1.getColor()==Checker.Black)
			{
				player1.bar.addChecker();
				player1.onBar=true;
			}
			else
			{
				player2.bar.addChecker();
				player2.onBar=true;
			}
		}
		else
		{
			if(player1.getColor()==Checker.Red)
			{
				player1.bar.addChecker();
				player1.onBar=true;
			}
			else
			{
				player2.bar.addChecker();
				player2.onBar=true;
			}
		}
		destination.setPlayerOccupying(currentPlayer);
		dieToUse.isUsed = true;
	}
	
	public MoveException bearOff(Point pointToUse, Die dieToUse, int dieValue)
	{	
		int tempCoord = pointToUse.getCoordinates();
		Point destination = null;

		if(currentPlayer.getColor() == Checker.Red)
		{
			while(tempCoord >= 19)
			{
				//checks behind pointToMove to make sure there are no required moves behind it.
				if(gameBoard.points[tempCoord-1].getNumOfCheckers() > 0 && dieValue != 25- pointToUse.getCoordinates())
					return new BearoffException();				
				tempCoord--;
			}
			//There are no points before the die value that must be moved.  Player can bear off the piece.
			
			//If the player rolls a value greater than the array and must move a piece closer to the score
			//this prevents an out of bounds error
			if(pointToUse.getCoordinates() + dieValue > 25)
			{
				dieValue-=(pointToUse.getCoordinates() + dieValue - 25);
				destination = gameBoard.points[pointToUse.getCoordinates() + dieValue];
			}
			else
				destination = gameBoard.points[pointToUse.getCoordinates() + dieValue];
			
		}
		else
		{
			while(tempCoord <=6)
			{
				//checks behind pointToMove to make sure there are no required moves behind it
				if(gameBoard.points[tempCoord + 1].getNumOfCheckers() > 0 && dieValue != pointToUse.getCoordinates())
					return new BearoffException();
				tempCoord++;
			}
			
			//There are no points before the die value that must be moved.  Player can bear off a piece
			
			//If the player rolls a value greater than the array and must move a piece closer to the score
			//this prevents an out of bounds error
			if(pointToUse.getCoordinates() < dieValue)
			{
				dieValue=pointToUse.getCoordinates();
				destination = gameBoard.points[pointToUse.getCoordinates() - dieValue];
			}
			else
				destination = gameBoard.points[pointToUse.getCoordinates() - dieValue];
		}
		
		//code from Move
		if(destination.getPlayerOccupying() == null || destination.getPlayerOccupying() == currentPlayer)
		{
			pointToUse.removeChecker();
			destination.addChecker();
			destination.setPlayerOccupying(currentPlayer);
			dieToUse.isUsed = true;
		}
		else
			if(destination.getNumOfCheckers() == 1)
			{
				hit(destination, dieToUse);
				pointToUse.removeChecker();
			}
			else
				return new CoordinateException();
			
		
		
		//----move successful. no exceptions----------
		return new NoException(this.gameBoard);
		
	}
	
	public boolean checkBearOff()
	{
		int tempCoord;
		
		if(currentPlayer.getColor() == Checker.Red)
		{
			tempCoord=18;
			while(tempCoord >= 1)
			{
				//only prevents a bear off if the player has his own pieces outside of the home base
				if(gameBoard.points[tempCoord].getPlayerOccupying()==currentPlayer)
					return false;
				tempCoord--;
			}
			if(currentPlayer.bar.getNumOfCheckers() != 0)
				return false;
		}
		else
		{
			tempCoord=7;
			while(tempCoord <= 24)
			{
				//only prevents a bear off if the player has his own pieces outside of the home base
				if(gameBoard.points[tempCoord].getPlayerOccupying()==currentPlayer)
					return false;
				tempCoord++;
			}
			if(currentPlayer.bar.getNumOfCheckers() != 0)
				return false;
		}
		
		return true;
	}
	
	public boolean canBarMove()
	{
		int targetPoint1 = 0;
		int targetPoint2 = 0;
		boolean p1Valid, p2Valid;
		Point p1,p2;
		//If black rolls a 1 they have to go to point 24, 2 goes to 23, etc.
		if(currentPlayer.getColor() == Checker.Red)
		{
			targetPoint1 = d1.getValue();
			targetPoint2 = d2.getValue();
		}
		else if(currentPlayer.getColor() == Checker.Black)
		{
			targetPoint1 = 25 - d1.getValue();
			targetPoint2 = 25 - d2.getValue();
		}
		
		p1 = gameBoard.points[targetPoint1];
		p2 = gameBoard.points[targetPoint2];
		
		if(p1.getPlayerOccupying() == currentPlayer || p1.getPlayerOccupying() == null || p1.getNumOfCheckers() == 1)
			p1Valid = true;
		else
			p1Valid = false;
		
		if(p2.getPlayerOccupying() == currentPlayer || p2.getPlayerOccupying() == null || p2.getNumOfCheckers() == 1)
			p2Valid = true;
		else
			p2Valid = false;
		
		return p1Valid || p2Valid;
		
	}
	
	public MoveException barMove(Die dieToUse)
	{
		int targetPoint = 0;
		Point p;
		//If black rolls a 1 they have to go to point 24, 2 goes to 23, etc.
		if(currentPlayer.getColor() == Checker.Red)
		{
			targetPoint = dieToUse.getValue();
		}
		else if(currentPlayer.getColor() == Checker.Black)
		{
			targetPoint = 25 - dieToUse.getValue();		
		}
		
		p = gameBoard.points[targetPoint];
		
		if(p.getPlayerOccupying() == currentPlayer || p.getPlayerOccupying() == null || p.getNumOfCheckers() == 1)
		{
			currentPlayer.bar.removeChecker();
			
			if(p.getPlayerOccupying() != currentPlayer && p.getNumOfCheckers() == 1)
				hit(p,dieToUse);
			else
			{
				p.addChecker();
				p.setPlayerOccupying(currentPlayer);
				dieToUse.isUsed = true;
			}
			if(currentPlayer.bar.getNumOfCheckers() == 0)
				currentPlayer.onBar = false;
			
			return new NoException(gameBoard);
		}
		else
		{
			return new CoordinateException();
		}
	}

}
