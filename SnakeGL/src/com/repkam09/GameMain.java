/**
 * 
 */
package com.repkam09;

/**
 * @author mark
 *
 */
public class GameMain {
	
	private static int GAME_WINDOW_WIDTH = 800;
	private static int GAME_WINDOW_HEIGHT = 600;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameEngine.init();
		GameEngine.printBoard();
		
		GameEngine.startGame();
	}
}


