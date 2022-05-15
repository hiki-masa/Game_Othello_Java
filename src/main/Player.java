package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/* 
 * プレイヤー抽象クラス 
 * */
abstract class Player implements OthelloStone {
	/* メンバ変数 */
	Stone stoneColor;
	protected ArrayList<Pointer> canPutLocations = new ArrayList<>();
	protected Pointer putLocation = new Pointer();
	protected Color pointerColor;

	/* コンストラクタ */
	protected Player(Stone stoneColor, Color pointerColor) {
		this.stoneColor = stoneColor;
		this.pointerColor = pointerColor;
	}

	/* プレイヤーごとに石の設置方法を変更するための抽象メソッド */
	protected abstract boolean play(OthelloBoard othelloBoard);

	/* 石を置ける場所を探す */
	protected void searchCanPut(OthelloBoard othelloBoard) {
		canPutLocations.clear();
		for (int y = 0; y < OthelloBoard.BOARD_SIZE; y++) {
			for (int x = 0; x < OthelloBoard.BOARD_SIZE; x++) {
				if (othelloBoard.canPut(x, y, stoneColor)) {
					canPutLocations.add(new Pointer(x, y));
				}
			}
		}
	}

	protected class Pointer {
		public int x, y;

		public Pointer() {
			x = 0;
			y = 0;
		}

		public Pointer(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

/* 
 * コンピュータプレイヤー
 *  */
class Computer extends Player {
	/* メンバ変数 */
	private Random random;

	/* コンストラクタ */
	public Computer(Stone stoneColor) {
		super(stoneColor, Color.blue);
		random = new Random();
	}

	/* 石の設置 */
	@Override
	public boolean play(OthelloBoard othelloBoard) {
		// 設置できる場所を探す
		searchCanPut(othelloBoard);
		if (canPutLocations.size() == 0) {
			System.out.println("(" + stoneColor + ")：置ける場所がないので，パスします");
		} else {
			putLocation = canPutLocations.get(random.nextInt(canPutLocations.size()));
			othelloBoard.putStone(putLocation.x, putLocation.y, stoneColor);
		}
		return true;
	}
}

/* 
 * 人間プレイヤー 
 * */
class Human extends Player {
	/* メンバ変数 */
	Scanner scanner;

	/* コンストラクタ */
	public Human(Stone stoneColor) {
		super(stoneColor, Color.red);
		scanner = new Scanner(System.in);
	}

	/* 石の設置 */
	@Override
	public boolean play(OthelloBoard othelloBoard) {
		searchCanPut(othelloBoard);
		if (canPutLocations.size() == 0) {
			System.out.println("(" + stoneColor + ")：置ける場所がないので，パスします");
		} else {
			System.out.println("以下の場所に置くことができます：");
			for (Pointer location : canPutLocations) {
				System.out.println(location.x + "，" + location.y);
			}
			// 設置できる場所が選択されるまでループ待機
			do {
				System.out.print("設置するx座標を入力してください：");
				putLocation.x = scanner.nextInt();
				System.out.print("設置するy座標を入力してください：");
				putLocation.y = scanner.nextInt();
			} while (!othelloBoard.canPut(putLocation.x, putLocation.y, stoneColor));
			// 指定された場所に石を設置
			othelloBoard.putStone(putLocation.x, putLocation.y, stoneColor);
		}
		return true;
	}
}

/* 
 * 人間プレイヤー 
 * */
class HumanGUI extends Human {
	/* コンストラクタ */
	public HumanGUI(Stone stoneColor) {
		super(stoneColor);
	}

	/* 石の設置 */
	@Override
	public boolean play(OthelloBoard othelloBoard) {
		searchCanPut(othelloBoard);
		if (canPutLocations.size() == 0) {
			System.out.println("(" + stoneColor + ")：置ける場所がないので，パスします");
			return true;
		} else {
			int counter = othelloBoard.countStone(stoneColor);
			// 指定された場所に石を設置
			othelloBoard.putStone(putLocation.x, putLocation.y, stoneColor);
			if (counter == othelloBoard.countStone(stoneColor)) {
				return false;
			} else {
				return true;
			}
		}
	}
}