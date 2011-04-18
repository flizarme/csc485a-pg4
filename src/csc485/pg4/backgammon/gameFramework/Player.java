package csc485.pg4.backgammon.gameFramework;

public class Player 
{
	private Checker color;
	private String name;
	public boolean isComputer;
	
	public Player(String aName, Checker aColor, boolean aComputer)
	{
		this.name = aName;
		this.color = aColor;
		this.isComputer = aComputer;
	}
	
	//This was not included in our class diagram but I think we may need it
	public Checker getColor()
	{
		return this.color;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String toString()
	{
		return this.name + "(" + this.color + ")";
	}
	
}
