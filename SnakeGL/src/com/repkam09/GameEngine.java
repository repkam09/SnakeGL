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
		int xPos = rand.nextInt(GAME_BOARD_SIZE);
		int yPos = rand.nextInt(GAME_BOARD_SIZE);

		System.out.println("Snake Start at (x:" + xPos + ", y:" + yPos + ")");
		SNAKE_ARRAY = new ArrayList<GameTile>();

		// Add the head of the snake to the list of snake parts
		SNAKE_ARRAY.add(new GameTile(xPos, yPos, GameTile.SNAKE_PART));

		// Pick an initial 'goal' location, as long as it does not equal the
		// snake head position
		int xPos2 = rand.nextInt(GAME_BOARD_SIZE);
		int yPos2 = rand.nextInt(GAME_BOARD_SIZE);
		while (xPos2 == xPos && yPos2 == yPos) {
			System.out.println("Snake/Goal Equal! Wow!");
			xPos2 = rand.nextInt(GAME_BOARD_SIZE);
			yPos2 = rand.nextInt(GAME_BOARD_SIZE);
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
				//System.out.println("tick");
				// Update the internal game state, move snake by direction
				for (int i=0; i < SNAKE_ARRAY.size(); i++) {
					GameTile g = SNAKE_ARRAY.get(i);
					GAME_BOARD_ARRAY[g.getx()][g.gety()] = GameTile.SNAKE_PART;
				}
				// Update the game board with the current goal location
				GAME_BOARD_ARRAY[goal.getx()][goal.gety()] = GameTile.GOAL_TILE;
				
				// Go draw the current game board state
				GameRender.updateGameDisplay();
				lastTick = currentTick;
			}
		}

		// We've left the 'running' loop, end the game render and exit
		System.out.println("Stopping...");
		GameRender.stopGame();
	}
}
