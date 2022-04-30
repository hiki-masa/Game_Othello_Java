package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import main.OseroStone.Stone;

/* プレイヤー抽象クラス */
abstract class Player {
	Stone color;
	protected ArrayList<int[]> methods = new ArrayList<int[]>();

	protected abstract void play(Board _board);

	/* 石を置ける場所を探す */
	protected void searchCanPut(Board _board) {
		methods.clear();
		for (int y = 0; y < Board.BOARD_SIZE; y++) {
			for (int x = 0; x < Board.BOARD_SIZE; x++) {
				if (_board.canPut(x, y, color)) {
					int[] placeCanPut = { x, y };
					methods.add(placeCanPut);
				}
			}
		}
	}
}

/* 具象クラス（コンピュータプレイヤー） */
class Computer extends Player {
	public Computer(Stone _color) {
		color = _color;
	}

	@Override
	public void play(Board _board) {
		// 設置できる場所を探す
		searchCanPut(_board);
		if (methods.size() == 0) {
			System.out.println("(" + color + ")：置ける場所がないので，パスします");
		} else {
			Random random = new Random();
			int[] method = methods.get(random.nextInt(methods.size()));
			_board.putStone(method[0], method[1], color);
		}
	}
}

/* 具象クラス（人間プレイヤー） */
class Human extends Player {
	Scanner scanner;
	
	public Human(Stone _color) {
		color = _color;
		scanner = new Scanner(System.in);
	}

	@Override
	public void play(Board _board) {
		searchCanPut(_board);
		if (methods.size() == 0) {
			System.out.println("(" + color + ")：置ける場所がないので，パスします");
		} else {
			System.out.println("以下の場所に置くことができます：");
			for (int[] method : methods) {
				System.out.println(method[0] + "，" + method[1]);
			}
			// 設置できる場所が選択されるまでループ待機
			int inputX, inputY;
			do {
				System.out.print("設置するx座標を入力してください：");
				inputX = scanner.nextInt();
				System.out.print("設置するy座標を入力してください：");
				inputY = scanner.nextInt();
			} while (!_board.canPut(inputX, inputY, color));
			//scanner.close();
			// 指定された場所に石を設置
			_board.putStone(inputX, inputY, color);
		}
	}
}

//class HumanGUI extends Human implements MouseListener{
//	private Board board;
//	
//	public HumanGUI(Stone _color, Board _board) {
//		super(_color);
//		board = _board;
//	}
//	
//	@Override
//	public void play(Board _board) {
//		int countStone = _board.countStone(color);
//		while (countStone == _board.countStone(color)) {
//			;
//		}
//	}
//	
//	/* マウスクリックに関する関数 */
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		// マウスでクリックした場所に石が置ける場合
//		if (board.canPut((e.getX() - 10) / BoardGUI.GRID_SIZE, (e.getY() - 30) / BoardGUI.GRID_SIZE, color)) {
//			board.putStone((e.getX() - 10) / BoardGUI.GRID_SIZE, (e.getY() - 30) / BoardGUI.GRID_SIZE, color);
//		}
//	}
//	@Override
//	public void mousePressed(MouseEvent e) {}
//	@Override
//	public void mouseReleased(MouseEvent e) {}
//	@Override
//	public void mouseEntered(MouseEvent e) {}
//	@Override
//	public void mouseExited(MouseEvent e) {}
//}