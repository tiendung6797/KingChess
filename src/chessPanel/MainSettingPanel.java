package chessPanel;

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

public class MainSettingPanel extends JPanel {

	private GameSetting gameSetting;
	private JFrame frameColor;
	private SettingFrameListener settingFrameListener;
	private ArrayList<JLabel> listLevel = new ArrayList<>();
	private JLabel btnOk;
	
	public MainSettingPanel(GameSetting gameSetting) {
		this.gameSetting = gameSetting;
		
		this.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lb1 = new JLabel(Utils.TITLE_SETTING_GAME_MODE);
		lb1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb1.setForeground(Color.WHITE);
		lb1.setFont(new Font("Serif", Font.BOLD, 20));
		
		JLabel lb2 = new JLabel(Utils.TITLE_SETTING_CHESSMAN_MOVE_FIRST);
		lb2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb2.setForeground(Color.WHITE);
		lb2.setFont(new Font("Serif", Font.BOLD, 20));
		
		JLabel lb3 = new JLabel(Utils.TITLE_SETTING_CHOICE_LEVEL);
		lb3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lb3.setForeground(Color.WHITE);
		lb3.setFont(new Font("Serif", Font.BOLD, 20));
		
		Component b1 = Box.createRigidArea(new Dimension(0, 20));
		Box box1 = Box.createVerticalBox();
		box1.add(lb1);
		box1.add(getTypePanel());
		this.add(box1);
		this.add(b1);
		
		Component b2 = Box.createRigidArea(new Dimension(0, 25));
		this.add(lb2);
		this.add(getColorPanel());
		this.add(b2);
		
		Component b3 = Box.createRigidArea(new Dimension(0, 20));
		this.add(lb3);
		this.add(getLvPanel());
		this.add(b3);
		
		Component b4 = Box.createRigidArea(new Dimension(0, 20));
		this.add(getButtonPanel());
		this.add(b4);
		
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
		
		createButtonLevel(Utils.ICON_CHECK_FALSE, rdPanel, "1");
		createButtonLevel(Utils.ICON_CHECK_FALSE, rdPanel, "2");
		createButtonLevel(Utils.ICON_CHECK_FALSE, rdPanel, "3");
		
		int i = GameSetting.rootLevel;
		checkRadioLevel(i);
		return rdPanel;
	}
	private JLabel createButtonLevel(ImageIcon icon, Box parent, String a) {
		JLabel radio = new JLabel(icon);
		radio.setText(a);
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
		
		JLabel cbBlack = new JLabel(Utils.LABEL_CHESSMAN_BLACK);
		cbBlack.setIcon(Utils.ICON_CHECK_FALSE);
		Component box = Box.createRigidArea(new Dimension(65, 0));
		JLabel cbWhite = new JLabel(Utils.LABEL_CHESSMAN_WHITE);
		cbWhite.setForeground(Color.WHITE);
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
		
		JLabel rdAi = new JLabel(Utils.LABEL_GAME_MODE_PAI);
		JLabel rd2P = new JLabel(Utils.LABEL_GAME_MODE_2P);
		
		rdAi.setIcon(Utils.ICON_RADIO_UNABLE);
		rd2P.setIcon(Utils.ICON_RADIO_UNABLE);

		if (gameSetting.isAiPlay()) {
			rdAi.setIcon(Utils.ICON_RADIO_ENABLE);
		} else {
			rd2P.setIcon(Utils.ICON_RADIO_ENABLE);
		}
		
		rdAi.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				rdAi.setIcon(Utils.ICON_RADIO_ENABLE);
				rd2P.setIcon(Utils.ICON_RADIO_UNABLE);
				gameSetting.setAiPlay(true);
			}
		});
		rd2P.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				rdAi.setIcon(Utils.ICON_RADIO_UNABLE);
				rd2P.setIcon(Utils.ICON_RADIO_ENABLE);
				gameSetting.setAiPlay(false);
			}
		});

		rdPanel.add(rd2P);
		Component area1 = Box.createRigidArea(new Dimension(10, 0));
		rdPanel.add(area1);
		rdPanel.add(rdAi);
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
