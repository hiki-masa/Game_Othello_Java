package main;

import main.Osero.Stone;

public class Player {
	final Stone color;
	
	public Player(Stone _color) {
		color = _color;
	}
	
	public void play(Osero app) {
		;
	}
}

class Human extends Player{
	public Human(Stone _color) {
		super(_color);
	}
}

class Computer extends Player{
	public Computer(Stone _color) {
		super(_color);
	}
	
	public void play(Osero app) {
		for (int y = 0; y < Osero.BOARD_SIZE; y++) {
			for (int x = 0; x < Osero.BOARD_SIZE; x++) {
				if (app.canPut(x, y, color)) {
					app.putStone(x, y, color);
					return;
				}
			}
		}
	}
}
