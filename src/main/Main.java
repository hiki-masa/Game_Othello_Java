package main;

import main.OthelloStone.Stone;

public class Main {
	public static void main(String[] args) {
		GameWindow othelloApp = new GameWindow("オセロ");
		// ゲームの実行
		othelloApp.setFrontScreenAndFocus(ScreenMode.GAME);
		othelloApp.getGamePanel().game();
		
		// 結果画面の描画
		othelloApp.setFrontScreenAndFocus(ScreenMode.RESULT);
		othelloApp.getResultPanel().paint(
				othelloApp.getGamePanel().othello.getOthelloBoard().countStone(Stone.BLACK),
				othelloApp.getGamePanel().othello.getOthelloBoard().countStone(Stone.WHITE));
	}
}