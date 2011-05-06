package csc485.pg4.backgammon.gameFramework;

public class Board 
{
	public Point[] points;
	public Bar redBar;
	public Bar blackBar;
	private final int numPoints = 24;
	private Player red, black;
	
	public Board(Player red, Player black)
	{
		//+2 is one for each spot to bear off
		points = new Point[numPoints+2];
		this.red = red;
		this.black = black;
		redBar = new Bar();
		blackBar = new Bar();
		createBoard();
		
	}
	
	private void createBoard()
	{
		for(int i = 0; i <= numPoints+1; i++)
		{
			/*
			switch(i)
			{
			case 1:
				points[i] = new Point(i, red, 2);
				break;
			case 6:
				points[i] = new Point(i, black, 2);
				break;
			case 8:
				points[i] = new Point(i, black, 3);
				break;
			case 12:
				points[i] = new Point(i, red, 5);
				break;
			case 13:
				points[i] = new Point(i, black, 5);
				break;
			case 17:
				points[i] = new Point(i, red, 3);
				break;
			case 19:
				points[i] = new Point(i, red, 5);
				break;
			case 24:
				points[i] = new Point(i, black, 2);
				break;
			default:
				points[i] = new Point(i);
				break;
			}
			*/
			//setup board for bear off testing
			
			switch(i)
			{
			case 1:
				points[i] = new Point(i, black, 2);
				break;
			case 2:
				points[i] = new Point(i, black, 2);
				break;
			case 3:
				points[i] = new Point(i, black, 3);
				break;
			case 4:
				points[i] = new Point(i, black, 3);
				break;
			case 5:
				points[i] = new Point(i, black, 3);
				break;
			case 6:
				points[i] = new Point(i, black, 3);
				break;
			case 19:
				points[i] = new Point(i, red, 2);
				break;	
			case 20:
				points[i] = new Point(i, red, 2);
				break;
			case 21:
				points[i] = new Point(i, red, 3);
				break;
			case 22:
				points[i] = new Point(i, red, 3);
				break;
			case 23:
				points[i] = new Point(i, red, 3);
				break;
			case 24:
				points[i] = new Point(i, red, 3);
				break;
			default:
				points[i] = new Point(i);
				break;
			}
			
		}
	}
	
	@Override
	public String toString()
	{
		String str = ""; 
		String top = "";
		String bottom = "";
		
		for (int i = 13; i <= 24; i++)
		{
			char color = ' ';
			if(points[i].getPlayerOccupying() == red)
			{
				color = 'r';
			}
			else if(points[i].getPlayerOccupying() == black)
			{
				color = 'b';
			}
				
			top+=" [" + color + points[i].getNumOfCheckers() + "]";
			
			if(i == 18)
				top += "  | ";
		}

		str=   "+------------------------------------------------------------------------+ R Scored\n"+
			   "|  13   14   15   16   17   18   |   19   20   21   22   23   24  | Bbar |" + " [ " + points[25].getNumOfCheckers() + "]\n" + 
			   "|" + top + " | [ " + blackBar.getNumOfCheckers()+ "] | \n";
		str += "|--------------------------------+--------------------------------|      |\n";
		for (int j = 1; j <= 12; j++)
		{
			char color = ' ';
			if(points[j].getPlayerOccupying() == red)
			{
				color = 'r';
			}
			else if(points[j].getPlayerOccupying() == black)
			{
				color = 'b';
			}
				
			bottom= " [" + color + points[j].getNumOfCheckers() + "]" + bottom;
			
			if(j == 6)
				bottom = "  | " + bottom;
		}
		str+= "|" + bottom + " | [ " + redBar.getNumOfCheckers()+ "] | \n" + 
			  "|  12   11   10    9    8    7   |    6    5    4    3    2    1  | Rbar |" + " [ " + points[0].getNumOfCheckers() + "]\n" +
			  "+------------------------------------------------------------------------+  B Scored\n";
		return str;
	}
	

}
