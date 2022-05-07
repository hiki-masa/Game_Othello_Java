package main;

/*
 * オセロ
 * */
class Othello implements OthelloStone {
	/* メンバ変数 */
	private OthelloBoard board;
	private Player[] players = new Player[2];
	private Stone playColor = Stone.BLACK;

	/* コンストラクタ */
	public Othello(Player player1, Player player2) {
		// ポリモフィズムを用いたボード・プレイヤー配列の作成
		if (player1.color != player2.color) {
			players[0] = player1;
			players[1] = player2;
		} else {
			System.out.println("player1とplayer2の石の色が同じに設定されています．");
			System.exit(0);
		}
		board = new BoardGUI();
	}

	/* ゲームループ */
	public void game() {
		board.dispBoard();
		while (board.countStone(players[0].color) + board.countStone(players[1].color) != OthelloBoard.BOARD_SIZE * OthelloBoard.BOARD_SIZE) {
			for (Player player : players) {
				// 対象色のプレイヤーがプレイ
				if (player.color == playColor) {
					player.play(board);
					board.dispBoard();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					playColor = OthelloStone.reverseStone(playColor);
					break;
				}
			}
		}
		result();
	}

	/* 結果表示 */
	private void result() {
		System.out.print(players[0].color);
		System.out.println("：" + board.countStone(players[0].color));

		System.out.print(players[1].color);
		System.out.println("：" + board.countStone(players[1].color));

		// 勝敗の表示
		if (board.countStone(players[0].color) > board.countStone(players[1].color)) {
			System.out.println(players[0].color + "の勝利");
		} else if (board.countStone(players[0].color) < board.countStone(players[1].color)) {
			System.out.println(players[1].color + "の勝利");
		} else {
			System.out.println("引き分け");
		}
	}
}