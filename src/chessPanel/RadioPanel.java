package chessPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import core.GameSetting;
import core.Utils;

public class RadioPanel extends JPanel implements MouseListener {
	private GameSetting gameSetting;
	private JLabel radioWatch, lblWatchMode;
	private ImageIcon labelWatchMode;

	public RadioPanel(GameSetting gameSetting, BoardPanel boardPanel) {
		this.setBounds(360, Utils.BOARD_GAME_HEIGHT, Utils.BOARD_GAME_WIDTH, 60);
		this.setFocusable(true);
		this.setBackground(Color.GREEN);
		this.gameSetting = gameSetting;
		
		labelWatchMode = Utils.resizeImageIcon("labelWatchMode", 100, 45);
		lblWatchMode = new JLabel(labelWatchMode);
		
		Component b = Box.createRigidArea(new Dimension(40, 0));
		Box b2 = Box.createHorizontalBox();
		b2.add(lblWatchMode);
		addRadioButton(b2);			// Important Method
		this.add(b);
		this.add(b2);
		
		Timer timerSetting = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkRadioSelected();
			}
		});
		timerSetting.start();
	}

	public void addRadioButton(Box b2) {
		radioWatch = createRadioButton(null, "off", b2);
		checkRadioSelected();
	}
	private void checkRadioSelected() {
		if(gameSetting.isWatchMode()) {
			radioWatch.setIcon(Utils.RADIO_MODE_ON);
		} else {
			radioWatch.setIcon(Utils.RADIO_MODE_OFF);
		}
		
	}
	
	public JLabel createRadioButton(String name, String iconName, Box box) {
		JLabel radio = new JLabel(name);
		radio.setForeground(Color.BLUE);
		radio.setIcon(Utils.resizeImageIcon(iconName, Utils.GAME_MODE_RADIO_WIDTH, Utils.GAME_MODE_RADIO_HEIGHT));
		radio.addMouseListener(this);
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

	@Override
	public void mousePressed(MouseEvent e) {
		JLabel lbl = (JLabel) e.getSource();
		
		if(lbl == radioWatch) {
			if(gameSetting.isWatchMode()) {
				radioWatch.setIcon(Utils.RADIO_MODE_OFF);
				gameSetting.setWatchMode(false);
				gameSetting.setAiPlay(false);
			} else {
				radioWatch.setIcon(Utils.RADIO_MODE_ON);
				gameSetting.setWatchMode(true);
				gameSetting.setAiPlay(false);
			}
		} 
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
