package main;

import gameframe.MenuFrame;

import java.io.File;

import core.GameSetting;
import core.Utils;

public class MainGame {
	public static void main(String[] args) {
		GameSetting gameSetting = new GameSetting();
		new MenuFrame(gameSetting);
	}
	
}
