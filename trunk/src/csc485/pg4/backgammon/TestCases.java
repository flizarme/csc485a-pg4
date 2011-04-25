package csc485.pg4.backgammon;
import java.util.ArrayList;
import java.util.Iterator;
import csc485.pg4.backgammon.gameFramework.*;

public class TestCases 
{
	public void run()
	{
		//---------Test Cases------------
		
		/*Create objects*/
		Player p1 = new Player("Blake",Checker.Black,false);
		Player p2 = new Player("John", Checker.Red, false);
		ArrayList<MoveException> exceptions = new ArrayList<MoveException>();
		Die d1 = new Die(DieName.D1);
		Die d2 = new Die(DieName.D2);
		Board bTest = new Board(p1, p2);
		FormatException badInput = new FormatException();
		CoordinateException badCoordinate = new CoordinateException();
		NoException noException = new NoException();
		
		/*---------Test Player and Dice Classes-------------*/
		System.out.println(p1.toString());
		d1.roll();
		d2.roll();
		System.out.println("Dice");
		System.out.println("-----");
		System.out.println(d1.toString() + "\n" + d2.toString());
		
		/*---------Test Exception Classes----------------------*/
		System.out.println(badInput.toString() + "\n" + badCoordinate.toString() + "\n" + noException.toString());
		
		exceptions.add(badInput);
		exceptions.add(badCoordinate);
		exceptions.add(noException);
		
		Iterator<MoveException> itr = exceptions.iterator();
		System.out.println();
		
		while(itr.hasNext())
		{
			MoveException curObj = itr.next();
			
			if(curObj instanceof NoException)
			{
				System.out.println("We would display the board since there are no problems");
				System.out.println(bTest.toString());
			}
			else
			{
				System.out.println(curObj.toString());
			}
			
		}
		
	}

}
