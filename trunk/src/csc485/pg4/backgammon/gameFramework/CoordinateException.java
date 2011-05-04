package csc485.pg4.backgammon.gameFramework;

public class CoordinateException extends MoveException 
{
	public CoordinateException()
	{
		super.message = "The coordinate or die entered was invalid";
	}
	
	@Override
	public String toString()
	{
		return message;
	}
}
