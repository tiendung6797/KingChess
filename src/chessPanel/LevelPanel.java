package chessPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import core.GameSetting;
import core.Utils;

public class LevelPanel extends JPanel {
	private GameSetting gameSetting;
	private JLabel radio4, radio5, radio6;

	public LevelPanel(GameSetting gameSetting) {
		this.setBounds(0, Utils.BOARD_GAME_HEIGHT, 360, 60);
		this.setFocusable(true);
		this.gameSetting = gameSetting;
		
		Box box = Box.createHorizontalBox();
		ImageIcon labelIconLevel = Utils.resizeImageIcon("level", 70, 40);
		JLabel labelLevel = new JLabel(labelIconLevel);
		box.add(labelLevel);
		
		addRadioButton(box);

		this.add(box);
	}

	public void addRadioButton(Box box) {
		radio4 = createRadioButton("4", "4", box);
		radio5 = createRadioButton("5", "5", box);
		radio6 = createRadioButton("6", "6", box);
		switch (gameSetting.getLevel()) {
		case 4:
			Utils.changeStateOfRadioButton(radio4, "set4");
			break;
		case 5:
			Utils.changeStateOfRadioButton(radio5, "set5");
			break;
		case 6:
			Utils.changeStateOfRadioButton(radio6, "set6");
			break;
		}
	}
	
	public JLabel createRadioButton(String iconName, String actionCommand, Box box) {
		JLabel radio = new JLabel();
		radio.setIcon(Utils.resizeImageIcon(iconName, Utils.RADIO_LEVEL_WIDTH, Utils.RADIO_LEVEL_HEIGHT));
		radio.setBackground(null);
		box.add(radio);
		return radio;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(Utils.IMG_PANEL, 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
}
