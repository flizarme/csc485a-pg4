package csc485.pg4.backgammon.ai;

import csc485.pg4.backgammon.gameFramework.*;

//EasyBot is the simplest possible AI implementation
//He takes the first available move he finds
public class EasyBot implements IComputerPlayer 
{
	Game game;
	
	public EasyBot(Game g)
	{
		game = g;
	}
	
	//Move handles moving a checker. The game class takes care of bearing off for us
	@Override
	public String move() 
	{
		int startingPoint;
		
		if(game.getCurrentPlayer().getColor() == Checker.Red)
		{
			startingPoint = 1;
			MoveException e = null;
			while(!(e instanceof NoException))
			{
				e = game.moveChecker("d1,"+startingPoint);
				
				if(e instanceof NoException)
					break;
				else
				{
					e = game.moveChecker("d2,"+startingPoint);
					if(e instanceof NoException)
						break;
				}
				
				startingPoint++;
			}
		}
		else
		{
			startingPoint = 24;
			MoveException e = null;
			while(!(e instanceof NoException))
			{
				e = game.moveChecker("d1,"+startingPoint);
				
				if(e instanceof NoException)
					break;
				else
				{
					e = game.moveChecker("d2,"+startingPoint);
					if(e instanceof NoException)
						break;
				}
				startingPoint--;
			}
		}
		return game.getCurrentPlayer().getName() + " moved checker at point " + startingPoint;
	}

	@Override
	public String moveFromBar() 
	{
		MoveException e = game.moveChecker("d1,bar");
		
		if(!(e instanceof NoException))
			e = game.moveChecker("d2,bar");
		
		return game.getCurrentPlayer().getName() + " moved a checker from the bar.";
	}

}
