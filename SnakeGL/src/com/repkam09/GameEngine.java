package com.repkam09;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine {

	private static long lastTick;
	private static long currentTick;

	public static boolean isGameRunning = false;

	public static final int GAME_BOARD_HEIGHT = 600;
	public static final int GAME_BOARD_WIDTH = 800;
	public static final int GAME_BOARD_SIZE = 40;

	public static int[][] GAME_BOARD_ARRAY;
	private static ArrayList<GameTile> SNAKE_ARRAY;
	private static GameTile goal;

	// Stores the current direction the snake is moving...
	public static int SNAKE_DIRECTION = 0;

	public static final int SNAKE_UP = 0;
	public static final int SNAKE_DOWN = 1;
	public static final int SNAKE_LEFT = 2;
	public static final int SNAKE_RIGHT = 3;

	private final static int TICK_DELAY = 150;

	private static Random rand = new Random();
	private static boolean scored = false;
	private static int score;

	public static void init() {
		
		GAME_BOARD_ARRAY = new int[GAME_BOARD_SIZE][GAME_BOARD_SIZE];

		// Set up the initial game board:
		for (int i = 0; i < GAME_BOARD_ARRAY.length; i++) {
			for (int j = 0; j < GAME_BOARD_ARRAY.length; j++) {
				// Initialize the board to all -1, open blank spaces
				GAME_BOARD_ARRAY[i][j] = GameTile.BLANK_TILE;
			}
		}

		// Put the snake head somewhere and add a part to the list
				int xPos = (GAME_BOARD_SIZE / 2);
				int yPos = (GAME_BOARD_SIZE / 2);

				System.out.println("Snake Start at (x:" + xPos + ", y:" + yPos + ")");
				SNAKE_ARRAY = new ArrayList<GameTile>();

				// Add the head of the snake to the list of snake parts
				SNAKE_ARRAY.add(new GameTile(xPos, yPos, GameTile.SNAKE_PART));
				// Initialize the snake as 5 parts long, because why not.
				SNAKE_ARRAY.add(new GameTile(xPos - 1, yPos, GameTile.SNAKE_PART));
				SNAKE_ARRAY.add(new GameTile(xPos - 2, yPos, GameTile.SNAKE_PART));
				SNAKE_ARRAY.add(new GameTile(xPos - 3, yPos, GameTile.SNAKE_PART));
				SNAKE_ARRAY.add(new GameTile(xPos - 4, yPos, GameTile.SNAKE_PART));

				// Create the initial goal position:
				createGoalPosition();
	}
	
	private static void createGoalPosition() {
		// Pick an initial 'goal' location, as long as it does not equal the
		// snake head position
		int xPos2 = rand.nextInt(GAME_BOARD_SIZE);
		int yPos2 = rand.nextInt(GAME_BOARD_SIZE);
		for (int i = 0; i < SNAKE_ARRAY.size(); i++) {
			// for each snake tile, make sure its not the goal
			while (xPos2 == SNAKE_ARRAY.get(i).getx()
					&& yPos2 == SNAKE_ARRAY.get(i).gety()) {
				System.out.println("Goal equals a snake part! Try again...");
				xPos2 = rand.nextInt(GAME_BOARD_SIZE);
				yPos2 = rand.nextInt(GAME_BOARD_SIZE);
			}
		}

		System.out.println("Goal Start at (x:" + xPos2 + ", y:" + yPos2 + ")");
		goal = new GameTile(xPos2, yPos2, GameTile.GOAL_TILE);
	}

	public static void printBoard() {
		
		
	}

	public static void startGame() {
		lastTick = System.currentTimeMillis();
		GameRender.init();
		GameEngine.isGameRunning = true;

		// Game 'tick' loop
		while (GameEngine.isGameRunning) {
			currentTick = System.currentTimeMillis();
			GameRender.checkUserInput();
			
			long deltaMillis = (currentTick - lastTick);
			if (deltaMillis > TICK_DELAY) {
				// Start the game tick update:
				GameTile head = SNAKE_ARRAY.get(0);

				GameTile headNew = null;
				switch (SNAKE_DIRECTION) {
				case SNAKE_UP:
					headNew = new GameTile(head.getx() + 1, head.gety(), GameTile.SNAKE_PART);
					break;
				case SNAKE_DOWN:
					headNew = new GameTile(head.getx() - 1, head.gety(), GameTile.SNAKE_PART);
					break;
				case SNAKE_LEFT:
					headNew = new GameTile(head.getx(), head.gety() - 1, GameTile.SNAKE_PART);
					break;
				case SNAKE_RIGHT:
					headNew = new GameTile(head.getx(), head.gety() + 1, GameTile.SNAKE_PART);
					break;
				}
				
				// Check if we've tried to go off the board...
				if (headNew.getx() >= 0 && headNew.getx() < GAME_BOARD_SIZE
						&& headNew.gety() >= 0 && headNew.gety() < GAME_BOARD_SIZE) {
					// Add the new Head tile at the front
					SNAKE_ARRAY.add(0, headNew);
				} else {
					System.out.println("Snake out of bounds!");
					isGameRunning = false;
				}

				// Check if we have just scored:
				if (headNew.getx() == goal.getx() && headNew.gety() == goal.gety()) {
					// This variable will survive for 1 tick, before it is set back to false
					// Could do some action on the next tick to display something...
					scored = true;
					score++;
					
					// This should actually update the game UI at some point
					System.out.println("Score = " + score);
					
					GAME_BOARD_ARRAY[goal.getx()][goal.gety()] = GameTile.SNAKE_PART;
					createGoalPosition();
				} else {
					scored = false;
					GameTile tail = SNAKE_ARRAY.remove(SNAKE_ARRAY.size() - 1);
					GAME_BOARD_ARRAY[tail.getx()][tail.gety()] = GameTile.BLANK_TILE;
				}
				
				
				// Put the updated SNAKE_ARRAY into the GAME_BOARD_ARRAY
				for (int i=0; i < SNAKE_ARRAY.size(); i++) {
					GameTile g = SNAKE_ARRAY.get(i);
					GAME_BOARD_ARRAY[g.getx()][g.gety()] = GameTile.SNAKE_PART;
				}
				
				// Put the (potentially) updated goal location into the GAME_BOARD_ARRAY
				GAME_BOARD_ARRAY[goal.getx()][goal.gety()] = GameTile.GOAL_TILE;
				
				// Update the UI with the new state
				GameRender.updateGameDisplay();
				lastTick = currentTick;
			}
		}

		// We've left the 'running' loop, end the game render and exit
		System.out.println("Stopping...");
		GameRender.stopGame();
	}
}
