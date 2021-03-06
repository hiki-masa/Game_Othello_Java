package main;

import main.OthelloStone.Stone;

public class Main {
	public static void main(String[] args) {
		// ウィンドウ・パネルの準備
		GameWindow othelloApp = new GameWindow("オセロ");
		othelloApp.preparePanels();
		othelloApp.prepareComponents();

		// ウィンドウ表示
		othelloApp.setVisible(true);

		// ゲームの実行
		othelloApp.setFrontScreenAndFocus(ScreenMode.GAME);
		othelloApp.getGamePanel().game();

		// 結果画面の描画
		othelloApp.setFrontScreenAndFocus(ScreenMode.RESULT);
		othelloApp.getResultPanel().dispResult(
				othelloApp.getGamePanel().othello.getOthelloBoard().countStone(Stone.BLACK),
				othelloApp.getGamePanel().othello.getOthelloBoard().countStone(Stone.WHITE));
	}
}