package gameframe;

import javax.swing.JFrame;

import chessPanel.BoardPanel;
import chessPanel.ButtonPanel;
import chessPanel.LevelPanel;
import chessPanel.PanelInformation;
import chessPanel.PanelInformation1;
import core.GameSetting;
import core.Utils;

public class MainChessFrame extends JFrame {
	private PanelInformation panelInformation;
	private PanelInformation1 panelInformation1;
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
		panelInformation = new PanelInformation();
		panelInformation1 = new  PanelInformation1();
		boardPanel = new BoardPanel(gameSetting, panelInformation, panelInformation1);
		levelPanel = new LevelPanel(gameSetting);
		buttonPanel = new ButtonPanel(gameSetting, this, panelInformation, panelInformation1);
		
		add(panelInformation);
		add(panelInformation1);
		add(boardPanel);
		add(levelPanel);
		add(buttonPanel);
	}
	
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}
	public PanelInformation getPanelInformation() {
		return panelInformation;
	}
	public MenuFrame getMenuFrame() {
		return menuFrame;
	}
}
