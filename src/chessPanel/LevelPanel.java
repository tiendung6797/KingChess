package chessPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.GameSetting;
import core.Utils;

public class LevelPanel extends JPanel {
	private GameSetting gameSetting;
	private JLabel radio4, radio5, radio6;

	public LevelPanel(GameSetting gameSetting) {
		this.setBounds(Utils.BOARD_GAME_WIDTH + 20, 223, 300, 210);
		this.setFocusable(true);
		this.gameSetting = gameSetting;
		Box box = Box.createVerticalBox();
		addRadioButton(box);
		Component lb = Box.createRigidArea(new Dimension(70, 0));
		this.add(lb);
		this.add(box);
	}

	public void addRadioButton(Box box) {
		radio4 = createRadioButton("uneasy", box);
		radio5 = createRadioButton("unmedium", box);
		radio6 = createRadioButton("unhard", box);
		switch (GameSetting.rootLevel) {
		case 2:
			Utils.changeStateOfRadioButton(radio4, "easy");
			break;
		case 4:
			Utils.changeStateOfRadioButton(radio5, "medium");
			break;
		case 6:
			Utils.changeStateOfRadioButton(radio6, "hard");
			break;
		}
	}

	public JLabel createRadioButton(String iconName, Box box) {
		JLabel radio = new JLabel();
		Component lb = Box.createRigidArea(new Dimension(0, 10));
		radio.setIcon(Utils.resizeImageIcon(iconName, Utils.RADIO_LEVEL_WIDTH_1, Utils.RADIO_LEVEL_HEIGHT_1));
		radio.setBackground(null);
		box.add(radio);
		box.add(lb);
		return radio;
	}

	@Override
	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(Utils.IMG_PANEL, 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		ImageIcon level = new ImageIcon(this.getClass().getResource("/image/level.png"));
		g2d.drawImage(level.getImage(), 30, 70, 105, 60, null);
	}

}
