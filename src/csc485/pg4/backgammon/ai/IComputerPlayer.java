package csc485.pg4.backgammon.ai;

public interface IComputerPlayer 
{
	public void startTurn();
	
	public void findAvailableMoves();
	
	public void move();
	
	public void moveFromBar();
	
	public void bearOff();
}
