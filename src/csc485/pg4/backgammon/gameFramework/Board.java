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
		for(int i = 1; i <= numPoints; i++)
		{
			switch(i)
			{
			case 1:
				points[i] = new Point(Integer.toString(i), red, 2);
				break;
			case 6:
				points[i] = new Point(Integer.toString(i), black, 2);
				break;
			case 8:
				points[i] = new Point(Integer.toString(i), black, 3);
				break;
			case 12:
				points[i] = new Point(Integer.toString(i), red, 5);
				break;
			case 13:
				points[i] = new Point(Integer.toString(i), black, 5);
				break;
			case 17:
				points[i] = new Point(Integer.toString(i), red, 3);
				break;
			case 19:
				points[i] = new Point(Integer.toString(i), red, 5);
				break;
			case 24:
				points[i] = new Point(Integer.toString(i), black, 2);
				break;
			default:
				points[i] = new Point(Integer.toString(i));
				break;
			}
		}
	}
	
	@Override
	public String toString()
	{
		//TODO: implement board display
		return "";
	}
	

}
