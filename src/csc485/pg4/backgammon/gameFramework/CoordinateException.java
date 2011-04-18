package csc485.pg4.backgammon.gameFramework;

public class CoordinateException extends MoveException 
{
	public CoordinateException()
	{
		super.message = "The coordinate entered was not recognized";
	}
	
	@Override
	public String toString()
	{
		return message;
	}
}
