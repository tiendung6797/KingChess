package chessPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import chessPieces.Pieces;
import core.ArtificialIntelligence;
import core.GameSetting;
import core.Move;
import core.PositionBoard;
import core.Utils;
import gameframe.MainChessFrame;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {
	private MainChessFrame mainChessFrame;
	private GameSetting gameSetting;
	public PositionBoard positionBoard;
	private boolean showCanMove;
	private Pieces piecesHumanChoise;
	private Move piecesAIChoise;
	private boolean isHumanTurn;
	private boolean wasChoisePieces = false;
	private ArtificialIntelligence ai;
	private boolean win;
	private PanelInformation panelInformation;
	private PanelInformation1 panelInformation1;
	private String pieceAIDie;
	private String pieceHumanDie;
	
	public String getPieceAIDie() {
		return pieceAIDie;
	}

	public void setPieceAIDie(String pieceAIDie) {
		this.pieceAIDie = pieceAIDie;
	}
	
	public String getPieceHumanDie() {
		return pieceHumanDie;
	}

	public void setPieceHumanDie(String pieceHumanDie) {
		this.pieceHumanDie = pieceHumanDie;
	}

	public Move getPiecesAIChoise() {
		return piecesAIChoise;
	}

	public void setPiecesAIChoise(Move piecesAIChoise) {
		this.piecesAIChoise = piecesAIChoise;
	}

	public BoardPanel(GameSetting gameSetting, MainChessFrame mainChessFrame, PanelInformation panelInformation, PanelInformation1 panelInformation1) {
		this.gameSetting = gameSetting;
		this.mainChessFrame = mainChessFrame;
		this.panelInformation = panelInformation;
		this.panelInformation1 = panelInformation1;
		this.positionBoard = new PositionBoard(gameSetting.getLevel(), gameSetting, this);
		this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(10, 70, Utils.BOARD_GAME_WIDTH, Utils.BOARD_GAME_HEIGHT);
		this.setFocusable(true);
		showCanMove = false;
		addMouseListener(this);
		addMouseMotionListener(this);
		if (gameSetting.isAiFirst()) {
			isHumanTurn = false;
		} else {
			isHumanTurn = true;
		}
		win = false;
		
		Timer timerMoveAi = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveAi();
			}
		});
		timerMoveAi.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		ImageIcon imageBackGround = new ImageIcon(this.getClass().getResource("/image/board.png"));
		g2d.drawImage(imageBackGround.getImage(), 0, 0, 642, 645, null);
		
		ImageIcon gray = new ImageIcon(this.getClass().getResource("/image/gray.jpg"));
		ImageIcon dark = new ImageIcon(this.getClass().getResource("/image/dark.png"));
		ImageIcon showMove = new ImageIcon(this.getClass().getResource("/image/showmove.png"));
		ImageIcon moveAI = new ImageIcon(this.getClass().getResource("/image/moveAI.png"));
		ImageIcon moveHuman = new ImageIcon(this.getClass().getResource("/image/moveHuman.png"));
		
		for(int i = 0; i < 64;  i++) {
			int setX = i % 8;
			int setY = i / 8;
			g2d.drawRect(20 + (setX * 75), 23 + ((7 - setY) * 75), 75, 75);
			
			if((setX + setY) % 2 == 0) {
				g2d.drawImage(dark.getImage(), 20 + (setX * 75), 23 + (7 - setY) * 75, 75, 75, null);
			}else {
				g2d.drawImage(gray.getImage(), 20 + (setX * 75), 23 + (7 - setY) * 75, 75, 75, null);
			}
		}
		
		if (showCanMove) {
			for (int j = 0; j < piecesHumanChoise.getNumberCanMove().size(); j++) {
				int locationY = (int) (piecesHumanChoise.getNumberCanMove().get(j) / 8);
				int locationX = piecesHumanChoise.getNumberCanMove().get(j) % 8;
				if((locationX + locationY) % 2 == 0) {
					g2d.drawImage(showMove.getImage(), 20 + (locationX) * 75, 23 + (7 - locationY) * 75, 75, 75, null);
				} else {
					g2d.drawImage(showMove.getImage(), 20 + (locationX) * 75, 23 + (7 - locationY) * 75, 75, 75, null);
				}
			}
			if(piecesHumanChoise.getNumberCanMove().size() > 0) {
				int locationY = (int) (piecesHumanChoise.getNumberInBoard() / 8);
				int locationX = piecesHumanChoise.getNumberInBoard() % 8;
				g2d.drawImage(moveHuman.getImage(), 20 + (locationX) * 75, 23 + (7 - locationY) * 75, 75, 75, null);
			}
		}
		
		if(piecesAIChoise != null && isHumanTurn) {
			int choseX = piecesAIChoise.getPieces().getNumberInBoard() % 8;
			int choseY = piecesAIChoise.getPieces().getNumberInBoard() / 8; 
			
			int moveX = piecesAIChoise.getNumberNext() % 8;
			int moveY = piecesAIChoise.getNumberNext() / 8;
			
			g2d.drawImage(moveAI.getImage(), 20 + (choseX) * 75, 23 + (7 - choseY) * 75, 75, 75, null);
			g2d.drawImage(moveAI.getImage(), 20 + (moveX) * 75, 23 + (7 - moveY) * 75, 75, 75, null);
		}
		
		this.positionBoard.draw(g);
	}
	
	public void moveAi() {
		if (gameSetting.isAiPlay() && !win && !isHumanTurn) {
			ai = new ArtificialIntelligence(gameSetting, positionBoard);
			positionBoard = ai.getNextPosition();
			piecesAIChoise = positionBoard.getParentMove(); 
			
			if(positionBoard.isAiWasEat()) {
				try {
					URL url = this.getClass().getClassLoader().getResource("Capture.WAV");
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			} else {
				try {
					URL url = this.getClass().getClassLoader().getResource("Move.WAV");
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
			
			isHumanTurn = true;
			showCanMove = true;

			repaint();
			showCanMove = false;

			checkWin();
			repaint();
		}
		if(pieceHumanDie != null) {
			Image img = new ImageIcon(this.getClass().getResource("/imgchessman/" + pieceHumanDie + "3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			panelInformation.appendImage(img);
		}
		pieceHumanDie = null;
	}
	
	
	
//	for (int i = 0; i < positionBoard.getListPiecesAi().size(); i++) {
//		positionBoard.getListPiecesAi().get(i).setNumberCanMove();
//	}
//	
//	if(piecesChoise.getName() == "Tuong") {
//		for (int i = 0; i < positionBoard.getListPiecesAi().size(); i++) {
//			for (int j = 0; j < positionBoard.getListPiecesAi().get(i).getNumberCanMove().size(); j++) {
//				for(int k = 0; k < piecesChoise.getNumberCanMove().size(); k++ ) {
//					if (piecesChoise.getNumberCanMove().get(k) == positionBoard.getListPiecesAi().get(i).getNumberCanMove().get(j)) {
//						piecesChoise.getNumberCanMove().remove(k);
//					}
//				}
//			}
//		}
//	}
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		win = false;
		if (isHumanTurn) {
			int location = (7 - (int) ((e.getY() - 23) / 75)) * 8 + (int) ((e.getX() - 20) / 75);
			if (positionBoard.wasSetHuman(location)) {
				showCanMove = true;
				wasChoisePieces = true;
				piecesHumanChoise = positionBoard.getChoisePiecesHuman(location);
				piecesHumanChoise.setNumberCanMove();
				repaint();
			} else {
				if (wasChoisePieces && piecesHumanChoise.canMove(location) && gameSetting.isAiPlay()) {
					Move move = new Move(piecesHumanChoise, location);
					positionBoard = positionBoard.newPositionBoard(move);

					if(positionBoard.isHumanWasEat()) {
						try {
							URL url = this.getClass().getClassLoader().getResource("Capture.WAV");
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException ex) {
							ex.printStackTrace();
						} catch (IOException ex) {
							ex.printStackTrace();
						} catch (LineUnavailableException ex) {
							ex.printStackTrace();
						}
					} else {
						try {
							URL url = this.getClass().getClassLoader().getResource("Move.WAV");
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
							Clip clip = AudioSystem.getClip();
							clip.open(audioIn);
							clip.start();
						} catch (UnsupportedAudioFileException ex) {
							ex.printStackTrace();
						} catch (IOException ex) {
							ex.printStackTrace();
						} catch (LineUnavailableException ex) {
							ex.printStackTrace();
						}
					}
					
					isHumanTurn = false;
					showCanMove = false;

					checkWin();
					wasChoisePieces = false;
					repaint();
				} else if (wasChoisePieces && piecesHumanChoise.canMove(location) && !gameSetting.isAiPlay()) {
					Move move = new Move(piecesHumanChoise, location);

					positionBoard = positionBoard.newPositionBoard(move);

					isHumanTurn = false;
					showCanMove = false;

					checkWin();
					wasChoisePieces = false;
					repaint();
				}
				if(pieceAIDie != null) {
					Image img = new ImageIcon(this.getClass().getResource("/imgchessman/" + pieceAIDie + "3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
					panelInformation1.appendImage(img);
				}
				pieceAIDie = null;
			}
		} else {
			if (!gameSetting.isAiPlay()) {
				int location = (7 - (int) ((e.getY() - 23) / 75)) * 8 + (int) ((e.getX() - 20) / 75);
				if (positionBoard.wasSetAi(location)) {
					showCanMove = true;
					wasChoisePieces = true;
					piecesHumanChoise = positionBoard.getChoisePiecesAi(location);
					piecesHumanChoise.setNumberCanMove();
					
					repaint();
				} else {
					if (wasChoisePieces && piecesHumanChoise.canMove(location)) {
						Move move = new Move(piecesHumanChoise, location);
						positionBoard = positionBoard.newPositionBoard(move);
						isHumanTurn = true;
						repaint();
						showCanMove = false;
						checkWin();
						wasChoisePieces = false;
						repaint();
					}
					if(pieceHumanDie != null) {
						Image img = new ImageIcon(this.getClass().getResource("/imgchessman/" + pieceHumanDie + "3.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
						panelInformation.appendImage(img);
					}
					pieceHumanDie = null;
				}
			}
		}
	}
	
	public void checkWin() {
		if (isHumanTurn) {
			if (this.positionBoard.checkAiWin()) {
				win = true;
				final JOptionPane optionPane = new JOptionPane(Utils.RESULT_LOSE,
						JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
				final JDialog dialog = new JDialog(new JFrame(), Utils.TITLE_DIALOG_FAIL, true);
				dialog.setContentPane(optionPane);
				dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				optionPane.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						String prop = e.getPropertyName();
						if (dialog.isVisible() && (e.getSource() == optionPane)
								&& (JOptionPane.VALUE_PROPERTY.equals(prop))) {
							dialog.setVisible(false);
						}
					}
				});
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);

				int value = ((Integer) optionPane.getValue()).intValue();
				if (value == JOptionPane.YES_OPTION) {
					this.positionBoard = new PositionBoard(gameSetting.getLevel(), gameSetting, this);
					repaint();
				} else {
					Utils.inGame = false;
					mainChessFrame.getMenuFrame().setVisible(true);
					mainChessFrame.dispose();
				}
			}
		} else {
			if (this.positionBoard.checkHumanWin()) {
				win = true;
				final JOptionPane optionPane = new JOptionPane(Utils.RESULT_WIN,
						JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
				final JDialog dialog = new JDialog(new JFrame(), Utils.TITLE_DIALOG_SUCCESS, true);
				dialog.setContentPane(optionPane);
				dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				optionPane.addPropertyChangeListener(new PropertyChangeListener() {

					public void propertyChange(PropertyChangeEvent e) {
						String prop = e.getPropertyName();
						if (dialog.isVisible() && (e.getSource() == optionPane)
								&& (JOptionPane.VALUE_PROPERTY.equals(prop))) {
							dialog.setVisible(false);
						}
					}
				});
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				int value = ((Integer) optionPane.getValue()).intValue();
				if (value == JOptionPane.YES_OPTION) {
					this.positionBoard = new PositionBoard(gameSetting.getLevel(), gameSetting, this);
					repaint();
				} else {
					Utils.inGame = false;
					mainChessFrame.getMenuFrame().setVisible(true);
					mainChessFrame.dispose();
				}
			}
		}
	}
	
	public boolean isHumanTurn() {
		return isHumanTurn;
	}

	public void setHumanTurn(boolean isHumanTurn) {
		this.isHumanTurn = isHumanTurn;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public boolean isShowCanMove() {
		return showCanMove;
	}

	public void setShowCanMove(boolean showCanMove) {
		this.showCanMove = showCanMove;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public GameSetting getGameSetting() {
		return gameSetting;
	}

	public void setGameSetting(GameSetting gameSetting) {
		this.gameSetting = gameSetting;
	}

	public PositionBoard getPositionBoard() {
		return positionBoard;
	}

	public void setPositionBoard(PositionBoard positionBoard) {
		this.positionBoard = positionBoard;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}

}