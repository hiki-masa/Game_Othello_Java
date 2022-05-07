package main;

import main.OthelloStone.Stone;

public class Main {
	public static void main(String[] args) {
		new Othello(new Computer(Stone.BLACK), new Computer(Stone.WHITE)).game();
	}
}