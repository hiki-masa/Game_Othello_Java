package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/* プレイヤー抽象クラス */
abstract class Player implements OseroStone{
	Stone color;
	protected ArrayList<int[]> methods = new ArrayList<int[]>();
	
	protected abstract void play();
	/* 石を置ける場所を探す */
	protected void searchCanPut() {
		methods.clear();
		for (int y = 0; y < Osero.BOARD_SIZE; y++) {
			for (int x = 0; x < Osero.BOARD_SIZE; x++) {
				if (Osero.canPut(x, y, color)) {
					int[] placeCanPut = {x, y};
					methods.add(placeCanPut);
				}
			}
		}
	}
}

/* 具象クラス（人間プレイヤー） */
class Human extends Player{
	public Human(Stone _color) {
		color = _color;
	}
	
	@Override
	public void play() {
		searchCanPut();
		if (methods.size() == 0) {
			System.out.println("置ける場所がないので，パスします");
		}else {
			System.out.println("以下の場所に置くことができます：");
			for (int[] method : methods) {
				System.out.println(method[0] + "，"  + method[1]);
			}
			// 設置できる場所が選択されるまでループ待機
			int inputX, inputY;
			Scanner scanner = new Scanner(System.in);
			do{
				System.out.print("設置するx座標を入力してください：");
				inputX = scanner.nextInt();
				System.out.print("設置するy座標を入力してください：");
				inputY = scanner.nextInt();
			}while(!Osero.canPut(inputX, inputY, color));
			scanner.close();
			// 指定された場所に石を設置
			Osero.putStone(inputX, inputY, color);
		}
	}
}

/* 具象クラス（コンピュータプレイヤー） */
class Computer extends Player{
	public Computer(Stone _color) {
		color = _color;
	}
	
	@Override
	public void play() {
		// 設置できる場所を探す
		searchCanPut();
		if (methods.size() == 0) {
			System.out.println("置ける場所がないので，パスします");
		}else {
			Random random = new Random();
			int[] method = methods.get(random.nextInt(methods.size()));
			Osero.putStone(method[0], method[1], color);
		}
	}
}
