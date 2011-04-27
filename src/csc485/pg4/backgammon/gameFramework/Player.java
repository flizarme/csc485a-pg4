package csc485.pg4.backgammon.gameFramework;

public class Player 
{
	private Checker color;
	private String name;
	public boolean isComputer;
	
	public Player(String aName, boolean aComputer)
	{
		this.name = aName;
		this.isComputer = aComputer;
	}
	
	public void setColor(Checker aColor)
	{
		this.color = aColor;
	}
	
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
