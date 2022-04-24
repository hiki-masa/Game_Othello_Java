package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/* オセロ石のインタフェース */
interface OseroStone{
	// 各マスでの状態
	public static enum Stone {
		EMPTY,
		BLACK,
		WHITE,
	};
	
	public static Stone reverseStone(final Stone _color) {
		if (_color == Stone.BLACK)
			return Stone.WHITE;
		else if(_color == Stone.WHITE)
			return Stone.BLACK;
		else
			return Stone.EMPTY;
	}
}

/* オセロクラス（インタフェースにするかどうか検討） */
class Osero implements OseroStone{
	protected static final int BOARD_SIZE = 8;
	private   static final ArrayList<ArrayList<Stone>> board = new ArrayList<ArrayList<Stone>>();
	// ポリモフィズムを用いたプレイヤー配列の作成
	protected Player[] players = { new Computer(Stone.BLACK), new Computer(Stone.WHITE) };
	
	/* コンストラクタ */
	public Osero() {
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
	
	/* ゲームループ */
	public void game() {
		while(countStone(players[0].color) + countStone(players[1].color) != BOARD_SIZE * BOARD_SIZE) {
			players[0].play();
			dispBoard();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			players[1].play();
			dispBoard();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
		result();
	}
	
	/* 結果表示 */
	private void result() {
		System.out.print(players[0].color);
		System.out.println("：" + countStone(players[0].color));
		
		System.out.print(players[1].color);
		System.out.println("：" + countStone(players[1].color));
	}
	
	/* 指定箇所に指定色の石が置けるか判断 */
	public static  boolean canPut(int _x, int _y, Stone _color) {
		// 石を設置前の盤面，対象の色の石の数を記録
		ArrayList<ArrayList<Stone>> copyBoard = copy();
		int stoneBeforePut = countStone(_color);
		// 指定箇所に石を設置
		putStone(_x, _y, _color);
		
		// 設置前と設置後で，数の変化があるなら置けると判定，変化が無いなら置けないと判定
		if (countStone(_color) != stoneBeforePut) {
			// 盤面の変更を修正
			updateBoard(copyBoard);
			return true;
		}else {
			return false;
		}
	}
	
	/* 指定箇所に石を設置 */
	public static void putStone(int _x, int _y, Stone _color) {
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
						if (0 <= _x + vx && _x + vx < BOARD_SIZE
								&& 0 <= _y + vy && _y + vy < BOARD_SIZE) {
							if (board.get(_y + vy).get(_x + vx) != OseroStone.reverseStone(_color)) {
								continue;
							}
						}
						
						for (int step = 2; step < BOARD_SIZE; step++) {
							// 範囲内に制限
							if (0 <= _x + vx * step && _x + vx * step < BOARD_SIZE
									&& 0 <= _y + vy * step && _y + vy * step < BOARD_SIZE) {
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
	public static int countStone(Stone _color) {
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
	
	/* Canvasを用いたCUI表示 */
	public void dispBoard() {
		for (ArrayList<Stone> rows: board) {
			for (Stone s : rows) {
				switch(s) {
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
	}
	
	/* コピー盤面を返す */
	public static ArrayList<ArrayList<Stone>> copy(){
		ArrayList<ArrayList<Stone>> copyBoard = new ArrayList<ArrayList<Stone>>();
		for (int y = 0; y < BOARD_SIZE; y++) {
			copyBoard.add(new ArrayList<>());
			for (int x = 0; x < BOARD_SIZE; x++) {
				copyBoard.get(y).add(board.get(y).get(x));
			}
		}
		return copyBoard;
	}
	
	/* 与えられた盤面に更新 */
	public static void updateBoard(ArrayList<ArrayList<Stone>> _board) {
		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				board.get(y).set(x, _board.get(y).get(x));
			}
		}
	}
}

public class OseroGUI extends Osero implements MouseListener{
	static  final int GRID_SIZE = 100;
	private final Canvas canv = new Canvas();
	private final Window FRAME;
	
	/* コンストラクタ */
	public OseroGUI() {
		// ウィンドウクラスの作成
		FRAME = new Window("Osero", GRID_SIZE * BOARD_SIZE, GRID_SIZE * BOARD_SIZE);
		
		FRAME.add(canv);
		dispBoard();
		FRAME.setVisible(true);
		
		// マウスイベントの有効化
		FRAME.addMouseListener(this);
	}
	
	/* Canvasを用いたGUI表示 */
	@Override
	public void dispBoard() {
		canv.setBoard(copy());
		FRAME.repaint();
	}
	
	/* マウスクリックに関する関数 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// マウスでクリックした場所に石が置ける場合
		if (super.canPut((e.getX() - 10) / GRID_SIZE, (e.getY() - 30) / GRID_SIZE, players[0].color)) {
			// 人間のプレイ
			super.putStone((e.getX() - 10) / GRID_SIZE, (e.getY() - 30) / GRID_SIZE, players[0].color);
			// CPUのプレイ
			players[1].play();
			dispBoard();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}