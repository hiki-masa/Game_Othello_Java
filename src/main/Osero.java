package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Osero implements MouseListener {
	// 各マスでの状態
	public enum Stone {
		EMPTY,
		BLACK,
		WHITE,
	};
	
	static  final int MassSize  = 100;
	static  final int FieldSize = 8;
	private final ArrayList<ArrayList<Stone>> Field = new ArrayList<ArrayList<Stone>>();
	private final Window Frame;
	
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
		Frame.add(new Canvas(Field));
		Frame.setVisible(true);
		
		// マウスイベントの有効化
		Frame.addMouseListener(this);
		// マウスイベントの無効化
		Frame.removeMouseListener(this);
		
		while(true) {
			for (int y = 0; y < Osero.FieldSize; y++) {
				for (int x = 0; x < Osero.FieldSize; x++) {
					switch (Field.get(y).get(x)) {
					case EMPTY:
						Field.get(y).set(x, Stone.BLACK);
						break;
					case BLACK:
						Field.get(y).set(x, Stone.WHITE);
						break;
					case WHITE:
						Field.get(y).set(x, Stone.EMPTY);
						break;
					
					}
					
					Frame.repaint();
					try { Thread.sleep(100); }
					catch (InterruptedException e) {}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}

