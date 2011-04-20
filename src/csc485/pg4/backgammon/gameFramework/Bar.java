package csc485.pg4.backgammon.gameFramework;

public class Bar implements ICheckerSpot 
{
	private int numCheckers;
	
	public Bar()
	{
		numCheckers = 0;
	}

	@Override
	public void addChecker() 
	{
		numCheckers++;
	}

	@Override
	public void removeChecker() 
	{
		numCheckers--;
	}

	@Override
	public int getNumOfCheckers() 
	{
		return numCheckers;
	}

}
