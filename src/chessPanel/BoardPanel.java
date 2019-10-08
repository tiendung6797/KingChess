package chessPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
		if (showCanMove) {
			g2d.setColor(Color.CYAN);
			for (int i = 0; i < piecesChoise.getNumberCanMove().size(); i++) {
				g2d.setColor(Color.CYAN);
				int locationY = (int) (piecesChoise.getNumberCanMove().get(i) / 8) + 1;
				int locationX = piecesChoise.getNumberCanMove().get(i) % 8;
				g2d.fillRect(20 + (locationX) * (600 / 8), 30 + (8 - locationY) * (595 / 8), 600 / 8, 595 / 8);
				Font f = new Font("Dialog", Font.PLAIN, 40);
				g2d.setColor(Color.RED);
				g2d.drawString(String.valueOf(piecesChoise.getDefaulsePower()
								+ piecesChoise.getPowerBonuses()[piecesChoise.getNumberCanMove().get(i)]),
						40 + (locationX) * (600 / 8), 50 + (8 - locationY) * (595 / 8));

				// }
			}
			g2d.setColor(Color.BLUE);
			int locationY = (int) (piecesChoise.getNumberInBoard() / 8) + 1;
			int locationX = piecesChoise.getNumberInBoard() % 8;
			g2d.fillRect(17 + (locationX) * (600 / 8), 28 + (8 - locationY) * (595 / 8), 600 / 8, 595 / 8);
			g2d.setColor(Color.RED);
			g2d.drawString(String.valueOf(piecesChoise.getDefaulsePower()
							+ piecesChoise.getPowerBonuses()[piecesChoise.getNumberInBoard()]),
					40 + (locationX) * (600 / 8), 50 + (8 - locationY) * (595 / 8));
		}
		ImageIcon imageBackGround = new ImageIcon(this.getClass().getResource("/image/board.png"));
		g2d.drawImage(imageBackGround.getImage(), 0, 0, 640, 640, null);
		g2d.setColor(Color.WHITE);
		Font f = new Font("Dialog", Font.PLAIN, 16);
		g2d.setFont(f);
		String[] listLocationX = { "a", "b", "c", "d", "e", "f", "g", "h" };
		for (int i = 0; i <= 7; i++) {
			g2d.drawString(String.valueOf(8 - i), 5, 25 + 595 / 8 * (i) + 595 / 16);
			g2d.drawString(listLocationX[i], 20 + 600 / 8 * i + 600 / 16, 15);
		}
		this.positionBoard.draw(g);
	}

	public void moveAi() {
		if (gameSetting.isAiPlay() && !gameSetting.isWatchMode() && !win && !isHumanTurn) {
			ai = new ArtificialIntelligence(gameSetting, positionBoard);
			positionBoard = ai.getNextPosition();
			isHumanTurn = true;
			showCanMove = true;
			System.out.println("======*** Human Turn ***=====");

			repaint();
			showCanMove = false;

			checkWin();
			repaint();
		} else if (gameSetting.isWatchMode() && gameSetting.isWatchMode() && !win && !isHumanTurn) {
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

	public void moveHumanInWatchMode() {
		if (gameSetting.isWatchMode() && !win && isHumanTurn) {
			PositionBoard old = positionBoard.getOldPositionBoard();
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
