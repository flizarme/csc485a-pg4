package csc485.pg4.backgammon.gameFramework;

public abstract class MoveException 
{
	protected String message;
	
	@Override
	public String toString()
	{
		return "An exception occurred";
	}
}
