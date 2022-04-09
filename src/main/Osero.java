package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Osero implements MouseListener{
	// 各マスでの状態
	public enum Stone {
		EMPTY,
		BLACK,
		WHITE,
	};
	
	public Stone reverseColor(final Stone _Color) {
		if (_Color == Stone.BLACK)
			return Stone.WHITE;
		else if(_Color == Stone.WHITE)
			return Stone.BLACK;
		else
			return Stone.EMPTY;
	}
	
	
	static  final int MassSize  = 100;
	static  final int FieldSize = 8;
	private final ArrayList<ArrayList<Stone>> Field = new ArrayList<ArrayList<Stone>>();
	private final Window Frame;
	Stone PutStoneColor = Stone.BLACK;
	
	/* コンストラクタ */
	public Osero() {
		// ウィンドウクラスの作成
		Frame = new Window("Osero", MassSize * FieldSize, MassSize * FieldSize);
		// リストの作成
		for (int y = 0; y < FieldSize; y++) {
			Field.add(new ArrayList<>());
			for (int x = 0; x < FieldSize; x++) {
				Field.get(y).add(Stone.EMPTY);
			}
		}
		// 初期石の設定
		Field.get(3).set(3, Stone.BLACK);
		Field.get(4).set(4, Stone.BLACK);
		Field.get(3).set(4, Stone.WHITE);
		Field.get(4).set(3, Stone.WHITE);
		
		Frame.add(new Canvas(Field));
		Frame.setVisible(true);
		
		// マウスイベントの有効化
		Frame.addMouseListener(this);
	}
	
	// 指定箇所に石を設置
	public void putStone(int _x, int _y, Stone _color) {
		// 範囲内を選択しているか判定
		if (0 <= _x && _x < FieldSize && 0 <= _y && _y < FieldSize) {
			if (Field.get(_y).get(_x) == Stone.EMPTY) {
				// 全方向探索
				for (int vy = -1; vy <= 1; vy++) {
					for (int vx = -1; vx <= 1; vx++) {
						// 確認方向がない場合，処理を飛ばす
						if (vx == 0 && vy == 0) {
							continue;
						}
						
						// 確認方向に相手の石がなければ，処理を飛ばす
						if (0 <= _x + vx && _x + vx < FieldSize
								&& 0 <= _y + vy && _y + vy < FieldSize) {
							if (Field.get(_y + vy).get(_x + vx) != reverseColor(_color)) {
								continue;
							}
						}
						
						for (int step = 2; step < FieldSize; step++) {
							// 範囲内に制限
							if (0 <= _x + vx * step && _x + vx * step < FieldSize
									&& 0 <= _y + vy * step && _y + vy * step < FieldSize) {
								// EMPTYマスが先に見つかった場合，置き換えることは出来ないので，処理を飛ばす
								if (Field.get(_y + vy * step).get(_x + vx * step) == Stone.EMPTY) {
									break;
								}
								// 自分Colorの石が存在すれば，あいだの石を置き換える
								if (Field.get(_y + vy * step).get(_x + vx * step) == _color) {
									Field.get(_y).set(_x, _color);
									for (int s = 0; s < step; s++) {
										Field.get(_y + vy * s).set(_x + vx * s, _color);
										Frame.repaint();
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public int countStone(Stone _color) {
		int count = 0;
		for (int y = 0; y < FieldSize; y++) {
			for (int x = 0; x < FieldSize; x++) {
				if (Field.get(y).get(x) == _color) {
					count++;
				}
			}
		}
		return count;
	}
	
	// マウスクリックに関する関数
	@Override
	public void mouseClicked(MouseEvent e) {
		int beforStone = countStone(PutStoneColor);
		putStone((e.getX() - 10) / MassSize, (e.getY() - 30) / MassSize, PutStoneColor);
		// 数の変化があれば、石が置けたと判断し、置く石の色を交換
		if (beforStone != countStone(PutStoneColor)) {
			PutStoneColor = reverseColor(PutStoneColor);
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