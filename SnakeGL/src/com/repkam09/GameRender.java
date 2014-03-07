package com.repkam09;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class GameRender {
	
	public static void init() {
		try {
			// Create the OpenGL Display, set the size and name
			Display.setDisplayMode(new DisplayMode(GameEngine.GAME_BOARD_WIDTH, GameEngine.GAME_BOARD_HEIGHT));
			Display.setTitle("SnakeGL v1.0.0");
			Display.create();

			// init OpenGL
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, GameEngine.GAME_BOARD_WIDTH, 0, GameEngine.GAME_BOARD_HEIGHT, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void checkUserInput() {
		// If we have a next key press, and it is a key down event:
		if (Keyboard.next() && Keyboard.getEventKeyState()) {
			int user_input = Keyboard.getEventKey();
			System.out.println("User Input: " + Keyboard.getKeyName(user_input));
			switch (user_input) {
			case Keyboard.KEY_UP:
				GameEngine.SNAKE_DIRECTION = GameEngine.SNAKE_UP;
				break;
			case Keyboard.KEY_DOWN:
				GameEngine.SNAKE_DIRECTION = GameEngine.SNAKE_DOWN;
				break;
			case Keyboard.KEY_LEFT:
				GameEngine.SNAKE_DIRECTION = GameEngine.SNAKE_LEFT;
				break;
			case Keyboard.KEY_RIGHT:
				GameEngine.SNAKE_DIRECTION = GameEngine.SNAKE_RIGHT;
				break;
			case Keyboard.KEY_ESCAPE:
				GameEngine.isGameRunning = false;
				break;
			default:
				System.out.println("Unhandled user input '" + Keyboard.getKeyName(user_input) + "' [" +user_input + "]");
				break;
			}
		}
	}

	public static void updateGameDisplay() {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		// Draw every tile from the internal game state
		for (int i = 0; i < GameEngine.GAME_BOARD_ARRAY.length; i++) {
			for (int j = 0; j < GameEngine.GAME_BOARD_ARRAY.length; j++) {
				// Grab the current tile to render
				int value = GameEngine.GAME_BOARD_ARRAY[i][j];
				switch (value) {
				case GameTile.SNAKE_PART:
					GL11.glColor3f(1.0f, 0.1f, 0.1f);
					break;
				case GameTile.GOAL_TILE:
					GL11.glColor3f(0.1f, 1.0f, 0.1f);
					break;
				
				case GameTile.BLANK_TILE:
					GL11.glColor3f(0.5f, 0.5f, 0.5f);
					break;
				default:
					System.out.println("Unhandled tile type " + value);
				
				}
				
				// This is not at all correct yet...
				int x_adjust = j*(GameEngine.GAME_BOARD_WIDTH / GameEngine.GAME_BOARD_SIZE);
				int y_adjust = i*(GameEngine.GAME_BOARD_HEIGHT / GameEngine.GAME_BOARD_SIZE);
				
				int sizeX = ((GameEngine.GAME_BOARD_WIDTH *2) / (GameEngine.GAME_BOARD_SIZE));
				int sizeY = ((GameEngine.GAME_BOARD_HEIGHT *2) / (GameEngine.GAME_BOARD_SIZE));
				
				//System.out.println("tile [" + i + "," + j + "] render at {" + x_adjust + "," + y_adjust + "} with size " + sizeX + "x" + sizeY);
				
				// Draw this tile using math!
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x_adjust, y_adjust);
				GL11.glVertex2f(x_adjust+sizeX, y_adjust);
				GL11.glVertex2f(x_adjust+sizeX, y_adjust+sizeY);
				GL11.glVertex2f(x_adjust, y_adjust+sizeY);
				GL11.glEnd();
			}
		}
		// Do the actual update
		Display.update();
		Display.sync(60);

	}

	public static void stopGame() {
		// Just in case we have not stopped the loop yet...
		GameEngine.isGameRunning = false;
		Display.destroy();
	}
}
