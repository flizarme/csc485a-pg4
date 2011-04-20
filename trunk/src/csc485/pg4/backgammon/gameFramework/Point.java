package csc485.pg4.backgammon.gameFramework;

public class Point implements ICheckerSpot 
{
	private Player playerOccupying;
	private int numCheckers;
	private String coords;
	
	//---------constructor---------
	public Point(String coordinates)
	{
		coords = coordinates;
		numCheckers = 0;
	}
	
	//----------overloaded constructor-------------
	public Point(String coordinates, Player occupant)
	{
		coords = coordinates;
		playerOccupying = occupant;
		numCheckers = 0;
	}
	
	//----------------------overloaded constructor------------------------
	public Point(String coordinates, Player occupant, int numberOfCheckers)
	{
		coords = coordinates;
		playerOccupying = occupant;
		numCheckers = numberOfCheckers;
	}
	
	public String getCoordinates()
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
	}

	@Override
	public int getNumOfCheckers() 
	{
		return numCheckers;
	}

}
