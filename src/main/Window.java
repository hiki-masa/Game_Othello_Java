package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * スクリーンの表示モード
 * */
enum ScreenMode {
	GAME, RESULT,
}

/*
 * ウィンドウ
 * */
public class Window extends JFrame {
	/* メンバ変数 */
	private ScreenMode screenMode;
	CardLayout layout = new CardLayout();
	private GamePanel gamePanel;
	private ResultPanel resultPanel;

	/* コンストラクタ */
	public Window(String windowName, int windowWidth, int windowHeight) {
		// Windowの設定
		setTitle(windowName);
		this.setIconImage(new ImageIcon("src/BLACK.png").getImage());
		setSize(windowWidth, windowHeight);
		setResizable(true);

		// Windowの閉じるボタンを押せば，プログラムが終了する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(layout);
		this.preparePanels();

		this.setVisible(true);
	}

	/* パネルの準備 */
	private void preparePanels() {
		this.gamePanel = new GamePanel();
		this.add(this.gamePanel, "ゲーム画面");
		this.resultPanel = new ResultPanel();
		this.add(this.resultPanel, "結果画面");
	}

	public void setFrontScreenAndFocus(ScreenMode s) {
		screenMode = s;
		switch (screenMode) {
		case GAME:
			layout.show(this.getContentPane(), "ゲーム画面");
			this.gamePanel.requestFocus();
			break;
		case RESULT:
			layout.show(this.getContentPane(), "結果画面");
			this.resultPanel.requestFocus();
			break;
		}
	}

	/* ゲッター */
	public GamePanel getGamePanel() {
		return this.gamePanel;
	}
}

/*
 * ゲーム画面用パネル
 * */
class GamePanel extends JPanel {
	/* メンバ変数 */
	private Image EMPTY_IMG = Toolkit.getDefaultToolkit().getImage("src/EMPTY.png");
	private Image BLACK_IMG = Toolkit.getDefaultToolkit().getImage("src/BLACK.png");
	private Image WHITE_IMG = Toolkit.getDefaultToolkit().getImage("src/WHITE.png");
	private OthelloBoard board = null;

	/* コンストラクタ */
	public GamePanel() {
		this.setLayout(null);
		this.setBackground(Color.black);
	}

	/* セッター */
	public void setBoard(OthelloBoard board) {
		this.board = board;
	}

	/* コンポーネントの描画 */
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

/*
 * 結果画面用パネル
 * */
class ResultPanel extends JPanel {
	/* コンストラクタ */
	public ResultPanel() {
		this.setLayout(null);
		this.setBackground(Color.green);
	}
}