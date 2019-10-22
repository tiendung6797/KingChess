package chessPanel;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.GameSetting;
import core.SettingFrameListener;
import core.Utils;
import javax.swing.Icon;
import java.awt.SystemColor;

public class MainSettingPanel extends JPanel {

	private GameSetting gameSetting;
	private SettingFrameListener settingFrameListener;
	private ArrayList<JLabel> listLevel = new ArrayList<>();
	private JLabel btnOk;
	
	public MainSettingPanel(GameSetting gameSetting) {
		this.gameSetting = gameSetting;
		
		this.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lb1 = new JLabel("MODE");
		lb1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb1.setForeground(new Color(160, 82, 45));
		lb1.setFont(new Font("Times New Roman", Font.BOLD, 24));
		
		JLabel lb2 = new JLabel("WHO FIRST?");
		lb2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb2.setForeground(new Color(139, 69, 19));
		lb2.setFont(new Font("Times New Roman", Font.BOLD, 24));
		
		JLabel lb3 = new JLabel("LEVEL");
		lb3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb3.setForeground(new Color(139, 69, 19));
		lb3.setFont(new Font("Times New Roman", Font.BOLD, 24));
		
		Component b1 = Box.createRigidArea(new Dimension(0, 20));
		Component b2 = Box.createRigidArea(new Dimension(0, 7));
		Component b3 = Box.createRigidArea(new Dimension(0, 30));
		Component b4 = Box.createRigidArea(new Dimension(0, 7));
		Component b5 = Box.createRigidArea(new Dimension(0, 30));
		Component b6 = Box.createRigidArea(new Dimension(0, 7));
		Component b7 = Box.createRigidArea(new Dimension(0, 30));
		
		this.add(b1);
		this.add(lb1);
		this.add(b2);
		this.add(getTypePanel());
		this.add(b3);
		
		this.add(lb2);
		this.add(b4);
		this.add(getColorPanel());
		this.add(b5);
		
		this.add(lb3);
		this.add(b6);
		this.add(getLvPanel());
		this.add(b7);
		
		this.add(getButtonPanel());
		
		Timer timerRadioLevel = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = gameSetting.getLevel();
				checkRadioLevel(i);
			}
		});
		timerRadioLevel.start();
	}
	

	/**
	 * 		Level Panel
	 **/
	private Box getLvPanel() {
		Box rdPanel = Box.createHorizontalBox();
		rdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		createButtonLevel(Utils.ICON_CHECK_FALSE, rdPanel, "Easy");
		createButtonLevel(Utils.ICON_CHECK_FALSE, rdPanel, "Medium");
		createButtonLevel(Utils.ICON_CHECK_FALSE, rdPanel, "Hard");
		
		int i = GameSetting.rootLevel;
		checkRadioLevel(i);
		return rdPanel;
	}
	private JLabel createButtonLevel(ImageIcon icon, Box parent, String level) {
		JLabel radio = new JLabel(level);
		radio.setForeground(Color.BLACK);
		radio.setFont(new Font("Times New Roman", Font.BOLD, 19));
		radio.setIcon(icon);
		radio.addMouseListener(new RadioLevelHandler());
		listLevel.add(radio);
		parent.add(radio);
		Component c = Box.createRigidArea(new Dimension(20, 0));
		parent.add(c);
		return radio;
	}
	private void checkRadioLevel(int id) {
		for(int i = 4; i <= 6; i++) {
			if(id == i) {
				listLevel.get(i-4).setIcon(Utils.resizeImageIcon("checkTrue", Utils.RADIO_LEVEL_WIDTH, Utils.RADIO_LEVEL_HEIGHT));
			} else {
				listLevel.get(i-4).setIcon(Utils.resizeImageIcon("checkFalse", Utils.RADIO_LEVEL_WIDTH, Utils.RADIO_LEVEL_HEIGHT));
			}
		}
	}
	
	class RadioLevelHandler implements MouseListener {
		@Override
		public void mousePressed(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			for(JLabel lbl : listLevel) {
				int i = listLevel.indexOf(lbl) + 4;
				if(label == lbl) {
					gameSetting.setLevel(i);
					GameSetting.rootLevel = gameSetting.getLevel();
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

	/**
	 * 			Color Panel
	 **/
	private Box getColorPanel() {
		Box rdPanel = Box.createHorizontalBox();
		rdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel cbBlack = new JLabel("Black pieces");
		cbBlack.setForeground(Color.BLACK);
		cbBlack.setFont(new Font("Times New Roman", Font.BOLD, 19));
		cbBlack.setIcon(Utils.ICON_CHECK_FALSE);
		Component box = Box.createRigidArea(new Dimension(40, 0));
		JLabel cbWhite = new JLabel("White pieces");
		cbWhite.setForeground(Color.BLACK);
		cbWhite.setFont(new Font("Times New Roman", Font.BOLD, 19));
		cbWhite.setIcon(Utils.ICON_CHECK_FALSE);
		if (gameSetting.isAiFirst()) {
			cbBlack.setIcon(Utils.ICON_CHECK_TRUE);
		} else {
			cbWhite.setIcon(Utils.ICON_CHECK_TRUE);
		}
		
		cbBlack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cbBlack.setIcon(Utils.ICON_CHECK_TRUE);
				cbWhite.setIcon(Utils.ICON_CHECK_FALSE);
				gameSetting.setAiFirst(true);
				gameSetting.setHumanFirst(false);
			}
		});
		cbWhite.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cbBlack.setIcon(Utils.ICON_CHECK_FALSE);
				cbWhite.setIcon(Utils.ICON_CHECK_TRUE);
				gameSetting.setAiFirst(false);
				gameSetting.setHumanFirst(true);
			}
		});
		
		rdPanel.add(cbBlack);
		rdPanel.add(box);
		rdPanel.add(cbWhite);
		return rdPanel;
	}

	/**
	 * 			Game Mode Panel
	 **/
	private Box getTypePanel() {
		Box rdPanel = Box.createHorizontalBox();
		rdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel rdAi = new JLabel("Computer");
		rdAi.setForeground(Color.BLACK);
		rdAi.setFont(new Font("Times New Roman", Font.BOLD, 19));
		JLabel rd2P = new JLabel("Human");
		rd2P.setForeground(Color.BLACK);
		rd2P.setFont(new Font("Times New Roman", Font.BOLD, 19));
		JLabel rdTr = new JLabel("Training");
		rdTr.setForeground(Color.BLACK);
		rdTr.setFont(new Font("Times New Roman", Font.BOLD, 19));

		if (gameSetting.isAiPlay()) {
			rdAi.setIcon(Utils.ICON_CHECK_TRUE);
			rd2P.setIcon(Utils.ICON_CHECK_FALSE);
		}else {
			rdAi.setIcon(Utils.ICON_CHECK_FALSE);
			rd2P.setIcon(Utils.ICON_CHECK_TRUE);
		}
		
		rdAi.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				rdAi.setIcon(Utils.ICON_CHECK_TRUE);
				rd2P.setIcon(Utils.ICON_CHECK_FALSE);
				gameSetting.setAiPlay(true);
			}
		});
		rd2P.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				rdAi.setIcon(Utils.ICON_CHECK_FALSE);
				rd2P.setIcon(Utils.ICON_CHECK_TRUE);
				gameSetting.setAiPlay(false);
			}
		});

		rdPanel.add(rd2P);
		Component area1 = Box.createRigidArea(new Dimension(14, 0));
		rdPanel.add(area1);
		rdPanel.add(rdAi);
		Component area2 = Box.createRigidArea(new Dimension(14, 0));
		rdPanel.add(area2);
		rdPanel.add(rdTr);
		return rdPanel;
	}

	/**
	 * 			Button Control Panel
	 **/
	private Box getButtonPanel() {
		Box rdPanel = Box.createHorizontalBox();
		rdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnOk = new JLabel();
		btnOk.setIcon(Utils.ICON_SETTING_OK1);
		btnOk.addMouseListener(new ButtonColorHandler());

		Component c = Box.createRigidArea(new Dimension(20, 0));
		rdPanel.add(c);
		rdPanel.add(btnOk);
		return rdPanel;
	}
	class ButtonColorHandler implements MouseListener {
		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if(label == btnOk) {
				btnOk.setIcon(Utils.ICON_SETTING_OK2);
			} else {
				System.err.println(Utils.ERROR_BUTTON_COLOR_HANDLER_IN);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if(label == btnOk) {
				btnOk.setIcon(Utils.ICON_SETTING_OK1);
			} else {
				System.err.println(Utils.ERROR_BUTTON_COLOR_HANDLER_OUT);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if(label == btnOk) {
				if(settingFrameListener != null) {
					settingFrameListener.closeSettingFrameOccurred();
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(Utils.IMG_PANEL, 0, 0, d.width, d.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	public void setSettingFrameListener(SettingFrameListener sfl) {
		this.settingFrameListener = sfl;
	}
}
