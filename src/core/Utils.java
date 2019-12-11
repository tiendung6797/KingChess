package core;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainGame;

public class Utils {
	public static final String GAME_NAME = "KING CHESS - ©COPYRIGHT 2019 - DESIGN BY NGUYEN TIEN DUNG";
	public static final int GAME_WIDTH = 990;
	public static final int GAME_HEIGHT = 824;
	public static final int BOARD_GAME_WIDTH = 642;
	public static final int BOARD_GAME_HEIGHT = 645;
	public static final Image GAME_AVATAR = new ImageIcon(Utils.class.getResource("/image/avatarGame.png")).getImage();
	public static final Image BOARD_INFORMATION = new ImageIcon(Utils.class.getResource("/image/information.png")).getImage();
	public static final Image IMG_PANEL = new ImageIcon(Utils.class.getResource("/image/show.jpg")).getImage();
	public static final int BUTTON_CONTROL_WIDTH = 100;
	public static final int BUTTON_CONTROL_HEIGHT = 50;
	public static final int RADIO_LEVEL_WIDTH = 50;
	public static final int RADIO_LEVEL_HEIGHT = 50;
	public static final int RADIO_LEVEL_WIDTH_1 = 60;
	public static final int RADIO_LEVEL_HEIGHT_1 = 60;
	public static final int GAME_MODE_RADIO_WIDTH = 90;
	public static final int GAME_MODE_RADIO_HEIGHT = 45;
	
	/////////////////////// For Menu Game ///////////////////////////
	public static final int MENU_GAME_WIDTH = 820;
	public static final int MENU_GAME_HEIGHT = 620;

	public static final Image BG_IMAGE_MENU = new ImageIcon(Utils.class.getResource("/image/main.jpeg")).getImage();
	public static final ImageIcon ICON_BTN_START = new ImageIcon(Utils.class.getResource("/image/iconBtnStart.png"));
	public static final ImageIcon ICON_BTN_EXIT = new ImageIcon(Utils.class.getResource("/image/iconBtnExit.png"));
	public static final ImageIcon ICON_BTN_SETTING = new ImageIcon(Utils.class.getResource("/image/iconBtnSetting.png"));
	
	public static final String TITLE_MENU_ITEM_INSTRUCTIONE = "Game Instruction";
	
	/////////////////////// For SettingFrame /////////////////////////////
	public static final String TITLE_MENU_ITEM_SETTING = "GAME SETTING";
	public static final int SETTING_FRAME_WIDTH = 480;
	public static final int SETTING_FRAME_HEIGHT = 480;
	
	////////////// For ColorFrame, ChessmanChooserFrame //////////////////////
	public static final String TITLE_COLOR_FRAME = "Color Chooser";
	public static final String TITLE_CHESSMAN_CHOOSER_FRAME = "Chessman Chooser";
	public static final int CHESSMAN_CHOOSER_FRAME_WIDTH = 800;
	public static final int CHESSMAN_CHOOSER_FRAME_HEIGHT = 600;
	
	/////////////// For BoardPanel ///////////////////////////
	public static final String RESULT_LOSE = "Oh no! You losed. Do you want to play again?";
	public static final String RESULT_WIN = "Congratulation! You are the winner. Do you want to play again?";
	public static final String TITLE_DIALOG_FAIL = "Fail";
	public static final String TITLE_DIALOG_SUCCESS = "Success";
	
	
	/////////////// For ButtonPanel ////////////////////////////
	public static final ImageIcon ICON_UNDO1 = resizeImageIcon("undo3", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	public static final ImageIcon ICON_EXIT1 = resizeImageIcon("exit3", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	public static final ImageIcon ICON_SAVE1 = resizeImageIcon("save3", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	
	public static final ImageIcon ICON_UNDO2 = resizeImageIcon("undo4", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	public static final ImageIcon ICON_EXIT2 = resizeImageIcon("exit4", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	public static final ImageIcon ICON_SAVE2 = resizeImageIcon("save4", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	
	public static final ImageIcon ICON_CHECK_TRUE = resizeImageIcon("checkTrue", RADIO_LEVEL_WIDTH, RADIO_LEVEL_HEIGHT);
	public static final ImageIcon ICON_CHECK_FALSE = resizeImageIcon("checkFalse", RADIO_LEVEL_WIDTH, RADIO_LEVEL_HEIGHT);
	
	public static final ImageIcon ICON_SETTING_OK1 = resizeImageIcon("ok3", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	public static final ImageIcon ICON_SETTING_OK2 = resizeImageIcon("ok1", BUTTON_CONTROL_WIDTH, BUTTON_CONTROL_HEIGHT);
	
	public static final String ERROR_BUTTON_COLOR_HANDLER_IN = "None Button In!";
	public static final String ERROR_BUTTON_COLOR_HANDLER_OUT = "None Button Out!";
	
	//////////////////// For RadioPanel ////////////////////////////////
	public static final ImageIcon RADIO_MODE_ON = resizeImageIcon("on", GAME_MODE_RADIO_WIDTH, GAME_MODE_RADIO_HEIGHT);
	public static final ImageIcon RADIO_MODE_OFF = resizeImageIcon("off", GAME_MODE_RADIO_WIDTH, GAME_MODE_RADIO_HEIGHT);
	
	
	//////////////////// For AudioPlayer ///////////////////////////////
	public static boolean inGame = false;
	
	
	
	public static void changeStateOfRadioButton(JLabel radio, String iconName) {
		radio.setIcon(resizeImageIcon(iconName, RADIO_LEVEL_WIDTH_1, RADIO_LEVEL_HEIGHT_1));
	}
	
	public static ImageIcon resizeImageIcon(String iconName, int width, int height) {
		ImageIcon icon = new ImageIcon(Utils.class.getResource("/image/" + iconName + ".png"));
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImg);
		return newIcon;
	}
	
	public static ImageIcon createImageIcon(String path) {
		URL imgURL = Utils.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	public static String ExportResource(String resourceName) throws Exception {
		InputStream stream = null;
		OutputStream resStreamOut = null;
		String jarFolder;
		try {
			stream = MainGame.class.getResourceAsStream(resourceName);
			if (stream == null) {
				throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
			}
			int readBytes;
			byte[] buffer = new byte[4096];
			jarFolder = new File(MainGame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
					.getParentFile().getPath().replace('\\', '/');
			resStreamOut = new FileOutputStream(jarFolder + resourceName);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			stream.close();
			resStreamOut.close();
		}

		return jarFolder + resourceName;
	}
}
