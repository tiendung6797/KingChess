package core;

import java.awt.Color;

public class GameSetting {
	
	private Color backgroundColor;
	
	private int level;
	
	private boolean humanFirst;
	private boolean aiFirst;
	
	
	
	private boolean aiPlay;
	
	
	
	
	private boolean watchMode;
	
	// ////////////////////////////////
	private boolean testing;
	public static int rootLevel;
	public boolean testMode;
	// /////////////////////////////////

	public GameSetting() {
		
		this.backgroundColor = new Color(0x5858FA);
		
		this.level = 4;
		GameSetting.rootLevel = this.level;
		
		this.humanFirst = false;
		this.aiFirst = true;
		
		this.aiPlay = true;
		
		
		
		
		this.watchMode = false;
		
		// ////////////////////////
		this.testing = false;
		// //////////////////////
	}

	// ////////////////////////////////////////
	public boolean isTesting() {
		return testing;
	}

	public void setTesting(boolean testing) {
		this.testing = testing;
	}

	// //////////////////////////////////////
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isHumanFirst() {
		return humanFirst;
	}

	public void setHumanFirst(boolean humanFirst) {
		this.humanFirst = humanFirst;
	}

	public boolean isAiFirst() {
		return aiFirst;
	}

	public void setAiFirst(boolean aiFirst) {
		this.aiFirst = aiFirst;
	}

	public Color getBackgroundColor() {
		return backgroundColor;

	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;

	}

	public boolean isAiPlay() {
		return aiPlay;
	}

	public void setAiPlay(boolean aiPlay) {
		this.aiPlay = aiPlay;
	}

	public boolean isWatchMode() {
		return watchMode;
	}

	public void setWatchMode(boolean watchMode) {
		this.watchMode = watchMode;
	}

}
