package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * ウィンドウ
 * */
public class Window extends JFrame {
	/* コンストラクタ */
	public Window(String _windowName, int _width, int _height) {
		// Windowの設定
		setTitle(_windowName);
		this.setIconImage(new ImageIcon("src/BLACK.png").getImage());
		setSize(_width, _height);
		setResizable(true);

		// Windowの閉じるボタンを押せば，プログラムが終了する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}
}

/*
 * パネル
 * */
class GamePanel extends JPanel {
	/* メンバ変数 */
	private Image EMPTY_IMG = Toolkit.getDefaultToolkit().getImage("src/EMPTY.png");
	private Image BLACK_IMG = Toolkit.getDefaultToolkit().getImage("src/BLACK.png");
	private Image WHITE_IMG = Toolkit.getDefaultToolkit().getImage("src/WHITE.png");
	private OthelloBoard board;

	/* コンストラクタ */
	public GamePanel() {
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
						g.drawImage(EMPTY_IMG, OthelloBoardGUI.GRID_SIZE * x, OthelloBoardGUI.GRID_SIZE * y,
								OthelloBoardGUI.GRID_SIZE, OthelloBoardGUI.GRID_SIZE, this);
						break;
					case BLACK:
						g.drawImage(BLACK_IMG, OthelloBoardGUI.GRID_SIZE * x, OthelloBoardGUI.GRID_SIZE * y,
								OthelloBoardGUI.GRID_SIZE, OthelloBoardGUI.GRID_SIZE, this);
						break;
					case WHITE:
						g.drawImage(WHITE_IMG, OthelloBoardGUI.GRID_SIZE * x, OthelloBoardGUI.GRID_SIZE * y,
								OthelloBoardGUI.GRID_SIZE, OthelloBoardGUI.GRID_SIZE, this);
						break;
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.println("表示するオセロボード（board）が設定されていません．");
		}
	}
}