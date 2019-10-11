package gameframe;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.ButtonLoad;
import core.GameSetting;
import core.Utils;

public class MenuFrame extends JFrame {
	private GameSetting gameSetting;

	public MenuFrame(GameSetting gameSetting) {
		this.setSize(Utils.MENU_GAME_WIDTH, Utils.MENU_GAME_HEIGHT);
		this.setTitle(Utils.GAME_NAME);
		this.setIconImage(Utils.GAME_AVATAR);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameSetting = gameSetting;
		PanelMenu panel = new PanelMenu(this);
		this.add(panel);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	class PanelMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel btnStart, btnExit, btnSetting;
		private ButtonLoad btnLoad;

		public PanelMenu(MenuFrame menuFrame) {
			Box box = Box.createVerticalBox();
			btnSetting = new JLabel(Utils.ICON_BTN_SETTING);
			btnStart = new JLabel(Utils.ICON_BTN_START);
			btnExit = new JLabel(Utils.ICON_BTN_EXIT);
			btnLoad = new ButtonLoad(menuFrame, gameSetting);

			Component b1 = Box.createRigidArea(new Dimension(0, 10));
			
			box.setPreferredSize(new Dimension(375, 600));
			box.add(Box.createVerticalStrut(20));
			box.add(btnStart);
			box.add(btnLoad);
			box.add(btnSetting);
			box.add(b1);
			box.add(btnExit);

			this.add(box);
			btnStart.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					menuFrame.setVisible(false);
					Utils.inGame = true;
					new MainChessFrame(gameSetting, menuFrame,true);
				}
			});
			btnSetting.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					new SettingFrame(gameSetting);
				}
			});

			btnExit.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					System.exit(1);
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Dimension d = getSize();
			g.drawImage(Utils.BG_IMAGE_MENU, 0, 0, d.width, d.height, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}
}
