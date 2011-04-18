package csc485.pg4.backgammon.gameFramework;

import java.util.Random;

public class Die 
{
	private DieName name;
	private int value;
	private Random rnd;
	boolean isUsed;
	
	public Die(DieName aName)
	{
		this.name = aName;
		this.value = 0;
		this.isUsed = false;
		rnd = new Random();
	}
	
	//Get a random number from 1 to 6
	public void roll()
	{
		this.value = rnd.nextInt(6) + 1;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	@Override
	public String toString()
	{
		String str = this.name + ": " + this.value;
		return str;
	}
}
