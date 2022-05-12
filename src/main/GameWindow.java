package main;

import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.OthelloStone.Stone;

/*
 * スクリーンの表示モード
 * */
enum ScreenMode {
	GAME, RESULT,
}

/*
 * ウィンドウ
 * */
public class GameWindow extends JFrame {
	/* メンバ変数 */
	private ScreenMode screenMode;
	CardLayout layout = new CardLayout();
	private GamePanel gamePanel;
	private ResultPanel resultPanel;

	/* コンストラクタ */
	public GameWindow(String windowName) {
		// Windowの設定
		setTitle(windowName);
		this.setIconImage(new ImageIcon("src/BLACK.png").getImage());
		setSize(GamePanel.GRID_SIZE * OthelloBoard.BOARD_SIZE, GamePanel.GRID_SIZE * OthelloBoard.BOARD_SIZE);
		setResizable(true);

		// Windowの閉じるボタンを押せば，プログラムが終了する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// パネルの準備
		this.setLayout(layout);
		this.preparePanels();

		// ウィンドウ表示
		this.setVisible(true);
	}

	/* パネルの準備 */
	private void preparePanels() {
		this.gamePanel = new GamePanel();
		this.add(this.gamePanel, "ゲーム画面");
		this.resultPanel = new ResultPanel();
		this.add(this.resultPanel, "結果画面");
	}

	/* 表示するパネルの選択 */
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
	
	public ResultPanel getResultPanel() {
		return this.resultPanel;
	}
}

/*
 * ゲーム画面用パネル
 * */
class GamePanel extends JPanel {
	/* メンバ変数 */
	static final int GRID_SIZE = 100;
	private Image EMPTY_IMG = Toolkit.getDefaultToolkit().getImage("src/EMPTY.png");
	private Image BLACK_IMG = Toolkit.getDefaultToolkit().getImage("src/BLACK.png");
	private Image WHITE_IMG = Toolkit.getDefaultToolkit().getImage("src/WHITE.png");
	protected Othello othello = null;

	/* コンストラクタ */
	public GamePanel() {
		this.setLayout(null);
		this.setBackground(Color.black);
		this.othello = new Othello(new Computer(Stone.BLACK), new Computer(Stone.WHITE));
	}

	/* コンポーネントの描画 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int y = 0; y < OthelloBoard.BOARD_SIZE; y++) {
			for (int x = 0; x < OthelloBoard.BOARD_SIZE; x++) {
				switch (othello.getOthelloBoard().getBoard().get(y).get(x)) {
				case EMPTY:
					g.drawImage(EMPTY_IMG, GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE, this);
					break;
				case BLACK:
					g.drawImage(BLACK_IMG, GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE, this);
					break;
				case WHITE:
					g.drawImage(WHITE_IMG, GRID_SIZE * x, GRID_SIZE * y, GRID_SIZE, GRID_SIZE, this);
					break;
				}
			}
		}
		// 枠線の調整
		Graphics2D g2 = (Graphics2D)g;
		BasicStroke bs = new BasicStroke(5);
		g2.setStroke(bs);
		// 設置箇所の表示
		g.setColor(Color.red);
		g.drawRect(GRID_SIZE * othello.getPlayer(0).pointX, GRID_SIZE * othello.getPlayer(0).pointY, GRID_SIZE, GRID_SIZE);
		g.setColor(Color.blue);
		g.drawRect(GRID_SIZE * othello.getPlayer(1).pointX, GRID_SIZE * othello.getPlayer(1).pointY, GRID_SIZE, GRID_SIZE);
	}
	
	/* ゲーム処理 */
	public void game() {
		while(othello.getOthelloBoard().countStone(Stone.BLACK) + othello.getOthelloBoard().countStone(Stone.WHITE)
				!= OthelloBoard.BOARD_SIZE * OthelloBoard.BOARD_SIZE) {
			othello.game();
			repaint();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
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
	
	public void paint(int blackStoneNumber, int whiteStoneNumber) {
		System.out.println(Stone.BLACK + "：" + blackStoneNumber);
		System.out.println(Stone.WHITE + "：" + whiteStoneNumber);
		
		if (blackStoneNumber > whiteStoneNumber) {
			System.out.println(Stone.BLACK + "の勝ち");
		}else if(blackStoneNumber < whiteStoneNumber) {
			System.out.println(Stone.WHITE + "の勝ち");
		}else {
			System.out.println("引き分け");
		}
	}
}