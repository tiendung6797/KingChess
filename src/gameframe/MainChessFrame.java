package gameframe;

import javax.swing.JFrame;

import chessPanel.BoardPanel;
import chessPanel.ButtonPanel;
import chessPanel.LevelPanel;
import core.GameSetting;
import core.Utils;

public class MainChessFrame extends JFrame {
	private BoardPanel boardPanel;
	private GameSetting gameSetting;
	private LevelPanel levelPanel;
	private ButtonPanel buttonPanel;
	private MenuFrame menuFrame;

	public MainChessFrame(GameSetting gameSetting, MenuFrame menuFrame, Boolean visible) {
		this.setSize(Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		this.setTitle(Utils.GAME_NAME);
		this.setIconImage(Utils.GAME_AVATAR);
		this.menuFrame = menuFrame;
		this.gameSetting = gameSetting;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		addCompoment();
		this.setLocationRelativeTo(null);
		this.setVisible(visible);
	}

	public void addCompoment() {
		boardPanel = new BoardPanel(gameSetting);
		levelPanel = new LevelPanel(gameSetting);
		buttonPanel = new ButtonPanel(gameSetting, this);
		
		add(boardPanel);
		add(levelPanel);
		add(buttonPanel);
	}
	
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
	public MenuFrame getMenuFrame() {
		return menuFrame;
	}
}
