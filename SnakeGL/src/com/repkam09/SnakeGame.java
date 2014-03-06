/**
 * 
 */
package com.repkam09;

import java.util.ArrayList;

/**
 * @author mark
 *
 */
public class SnakeGame {

	private static int[][] gameboard;
	private static int nbrPlayed;
	private static int bestScore;
	private static int currentScore;
	
	private static int GAMEBOARD_WIDTH = 26;
	private static int GAMEBOARD_HEIGHT = 26;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gameboard = new int[GAMEBOARD_WIDTH][GAMEBOARD_HEIGHT];
		for (int x = 0; x < gameboard.length; x++) {
			for (int y = 0; y < gameboard.length; y++) {
				// Initialize the board to all zero
				gameboard[x][y] = 0;
			}
		}
	}
}