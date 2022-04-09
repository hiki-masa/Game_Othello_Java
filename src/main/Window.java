package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Osero.Stone;

public class Window extends JFrame{
	/* コンストラクタ */
	public Window(String _WindowName, int _Width, int _Height) {
		// Windowの設定
		setTitle(_WindowName);
		setSize(_Width, _Height);
		setResizable(true);
		
		// Windowの閉じるボタンを押せば，プログラムが終了する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 背景色の設定
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.GREEN);
	}
}

class Canvas extends JPanel {
	private Image EmptyImg = Toolkit.getDefaultToolkit().getImage("src/EMPTY.png");
	private Image BlackImg = Toolkit.getDefaultToolkit().getImage("src/BLACK.png");
	private Image WhiteImg = Toolkit.getDefaultToolkit().getImage("src/WHITE.png");

	private final ArrayList<ArrayList<Stone>> Field;
	
	// コンストラクタ
	public Canvas(ArrayList<ArrayList<Stone>> _Field) {
		Field = _Field;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int y = 0; y < Osero.FieldSize; y++) {
			for (int x = 0; x < Osero.FieldSize; x++) {
				switch (Field.get(y).get(x)) {
				case EMPTY:
					g.drawImage(EmptyImg, Osero.MassSize * x, Osero.MassSize * y, Osero.MassSize, Osero.MassSize, this);
					break;
				case BLACK:
					g.drawImage(BlackImg, Osero.MassSize * x, Osero.MassSize * y, Osero.MassSize, Osero.MassSize, this);
					break;
				case WHITE:
					g.drawImage(WhiteImg, Osero.MassSize * x, Osero.MassSize * y, Osero.MassSize, Osero.MassSize, this);
					break;
				}
			}
		}
	}
}