package main;

import main.OseroStone.Stone;

public class Main {
	public static void main(String[] args) {
		new Osero(new Human(Stone.BLACK), new Computer(Stone.WHITE)).game();
	}
}