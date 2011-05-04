package csc485.pg4.backgammon.gameFramework;

public class Point implements ICheckerSpot 
{
	private Player playerOccupying;
	private int numCheckers;
	private int coords;
	
	//---------constructor---------
	public Point(int coordinates)
	{
		coords = coordinates;
		numCheckers = 0;
		playerOccupying = null;
	}
	
	//----------------------overloaded constructor------------------------
	public Point(int coordinates, Player occupant, int numberOfCheckers)
	{
		coords = coordinates;
		playerOccupying = occupant;
		numCheckers = numberOfCheckers;
	}
	
	public int getCoordinates()
	{
		return coords;
	}
	
	public Player getPlayerOccupying()
	{
		return playerOccupying;
	}
	
	public void setPlayerOccupying(Player aPlayer)
	{
		playerOccupying = aPlayer;
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
		if (numCheckers <= 0)
			playerOccupying = null;
	}

	@Override
	public int getNumOfCheckers() 
	{
		return numCheckers;
	}

}
