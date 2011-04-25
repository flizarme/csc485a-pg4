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
		points = new Point[numPoints];
		this.red = red;
		this.black = black;
		redBar = new Bar();
		blackBar = new Bar();
		createBoard();
		
	}
	
	private void createBoard()
	{
		for(int i = 0; i < numPoints; i++)
		{
			switch(i)
			{
			case 0:
				points[i] = new Point(Integer.toString(i+1), red, 2);
				break;
			case 5:
				points[i] = new Point(Integer.toString(i+1), black, 2);
				break;
			case 7:
				points[i] = new Point(Integer.toString(i+1), black, 3);
				break;
			case 11:
				points[i] = new Point(Integer.toString(i+1), red, 5);
				break;
			case 12:
				points[i] = new Point(Integer.toString(i+1), black, 5);
				break;
			case 16:
				points[i] = new Point(Integer.toString(i+1), red, 3);
				break;
			case 18:
				points[i] = new Point(Integer.toString(i+1), red, 5);
				break;
			case 23:
				points[i] = new Point(Integer.toString(i+1), black, 2);
				break;
			default:
				points[i] = new Point(Integer.toString(i+1));
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
		
		for (int i = 12; i < 24; i++)
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
		}

		str="  13   14   15   16   17   18   19   20   21   22   23   24" + '\n' + top +'\n';
		for (int j = 0; j < 12; j++)
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
		}
		str+=bottom + '\n' + "  12   11   10    9    8    7    6    5    4    3    2    1";
		return str;
	}
	

}
