package csc485.pg4.backgammon.gameFramework;

public class FormatException extends MoveException 
{
	public FormatException()
	{
		super.message = "The input format was not recognized";
	}
	
	@Override
	public String toString()
	{
		return message;
	}
}
