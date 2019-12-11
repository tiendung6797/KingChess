package gameframe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
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
		getContentPane().setBackground(Color.DARK_GRAY);
		this.setSize(Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		this.setTitle(Utils.GAME_NAME);
		this.setIconImage(Utils.GAME_AVATAR);
		this.menuFrame = menuFrame;
		this.gameSetting = gameSetting;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);
		addCompoment();
		this.setLocationRelativeTo(null);
		this.setVisible(visible);
	}

	public void addCompoment() {
		panelInformation = new PanelInformation();
		panelInformation1 = new  PanelInformation1();
		boardPanel = new BoardPanel(gameSetting, this, panelInformation, panelInformation1);
		levelPanel = new LevelPanel(gameSetting);
		buttonPanel = new ButtonPanel(gameSetting, this, panelInformation, panelInformation1);
		
		getContentPane().add(panelInformation);
		getContentPane().add(panelInformation1);
		getContentPane().add(boardPanel);
		getContentPane().add(levelPanel);
		getContentPane().add(buttonPanel);
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

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		ImageIcon imageBackGround = new ImageIcon(this.getClass().getResource("/image/AI.jpg"));
		g2d.drawImage(imageBackGround.getImage(), Utils.BOARD_GAME_WIDTH + 28, 41, 300, 200, null);
		
		ImageIcon imageBackGround1 = new ImageIcon(this.getClass().getResource("/image/human.jpg"));
		g2d.drawImage(imageBackGround1.getImage(), Utils.BOARD_GAME_WIDTH + 28, 605, 300, 200, null);
	}
}
