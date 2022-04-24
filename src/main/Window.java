package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.OseroStone.Stone;

public class Window extends JFrame{
	/* コンストラクタ */
	public Window(String _windowName, int _width, int _height) {
		// Windowの設定
		setTitle(_windowName);
		setSize(_width, _height);
		setResizable(true);
		
		// Windowの閉じるボタンを押せば，プログラムが終了する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 背景色の設定
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.GREEN);
	}
}

class Canvas extends JPanel {
	private Image EMPTY_IMG = Toolkit.getDefaultToolkit().getImage("src/EMPTY.png");
	private Image BLACK_IMG = Toolkit.getDefaultToolkit().getImage("src/BLACK.png");
	private Image WHITE_IMG = Toolkit.getDefaultToolkit().getImage("src/WHITE.png");
	private ArrayList<ArrayList<Stone>> board;
	
	// コンストラクタ
	public Canvas() {
		;
	}
	
	// セッター
	public void setBoard(ArrayList<ArrayList<Stone>> _board) {
		board = _board;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int y = 0; y < Osero.BOARD_SIZE; y++) {
			for (int x = 0; x < OseroGUI.BOARD_SIZE; x++) {
				switch (board.get(y).get(x)) {
				case EMPTY:
					g.drawImage(EMPTY_IMG, OseroGUI.GRID_SIZE * x, OseroGUI.GRID_SIZE * y, OseroGUI.GRID_SIZE, OseroGUI.GRID_SIZE, this);
					break;
				case BLACK:
					g.drawImage(BLACK_IMG, OseroGUI.GRID_SIZE * x, OseroGUI.GRID_SIZE * y, OseroGUI.GRID_SIZE, OseroGUI.GRID_SIZE, this);
					break;
				case WHITE:
					g.drawImage(WHITE_IMG, OseroGUI.GRID_SIZE * x, OseroGUI.GRID_SIZE * y, OseroGUI.GRID_SIZE, OseroGUI.GRID_SIZE, this);
					break;
				}
			}
		}
	}
}