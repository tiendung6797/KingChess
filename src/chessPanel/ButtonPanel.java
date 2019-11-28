package chessPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import core.ButtonSave;
import core.GameSetting;
import core.Move;
import core.Utils;
import gameframe.MainChessFrame;

public class ButtonPanel extends JPanel implements MouseListener {
	private GameSetting gameSetting;
	private JLabel btnUndo, btnExit, btnSave;
	private MainChessFrame mainChessFrame;
	private PanelInformation panelInformation;
	private PanelInformation1 panelInformation1;
	
	private ArrayList<String> listWhite = new ArrayList<String>();
	private ArrayList<String> listBlack = new ArrayList<String>();
	
	public void addDefaul() {
		this.listWhite.add("TuongTrang");
		this.listWhite.add("HauTrang");
		this.listWhite.add("TinhTrang");
		this.listWhite.add("TinhTrang");
		this.listWhite.add("XeTrang");
		this.listWhite.add("XeTrang");
		this.listWhite.add("MaTrang");
		this.listWhite.add("MaTrang");
		this.listWhite.add("TotTrang");
		this.listWhite.add("TotTrang");
		this.listWhite.add("TotTrang");
		this.listWhite.add("TotTrang");
		this.listWhite.add("TotTrang");
		this.listWhite.add("TotTrang");
		this.listWhite.add("TotTrang");
		this.listWhite.add("TotTrang");
	
		this.listBlack.add("TuongDen");
		this.listBlack.add("HauDen");
		this.listBlack.add("TinhDen");
		this.listBlack.add("TinhDen");
		this.listBlack.add("XeDen");
		this.listBlack.add("XeDen");
		this.listBlack.add("MaDen");
		this.listBlack.add("MaDen");
		this.listBlack.add("TotDen");
		this.listBlack.add("TotDen");
		this.listBlack.add("TotDen");
		this.listBlack.add("TotDen");
		this.listBlack.add("TotDen");
		this.listBlack.add("TotDen");
		this.listBlack.add("TotDen");
		this.listBlack.add("TotDen");
	}

	public ButtonPanel(GameSetting gameSetting, MainChessFrame mainChessFrame, PanelInformation panelInformation, PanelInformation1 panelInformation1) {
		this.gameSetting = gameSetting;
		this.mainChessFrame = mainChessFrame;
		this.panelInformation = panelInformation;
		this.panelInformation1 = panelInformation1;
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));
		this.setBounds(Utils.BOARD_GAME_WIDTH, 520, Utils.GAME_WIDTH-Utils.BOARD_GAME_WIDTH-15, 115);
		this.setFocusable(true);
		this.setBackground(Color.GRAY);
		addButton();
		addDefaul();
	}

	public JLabel createButtonControl(String iconName, Box parent) {
		JLabel btn = new JLabel(Utils.resizeImageIcon(iconName, Utils.BUTTON_CONTROL_WIDTH, Utils.BUTTON_CONTROL_HEIGHT));
		btn.setBorder(null);
		btn.addMouseListener(this);
		btn.setFocusable(true);
		parent.add(btn);
		return btn;
	}
		
	public void addButton() {
		Box boxPanel1 = Box.createHorizontalBox();
		boxPanel1.setAlignmentX(CENTER_ALIGNMENT);
		Box boxPanel2 = Box.createHorizontalBox();
		boxPanel2.setAlignmentX(CENTER_ALIGNMENT);
		
		btnUndo = createButtonControl("undo3", boxPanel1);
		btnSave = new ButtonSave(mainChessFrame.getBoardPanel());
		btnSave.setIcon(Utils.ICON_SAVE1);
		btnSave.addMouseListener(this);
		
		boxPanel2.add(btnSave);
		btnExit = createButtonControl("exit3", boxPanel2);
		
		Box boxPanel3 = Box.createVerticalBox();
		boxPanel3.add(boxPanel1);
		boxPanel3.add(boxPanel2);
		this.add(boxPanel3);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(Utils.IMG_PANEL, 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JLabel lbl = (JLabel) e.getSource();
		if(lbl == btnUndo) {
			// First Button: Undo
			if (!gameSetting.isAiPlay()) {	
				Move move = mainChessFrame.getBoardPanel().getPositionBoard().getParentMove();
				move.toString();
				mainChessFrame.getBoardPanel().setPositionBoard(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard());
				if(gameSetting.isAiFirst()) {
					if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() != null && mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove().getPieces().getSide() == "Up") {
						mainChessFrame.getBoardPanel().setPiecesAIChoise(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove());
					}
				} else {
					if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() != null && mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove().getPieces().getSide() == "Up") {
						mainChessFrame.getBoardPanel().setPiecesAIChoise(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove());
					}else if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() == null){
						mainChessFrame.getBoardPanel().setPiecesAIChoise(null);
					}
				}
				mainChessFrame.getBoardPanel().setShowCanMove(false);
				mainChessFrame.getBoardPanel().repaint();
				if (mainChessFrame.getBoardPanel().isHumanTurn()) {
					mainChessFrame.getBoardPanel().setHumanTurn(false);
				} else {
					mainChessFrame.getBoardPanel().setHumanTurn(true);
				}
			} else {
				for (int i = 0; i <= 1; i++) {
					Move move = mainChessFrame.getBoardPanel().getPositionBoard().getParentMove();
					move.toString();
					mainChessFrame.getBoardPanel().setPositionBoard(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard());
					
					if(gameSetting.isAiFirst()) {
						if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() != null && mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove().getPieces().getSide() == "Up") {
							mainChessFrame.getBoardPanel().setPiecesAIChoise(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove());
						}
						
					} else {
						if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() != null && mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove().getPieces().getSide() == "Up") {
							mainChessFrame.getBoardPanel().setPiecesAIChoise(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove());
						}else if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() == null){
							mainChessFrame.getBoardPanel().setPiecesAIChoise(null);
						}
					}
					
					mainChessFrame.getBoardPanel().setShowCanMove(false);
					mainChessFrame.getBoardPanel().repaint();
					if (mainChessFrame.getBoardPanel().isHumanTurn()) {
						mainChessFrame.getBoardPanel().setHumanTurn(false);
					} else {
						mainChessFrame.getBoardPanel().setHumanTurn(true);
					}
				}
			}
			
			
//			panelInformation.clear();
//			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/" + "TotDen" + "3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
//			panelInformation.appendImage(img);
			
			
			for(int j = 0; j < mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman().size(); j++) {
				System.out.println(mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman().get(j).getName() 
							+ mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman().get(j).getColor());
			}
//			for(int i = 0; i < listBlack.size(); i++) {
//				for(int j = 0; j < mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman().size(); j++) {
//					if(listBlack.get(i) == (mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman().get(j).getName() 
//							+ mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman().get(j).getColor())){
//						break;
//					}
//					if(j == mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman().size()) {
//						System.out.println(listBlack.get(i));
//					}
//				}
//			}
			
		} else if(lbl == btnExit) {
			Utils.inGame = false;
			mainChessFrame.getMenuFrame().setVisible(true);
			mainChessFrame.dispose();
		} else if(lbl == btnSave) {
			
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		if(label == btnUndo){
			btnUndo.setIcon(Utils.ICON_UNDO2);
		} else if(label == btnExit) {
			btnExit.setIcon(Utils.ICON_EXIT2);
		} else {
			btnSave.setIcon(Utils.ICON_SAVE2);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		if(label == btnUndo){
			btnUndo.setIcon(Utils.ICON_UNDO1);
		} else if(label == btnExit) {
			btnExit.setIcon(Utils.ICON_EXIT1);
		} else {
			btnSave.setIcon(Utils.ICON_SAVE1);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
