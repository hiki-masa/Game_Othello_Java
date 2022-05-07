package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * ウィンド
 * */
public class Window extends JFrame {
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

/*
 * キャンバス
 * */
class Canvas extends JPanel {
	/* メンバ変数 */
	private Image EMPTY_IMG = Toolkit.getDefaultToolkit().getImage("src/EMPTY.png");
	private Image BLACK_IMG = Toolkit.getDefaultToolkit().getImage("src/BLACK.png");
	private Image WHITE_IMG = Toolkit.getDefaultToolkit().getImage("src/WHITE.png");
	private OthelloBoard board;

	/* コンストラクタ */
	public Canvas() {
		board = null;
	}

	/* セッター */
	public void setBoard(OthelloBoard _board) {
		board = _board;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		try {
			for (int y = 0; y < OthelloBoard.BOARD_SIZE; y++) {
				for (int x = 0; x < OthelloBoard.BOARD_SIZE; x++) {
					switch (board.getBoard().get(y).get(x)) {
					case EMPTY:
						g.drawImage(EMPTY_IMG, BoardGUI.GRID_SIZE * x, BoardGUI.GRID_SIZE * y,
								BoardGUI.GRID_SIZE, BoardGUI.GRID_SIZE, this);
						break;
					case BLACK:
						g.drawImage(BLACK_IMG, BoardGUI.GRID_SIZE * x, BoardGUI.GRID_SIZE * y,
								BoardGUI.GRID_SIZE, BoardGUI.GRID_SIZE, this);
						break;
					case WHITE:
						g.drawImage(WHITE_IMG, BoardGUI.GRID_SIZE * x, BoardGUI.GRID_SIZE * y,
								BoardGUI.GRID_SIZE, BoardGUI.GRID_SIZE, this);
						break;
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.println("表示するオセロボード（board）が設定されていません．");
		}
	}
}