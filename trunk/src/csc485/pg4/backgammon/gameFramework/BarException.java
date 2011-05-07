package csc485.pg4.backgammon.gameFramework;

public class BarException extends MoveException 
{
	public BarException()
	{
		super.message = "You must move all checkers on the bar.";
	}
	
	@Override
	public String toString()
	{
		return message;
	}
}
