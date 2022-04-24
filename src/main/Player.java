package main;

/* プレイヤー抽象クラス */
abstract class Player implements OseroStone{
	Stone color;
	
	public abstract void play();
}

/* 具象クラス（人間プレイヤー） */
class Human extends Player{
	public Human(Stone _color) {
		color = _color;
	}

	@Override
	public void play() {
		;
	}
}

/* 具象クラス（コンピュータプレイヤー） */
class Computer extends Player{
	public Computer(Stone _color) {
		color = _color;
	}
	
	@Override
	public void play() {
		for (int y = 0; y < Osero.getBOARD_SIZE(); y++) {
			for (int x = 0; x < Osero.getBOARD_SIZE(); x++) {
				if (Osero.canPut(x, y, color)) {
					Osero.putStone(x, y, color);
					return;
				}
			}
		}
	}
}
