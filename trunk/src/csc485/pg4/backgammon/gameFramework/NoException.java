package csc485.pg4.backgammon.gameFramework;

public class NoException extends MoveException 
{
	public NoException()
	{
		super.message = "No exceptions found";
	}
	
	@Override
	public String toString()
	{
		return message;
	}
}
