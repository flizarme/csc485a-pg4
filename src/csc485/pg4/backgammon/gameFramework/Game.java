package csc485.pg4.backgammon.gameFramework;


public class Game 
{
	private Board gameBoard;
	private Player player1, player2;
	private Player currentPlayer;
	private Die d1,d2;
	private boolean isDoubles;
	public boolean isBeginningOfTurn;
	public boolean firstTurnOfGame;
	
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
		try{tempPoint = Integer.parseInt(temp[1]);}
		//they didn't enter a valid number
		catch(Exception e){return new FormatException();}
		
		try{pointToUse = gameBoard.points[tempPoint];}
		//They entered a number that is not a point
		catch(Exception e){return new FormatException();}
			
		//-----Input was formatted correctly. Now try to move------
		dieVal = dieToUse.getValue();
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
				hit(pointToUse, destination, dieToUse);
			else
				return new CoordinateException();
			
		
		
		//----move successful. no exceptions----------
		return new NoException(this.gameBoard);
			
	}
	
	public boolean bothDieUsed()
	{
		return d1.isUsed && d2.isUsed;
	}
	
	private void hit(Point pointToUse, Point destination, Die dieToUse)
	{
		pointToUse.removeChecker();
		
		if(currentPlayer.getColor() == Checker.Red)
		{
			//if red made the hit black is the one getting moved to the bar
			gameBoard.blackBar.addChecker();
			if(player1.getColor()==Checker.Black)
				player1.onBar=true;
			else
				player2.onBar=true;
		}
		else
		{
			//if black made the hit red is getting moved to the bar
			gameBoard.redBar.addChecker();
			if(player1.getColor()==Checker.Red)
				player1.onBar=true;
			else
				player2.onBar=true;
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
					return new CoordinateException();				
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
					return new CoordinateException();
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
				hit(pointToUse, destination, dieToUse);
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
			if(gameBoard.redBar.getNumOfCheckers() != 0)
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
			if(gameBoard.blackBar.getNumOfCheckers() != 0)
				return false;
		}
		
		return true;
	}
	
	public void barMove()
	{
		//TODO: Game.barMove()
	}
	
	public void undo()
	{
		//TODO: Game.undo()
	}

}
