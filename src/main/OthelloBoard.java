package main;

import java.util.ArrayList;

/*
 * オセロ石のインタフェース
 * */
interface OthelloStone {
	/* 各マスでの状態 */
	public static enum Stone {
		EMPTY, BLACK, WHITE,
	};

	/* 引数で渡された反対の色を返す */
	public static Stone reverseStone(final Stone _color) {
		if (_color == Stone.BLACK)
			return Stone.WHITE;
		else if (_color == Stone.WHITE)
			return Stone.BLACK;
		else
			return Stone.EMPTY;
	}
}

/*
 * オセロボード
 * */
public class OthelloBoard implements OthelloStone, Cloneable {
	/* メンバ変数 */
	protected static final int BOARD_SIZE = 8;
	private final ArrayList<ArrayList<Stone>> board = new ArrayList<ArrayList<Stone>>();

	/* コンストラクタ */
	public OthelloBoard() {
		// リストの作成
		for (int y = 0; y < BOARD_SIZE; y++) {
			board.add(new ArrayList<>());
			for (int x = 0; x < BOARD_SIZE; x++) {
				board.get(y).add(Stone.EMPTY);
			}
		}
		// 初期石の設定
		board.get(3).set(3, Stone.BLACK);
		board.get(4).set(4, Stone.BLACK);
		board.get(3).set(4, Stone.WHITE);
		board.get(4).set(3, Stone.WHITE);
	}

	/* 指定箇所に指定色の石が置けるか判断 */
	public boolean canPut(int _x, int _y, Stone _color) {
		// 指定箇所に石を設置
		OthelloBoard copy = this.clone();
		copy.putStone(_x, _y, _color);

		// 設置前と設置後で，数の変化があるなら置けると判定，変化が無いなら置けないと判定
		if (copy.countStone(_color) != this.countStone(_color)) {
			return true;
		} else {
			return false;
		}
	}

	/* 指定箇所に石を設置 */
	public void putStone(int _x, int _y, Stone _color) {
		// 範囲内を選択しているか判定
		if (0 <= _x && _x < BOARD_SIZE && 0 <= _y && _y < BOARD_SIZE) {
			if (board.get(_y).get(_x) == Stone.EMPTY) {
				// 全方向探索
				for (int vy = -1; vy <= 1; vy++) {
					for (int vx = -1; vx <= 1; vx++) {
						// 確認方向がない場合，処理を飛ばす
						if (vx == 0 && vy == 0) {
							continue;
						}

						// 確認方向に相手の石がなければ，処理を飛ばす
						if (0 <= _x + vx && _x + vx < BOARD_SIZE && 0 <= _y + vy && _y + vy < BOARD_SIZE) {
							if (board.get(_y + vy).get(_x + vx) != OthelloStone.reverseStone(_color)) {
								continue;
							}
						}

						for (int step = 2; step < BOARD_SIZE; step++) {
							// 範囲内に制限
							if (0 <= _x + vx * step && _x + vx * step < BOARD_SIZE && 0 <= _y + vy * step
									&& _y + vy * step < BOARD_SIZE) {
								// EMPTYマスが先に見つかった場合，置き換えることは出来ないので，処理を飛ばす
								if (board.get(_y + vy * step).get(_x + vx * step) == Stone.EMPTY) {
									break;
								}
								// 自分Colorの石が存在すれば，あいだの石を置き換える
								if (board.get(_y + vy * step).get(_x + vx * step) == _color) {
									board.get(_y).set(_x, _color);
									for (int s = 0; s < step; s++) {
										board.get(_y + vy * s).set(_x + vx * s, _color);
									}
									break;
								}
							}
						}
					}
				}
			}
		}
		// 範囲外を選択している際の処理
		else {
			System.out.println("範囲外のマス座標を指定しないでください．");
		}
	}

	/* 盤面上にある指定した色の石の数を返す */
	public int countStone(Stone _color) {
		int count = 0;
		for (ArrayList<Stone> row : board) {
			for (Stone stone : row) {
				if (stone == _color) {
					count++;
				}
			}
		}
		return count;
	}

	/* 結果画面の表示 */
	public void dispResult() {
		System.out.print(Stone.BLACK);
		System.out.println("：" + countStone(Stone.BLACK));

		System.out.print(Stone.WHITE);
		System.out.println("：" + countStone(Stone.WHITE));

		// 勝敗の表示
		if (countStone(Stone.BLACK) > countStone(Stone.WHITE)) {
			System.out.println(Stone.BLACK + "の勝利");
		} else if (countStone(Stone.BLACK) < countStone(Stone.WHITE)) {
			System.out.println(Stone.WHITE + "の勝利");
		} else {
			System.out.println("引き分け");
		}
	}

	/* CUI表示 */
	public void dispBoard() {
		System.out.print("  ");
		for (int i = 0; i < OthelloBoard.BOARD_SIZE; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
		for (int y = 0, x; y < OthelloBoard.BOARD_SIZE; y++) {
			System.out.print(" " + y);
			for (x = 0; x < OthelloBoard.BOARD_SIZE; x++) {
				switch (board.get(y).get(x)) {
				case BLACK:
					System.out.print("〇");
					break;
				case WHITE:
					System.out.print("●");
					break;
				case EMPTY:
					System.out.print("　");
					break;
				default:
					;
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	/* 現在の盤面を複製した Boardクラス を返す */
	@Override
	public OthelloBoard clone() {
		OthelloBoard copy = new OthelloBoard();
		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				copy.board.get(y).set(x, this.board.get(y).get(x));
			}
		}
		return copy;
	}

	/* ゲッター */
	public ArrayList<ArrayList<Stone>> getBoard() {
		return this.clone().board;
	}
}

/*
 * GUI表示オセロボード
 * */
class OthelloBoardGUI extends OthelloBoard {
	/* メンバ変数 */
	static final int GRID_SIZE = 100;
	private final Window FRAME;

	/* コンストラクタ */
	public OthelloBoardGUI() {
		super();
		// ウィンドウクラスの作成
		FRAME = new Window("Osero", GRID_SIZE * OthelloBoard.BOARD_SIZE, GRID_SIZE * OthelloBoard.BOARD_SIZE);
	}

	/* Canvasを用いたGUI表示 */
	@Override
	public void dispBoard() {
		FRAME.setFrontScreenAndFocus(ScreenMode.GAME);
		FRAME.getGamePanel().setBoard(this.clone());
		FRAME.repaint();
	}

	/* 結果画面の表示 */
	@Override
	public void dispResult() {
		super.dispBoard();
		super.dispResult();
		FRAME.setFrontScreenAndFocus(ScreenMode.RESULT);
	}
}