package chessPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
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

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {
	private GameSetting gameSetting;
	private PositionBoard positionBoard;
	private boolean showCanMove;
	private Pieces piecesChoise;
	private boolean isHumanTurn;
	private boolean wasChoisePieces = false;
	private ArtificialIntelligence ai;
	private boolean win;

	public BoardPanel(GameSetting gameSetting) {
		this.gameSetting = gameSetting;
		this.positionBoard = new PositionBoard(gameSetting.getLevel(), gameSetting, this);
		this.setBorder(new LineBorder(Color.BLACK));
		this.setBackground(gameSetting.getBackgroundColor());
		this.setBounds(0, 0, Utils.BOARD_GAME_WIDTH, Utils.BOARD_GAME_HEIGHT);
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
		
		Timer timerSetting = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHumanTurn || !(gameSetting.isAiPlay() || gameSetting.isWatchMode())) {
					setBackground(gameSetting.getBackgroundColor());
					repaint();
				}
			}
		});
		timerSetting.start();
		
		Timer timerMoveHumanInWatchMode = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveHumanInWatchMode();
			}
		});
		timerMoveHumanInWatchMode.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		ImageIcon imageBackGround = new ImageIcon(this.getClass().getResource("/image/board.png"));
		g2d.drawImage(imageBackGround.getImage(), 0, 0, 642, 645, null);
		
		ImageIcon gray = new ImageIcon(this.getClass().getResource("/image/gray.jpg"));
		ImageIcon dark = new ImageIcon(this.getClass().getResource("/image/dark.png"));
		ImageIcon green = new ImageIcon(this.getClass().getResource("/image/green.jpg"));
		ImageIcon green1 = new ImageIcon(this.getClass().getResource("/image/green1.jpg"));
		ImageIcon brown = new ImageIcon(this.getClass().getResource("/image/brown.jpg"));
		
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
			for (int j = 0; j < piecesChoise.getNumberCanMove().size(); j++) {
				int locationY = (int) (piecesChoise.getNumberCanMove().get(j) / 8);
				int locationX = piecesChoise.getNumberCanMove().get(j) % 8;
				if((locationX + locationY) % 2 == 0) {
					g2d.drawImage(green.getImage(), 20 + (locationX) * 75, 23 + (7 - locationY) * 75, 75, 75, null);
				} else {
					g2d.drawImage(green1.getImage(), 20 + (locationX) * 75, 23 + (7 - locationY) * 75, 75, 75, null);
				}
			}
			if(piecesChoise.getNumberCanMove().size() > 0) {
				int locationY = (int) (piecesChoise.getNumberInBoard() / 8);
				int locationX = piecesChoise.getNumberInBoard() % 8;
				g2d.drawImage(brown.getImage(), 20 + (locationX) * 75, 23 + (7 - locationY) * 75, 75, 75, null);
			}
		}
		
		this.positionBoard.draw(g);
	}
	
	Graphics g;
	
	public void moveAi() {
		if ((gameSetting.isAiPlay() || gameSetting.isWatchMode()) && !win && !isHumanTurn) {
			if(!gameSetting.isWatchMode()) {
				ai = new ArtificialIntelligence(gameSetting, positionBoard);
				positionBoard = ai.getNextPosition();
				
				ImageIcon brown = new ImageIcon(this.getClass().getResource("/image/brown.jpg"));
				int moveX = ai.getNextPosition().getParentMove().getNumberNext() % 8;
				int moveY = ai.getNextPosition().getParentMove().getNumberNext() / 8;
				Graphics2D g2d = (Graphics2D) g;
				g2d.drawImage(brown.getImage(), 20 + (moveX) * 75, 23 + (7 - moveY) * 75, 75, 75, null);
				
				isHumanTurn = true;
				showCanMove = true;
				System.out.println("======*** Human Turn ***=====");

				repaint();
				showCanMove = false;

				checkWin();
				repaint();
			} else {
				ai = new ArtificialIntelligence(gameSetting, positionBoard.copy(gameSetting.getLevel(), gameSetting));
				positionBoard = ai.getNextPosition();
				isHumanTurn = true;
				showCanMove = true;
	
				repaint();
				showCanMove = false;
	
				checkWin();
				repaint();
			}
		}
	}
	
	public void moveHumanInWatchMode() {
		if (gameSetting.isWatchMode() && !win && isHumanTurn) {
			ai = new ArtificialIntelligence(gameSetting, positionBoard.copy(gameSetting.getLevel(), gameSetting));
			positionBoard = ai.getNextHumanPosition();
			
			isHumanTurn = false;
			showCanMove = true;
			repaint();
			showCanMove = false;

			checkWin();
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		win = false;
		if (!gameSetting.isWatchMode()) {
			if (isHumanTurn) {
				int location = (7 - (int) ((e.getY() - 25) / (595 / 8))) * 8 + (int) ((e.getX() - 20) / (600 / 8));
				if (positionBoard.wasSetHuman(location)) {
					showCanMove = true;
					wasChoisePieces = true;
					piecesChoise = positionBoard.getChoisePiecesHuman(location);
					piecesChoise.setNumberCanMove();
					
					repaint();
				} else {
					if (wasChoisePieces && piecesChoise.canMove(location) && gameSetting.isAiPlay()) {
						Move move = new Move(piecesChoise, location);

						positionBoard = positionBoard.newPositionBoard(move);

						isHumanTurn = false;
						showCanMove = false;
						System.out.println("======**** Ai Turn ****======");

						checkWin();
						wasChoisePieces = false;
						repaint();
					} else if (wasChoisePieces && piecesChoise.canMove(location) && !gameSetting.isAiPlay()) {
						Move move = new Move(piecesChoise, location);

						positionBoard = positionBoard.newPositionBoard(move);

						isHumanTurn = false;
						showCanMove = false;

						checkWin();
						wasChoisePieces = false;
						repaint();
					} 
				}

			} else {
				if (!gameSetting.isAiPlay()) {
					int location = (7 - (int) ((e.getY() - 25) / (595 / 8))) * 8 + (int) ((e.getX() - 20) / (600 / 8));
					if (positionBoard.wasSetAi(location)) {
						showCanMove = true;
						wasChoisePieces = true;
						piecesChoise = positionBoard.getChoisePiecesAi(location);
						piecesChoise.setNumberCanMove();
						repaint();
					} else {
						if (wasChoisePieces && piecesChoise.canMove(location)) {
							Move move = new Move(piecesChoise, location);
							positionBoard = positionBoard.newPositionBoard(move);
							isHumanTurn = true;
							repaint();
							showCanMove = false;
							checkWin();
							wasChoisePieces = false;
							repaint();
						}
					}
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
					isHumanTurn = true;
					repaint();
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
				if (value == JOptionPane.YES_OPTION)
				{
					this.positionBoard = new PositionBoard(gameSetting.getLevel(), gameSetting, this);
					isHumanTurn = true;
					repaint();
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
	public void mouseReleased(MouseEvent e) {
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
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
