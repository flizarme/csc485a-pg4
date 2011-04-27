package csc485.pg4.backgammon.gameFramework;

public class Game 
{
	private Board gameBoard;
	private Player player1, player2;
	public Player currentPlayer;
	private Die d1,d2;
	private boolean isDoubles, canBearOff;
	public boolean isBeginningOfTurn;
	
	public Game(Player p1, Player p2)
	{
		player1 = p1;
		player2 = p2;
		d1 = new Die(DieName.D1);
		d2 = new Die(DieName.D2);
		currentPlayer = null;
		gameBoard = new Board(p1,p2);
		isDoubles = false;
		canBearOff = false;
		isBeginningOfTurn = true;
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
	}
	
	public String rollDice()
	{
		d1.roll();
		d2.roll();

		if(d1.getValue() == d2.getValue())
			isDoubles = true;
		else
			isDoubles = false;
		
		return "die 1 = " + d1.getValue() + "\n" + "die 2 = " + d2.getValue();
	}
	
	public String getBoard()
	{
		return gameBoard.toString();
	}
	
	public void moveChecker()
	{
		//TODO: Game.moveChecker()
	}
	
	private void hit()
	{
		//TODO: Game.hit()
	}
	
	public void bearOff()
	{
		//TODO: Game.bearOff()
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
