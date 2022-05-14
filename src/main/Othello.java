package main;

/*
 * オセロ
 * */
class Othello implements OthelloStone {
	/* メンバ変数 */
	private OthelloBoard othelloBoard;
	private Player[] players = new Player[2];
	protected Stone playColor = Stone.BLACK;

	/* コンストラクタ */
	public Othello(Player player1, Player player2) {
		// ポリモフィズムを用いたボード・プレイヤー配列の作成
		if (player1.stoneColor != player2.stoneColor) {
			players[0] = player1;
			players[1] = player2;
		} else {
			System.out.println("player1とplayer2の石の色が同じに設定されています．");
			System.exit(0);
		}
		othelloBoard = new OthelloBoard();
	}

	/* ゲームループ */
	public void game() {
		for (Player player : players) {
			// 対象色のプレイヤーがプレイ
			if (player.stoneColor == playColor) {
				// 正しくプレイすることが出来たか判定
				if(player.play(othelloBoard))
					playColor = OthelloStone.reverseStone(playColor);
				break;
			}
		}
	}

	/* ゲッター */
	public OthelloBoard getOthelloBoard() {
		return othelloBoard;
	}
	
	public Player[] getPlayers() {
		return  players;
	}
}