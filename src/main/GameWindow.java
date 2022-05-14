package main;

import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

		// ウィンドウ表示
		this.setVisible(true);
	}

	/* パネルの準備 */
	public void preparePanels() {
		this.gamePanel = new GamePanel();
		this.add(this.gamePanel, "ゲーム画面");
		this.resultPanel = new ResultPanel();
		this.add(this.resultPanel, "結果画面");
	}

	/* コンポーネントの準備 */
	public void prepareComponents() {
		this.gamePanel.prepareComponent();
		this.resultPanel.prepareComponent();
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
	protected GameKeyListener gameKeyListener;

	/* コンストラクタ */
	public GamePanel() {
		this.setLayout(null);
		this.setBackground(Color.black);
	}

	/* コンポーネントの準備 */
	public void prepareComponent() {
		this.othello = new Othello(new HumanGUI(Stone.BLACK), new Computer(Stone.WHITE));
		gameKeyListener = new GameKeyListener(this);
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
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke bs = new BasicStroke(5);
		g2.setStroke(bs);
		// 設置箇所の表示
		for(Player player : othello.getPlayers()) {
			g.setColor(player.pointerColor);
			g.drawRect(GRID_SIZE * player.putLocation.x, GRID_SIZE * player.putLocation.y, GRID_SIZE, GRID_SIZE);
		}
	}

	/* ゲーム処理 */
	public void game() {
		while (othello.getOthelloBoard().countStone(Stone.BLACK) + othello.getOthelloBoard().countStone(Stone.WHITE) != OthelloBoard.BOARD_SIZE * OthelloBoard.BOARD_SIZE) {
			for (Player player : othello.getPlayers()) {
				if (player.stoneColor == othello.playColor) {
					if (player instanceof Computer) {
						othello.game();
						repaint();
					} else {
						;
					}
					break;
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	/* キーリスナー */
	private class GameKeyListener implements KeyListener {
		/* コンストラクタ */
		public GameKeyListener(GamePanel p) {
			super();
			p.addKeyListener(this);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			for (Player player : othello.getPlayers()) {
				if(player.stoneColor == othello.playColor) {
					switch (e.getKeyCode()) {
					case (KeyEvent.VK_UP):
						if (player.putLocation.y > 0)
							player.putLocation.y--;
						break;
					case (KeyEvent.VK_DOWN):
						if (player.putLocation.y < OthelloBoard.BOARD_SIZE - 1)
							player.putLocation.y++;
						break;
					case (KeyEvent.VK_LEFT):
						if (player.putLocation.x > 0)
							player.putLocation.x--;
						break;
					case (KeyEvent.VK_RIGHT):
						if (player.putLocation.x < OthelloBoard.BOARD_SIZE - 1)
							player.putLocation.x++;
						break;
					case (KeyEvent.VK_ENTER):
						othello.game();
						break;
					}
					repaint();
					break;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
}

/*
 * 結果画面用パネル
 * */
class ResultPanel extends JPanel {
	/* メンバ変数 */
	ResultKeyListener resultKeyListener;
	
	/* コンストラクタ */
	public ResultPanel() {
		this.setLayout(null);
		this.setBackground(Color.green);
	}
	
	/* コンポーネントの準備 */
	public void prepareComponent() {
		resultKeyListener = new ResultKeyListener(this);
	}

	public void paint(int blackStoneNumber, int whiteStoneNumber) {
		System.out.println(Stone.BLACK + "：" + blackStoneNumber);
		System.out.println(Stone.WHITE + "：" + whiteStoneNumber);

		if (blackStoneNumber > whiteStoneNumber) {
			System.out.println(Stone.BLACK + "の勝ち");
		} else if (blackStoneNumber < whiteStoneNumber) {
			System.out.println(Stone.WHITE + "の勝ち");
		} else {
			System.out.println("引き分け");
		}
	}

	@Override
	public void paintComponent(Graphics g) {

	}
	
	/* キーリスナー */
	private class ResultKeyListener implements KeyListener {
		/* コンストラクタ */
		public ResultKeyListener(ResultPanel p) {
			super();
			p.addKeyListener(this);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case (KeyEvent.VK_ESCAPE):
				System.exit(0);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
}