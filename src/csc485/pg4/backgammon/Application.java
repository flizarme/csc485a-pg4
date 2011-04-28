package csc485.pg4.backgammon;

import csc485.pg4.backgammon.gameFramework.*;

import java.io.IOException;
import java.util.Scanner;

public class Application 
{
	private Scanner in;
	private Game game;
	
	public void run() throws IOException
	{
		String choice;
		in = new Scanner(System.in);
		
		System.out.println("xxxxxxxxxxxxxxxxxx");
		System.out.println("x   Backgammon   x");
		System.out.println("xxxxxxxxxxxxxxxxxx");
		System.out.println();
		
	//--------------Before Game Has Started----------------------
		
		startNewGame();
		
	//-----------After game has started---------------------------
		
		do
		{
			//If it is the beginning of a players turn display the board
			//and prompt them to roll the dice.
			if(game.isBeginningOfTurn)
			{
				System.out.println("Current Player: " + game.getCurrentPlayer().toString());
				System.out.println(game.getBoard());
				System.out.println("Press enter to roll dice:");
				System.in.read();
				System.out.println(game.rollDice());
				game.isBeginningOfTurn = false;
			}
			
			//Get users input. First check for a known command entered. If no known
			//command is detected then assume they entered die/coords for a move
			//and attempt to play it. Exceptions will handle the rest when we get
			//there
			
			choice = getInput();
			
			if(choice.equals("quit"))
			{
				System.out.println("Quitting...");
				System.exit(0);
			}
			else if(choice.equals("new"))
				startNewGame();
			else if(choice.equals("help") || choice.equals("?"))
				displayHelp();
			else if(choice.equals("undo"))
				//TODO: write game.undo() code
				continue;//temporary
			else
				continue;//(this is temporary to get rid of errors)
				//TODO: enter code that attempts a move
				//After players are swapped set isBeginningOfTurn to True
			
		}
		while(!choice.equals("quit"));
	}
	
	//Starts a new game by getting player names and determining order
	private void startNewGame()
	{
		int numPlayers;
		int choice;
		
		//Prompt user to start a new game or to quit.
		//If new game is chosen have them enter number of human players
		//If invalid input is entered repeat
		
		do
		{
			System.out.println("+-------------+");
			System.out.println("|0: Quit      |");
			System.out.println("|1: New Game  |");
			System.out.println("+-------------+");
			
			System.out.print("\n>");
			choice = in.nextInt();
			
			switch(choice)
			{
			case 0:
				System.out.println("Quitting...");
				System.exit(0);
				break;
			case 1:
				numPlayers = getNumPlayers();
				if(numPlayers == 1)
				{
					game = new Game(new Player(getName(1),false), new Player("cpu",true));
					//TODO: Computer player stuff
				}
				else if(numPlayers == 2)
				{
					game = new Game(new Player(getName(1),false),new Player(getName(2),false));
				}
				break;
			default:
				System.out.println("Invalid command.");
				break;
			}
		}
		while(choice != 0 && choice != 1);
		
		//Now decide who will go first
		System.out.println(game.decideTurnOrder());
		
		System.out.println();
		System.out.println("Type help or ? at any time for a list of commands");
		System.out.println();
	}
	
	//Gets user's next input
	private String getInput()
	{
		System.out.print(">");
		return in.next();
	}
	
	//Gets the number of human players that will be playing
	private int getNumPlayers()
	{
		int selection;
		do
		{
			System.out.println("How many players will be playing?");
			System.out.println("(Enter 1 or 2)");
			System.out.print(">");
			selection = in.nextInt();
			
			if(selection != 1 && selection != 2)
				System.out.println("Invalid input!");
		}
		while(selection != 1 && selection != 2);
		
		return selection;
	}
	
	//method used to get player names
	private String getName(int playerNum)
	{
		System.out.print("Player " + playerNum + " - please enter your name: ");
		return in.next();
	}
	
	//help menu to explain how to enter coordinates
	private void displayHelp()
	{
		System.out.println("quit - exit the application");
		System.out.println("new - start a new game");
		System.out.println("undo - undo last action");
		System.out.println("To move a checker use this format: <die>,<point>");
		System.out.println("ex. To use die1 and move the checker at point 3");
		System.out.println("you would type d1,3");
	}
}
