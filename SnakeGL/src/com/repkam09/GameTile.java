package com.repkam09;

public class GameTile {
	
	public static final int SNAKE_PART = 1;
	public static final int BLANK_TILE = -1;
	public static final int GOAL_TILE = 0;
	
	private int value;
	private int x;
	private int y;
	
	public GameTile(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
		
	}
	
	public int getValue() {
		return this.value;
	}
	
	public int getx() {
		return this.x;
	}
	
	public int gety() {
		return this.y;
	}
	
	public String toString() {
		return "( x=" + x + " y=" + y + ")";
	}
}
