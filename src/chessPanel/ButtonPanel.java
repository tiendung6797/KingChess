package chessPanel;

import java.awt.Color;
import java.awt.Component;
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

import chessPieces.Pieces;
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
	
	public ButtonPanel(GameSetting gameSetting, MainChessFrame mainChessFrame, PanelInformation panelInformation, PanelInformation1 panelInformation1) {
		this.gameSetting = gameSetting;
		this.mainChessFrame = mainChessFrame;
		this.panelInformation = panelInformation;
		this.panelInformation1 = panelInformation1;
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));
		this.setBounds(Utils.BOARD_GAME_WIDTH + 20, 443, 300, 120);
		this.setFocusable(true);
		this.setBackground(Color.GRAY);
		addButton();
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
					if(checkHauTrang(mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesAi())) {
						
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
						panelInformation.clear();
						panelInformation1.clear();
						checkWhitePiece(mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesAi());
						checkWhiteBlack(mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman());
					} else {
						if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() != null && mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove().getPieces().getSide() == "Up") {
							mainChessFrame.getBoardPanel().setPiecesAIChoise(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove());
						}else if(mainChessFrame.getBoardPanel().getPositionBoard().getOldPositionBoard().getParentMove() == null){
							mainChessFrame.getBoardPanel().setPiecesAIChoise(null);
						}
						panelInformation.clear();
						panelInformation1.clear();
						checkWhitePiece1(mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesHuman());
						checkWhiteBlack1(mainChessFrame.getBoardPanel().getPositionBoard().getListPiecesAi());
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
	
	public void checkWhitePiece(ArrayList<Pieces> list) {
		for(int i = 0; i < (2 - checkMaTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/MaTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		for(int i = 0; i < (2 - checkTinhTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TinhTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		for(int i = 0; i < (2 - checkXeTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/XeTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		if(checkHauTrang(list) == false) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/HauTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		for(int i = 0; i < (8 - checkTotTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TotTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
	}
	
	public void checkWhitePiece1(ArrayList<Pieces> list) {
		for(int i = 0; i < (2 - checkMaTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/MaTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		for(int i = 0; i < (2 - checkTinhTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TinhTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		for(int i = 0; i < (2 - checkXeTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/XeTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		if(checkHauTrang(list) == false) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/HauTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		for(int i = 0; i < (8 - checkTotTrang(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TotTrang3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
	}
	
	public void checkWhiteBlack(ArrayList<Pieces> list) {
		for(int i = 0; i < (2 - checkMaDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/MaDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		for(int i = 0; i < (2 - checkTinhDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TinhDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		for(int i = 0; i < (2 - checkXeDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/XeDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		if(checkHauDen(list) == false) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/HauDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		for(int i = 0; i < (8 - checkTotDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TotDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
	}
	
	public void checkWhiteBlack1(ArrayList<Pieces> list) {
		for(int i = 0; i < (2 - checkMaDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/MaDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		for(int i = 0; i < (2 - checkTinhDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TinhDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		for(int i = 0; i < (2 - checkXeDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/XeDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		if(checkHauDen(list) == false) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/HauDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
		for(int i = 0; i < (8 - checkTotDen(list)); i++) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/TotDen3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation1.appendImage(img);
		}
	}
	
	public boolean checkHauDen(ArrayList<Pieces> list) {
		for(int i = 0; i < list.size(); i++) {
			if("HauDen".equals(list.get(i).getName() + list.get(i).getColor())) {
				return true;
			}
		}
		return false;
	}
	
	public int checkTinhDen(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("TinhDen".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	}
	
	public int checkXeDen(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("XeDen".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	}
	
	public int checkMaDen(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("MaDen".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	}

	public int checkTotDen(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("TotDen".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	} 
	
	public boolean checkHauTrang(ArrayList<Pieces> list) {
		for(int i = 0; i < list.size(); i++) {
			if("HauTrang".equals(list.get(i).getName() + list.get(i).getColor())) {
				return true;
			}
		}
		return false;
	}
	
	public int checkTinhTrang(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("TinhTrang".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	}
	
	public int checkXeTrang(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("XeTrang".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	}
	
	public int checkMaTrang(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("MaTrang".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	}

	public int checkTotTrang(ArrayList<Pieces> list) {
		int count = 0;
		for(int i = 0; i < list.size(); i++) {
			if("TotTrang".equals(list.get(i).getName() + list.get(i).getColor())) {
				count++;
			}
		}
		return count;
	}
}
