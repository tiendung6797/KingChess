package core;

public class GameSetting {

	private int level;

	private boolean humanFirst;
	private boolean aiFirst;

	private boolean aiPlay;

	// ////////////////////////////////
	private boolean testing;
	public static int rootLevel;
	public boolean testMode;
	// /////////////////////////////////

	public GameSetting() {

		this.level = 2;
		GameSetting.rootLevel = this.level;

		this.humanFirst = false;
		this.aiFirst = true;

		this.aiPlay = true;

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

	public boolean isAiPlay() {
		return aiPlay;
	}

	public void setAiPlay(boolean aiPlay) {
		this.aiPlay = aiPlay;
	}

}
