package project;

public class Job {
	private int startcodeIndex;
	private int codeSize;
	private int startmemoryIndex;
	private int currentIP;
	private int currentAcc;
	private States currentState;
	
	public int getStartcodeIndex() {
		return this.startcodeIndex;
	}
	public void setStartcodeIndex(int startcodeIndex) {
		this.startcodeIndex = startcodeIndex;
	}
	public int getCodeSize() {
		return this.codeSize;
	}
	public void setCodeSize(int codeSize) {
		this.codeSize = codeSize;
	}
	public int getStartmemoryIndex() {
		return this.startmemoryIndex;
	}
	public void setStartmemoryIndex(int startmemoryIndex) {
		this.startmemoryIndex = startmemoryIndex;
	}
	public int getCurrentIP() {
		return this.currentIP;
	}
	public void setCurrentIP(int currentIP) {
		this.currentIP = currentIP;
	}
	public int getCurrentAcc() {
		return this.currentAcc;
	}
	public void setCurrentAcc(int currentAcc) {
		this.currentAcc = currentAcc;
	}
	public States getCurrentState() {
		return this.currentState;
	}
	public void setCurrentState(States currentState) {
		this.currentState = currentState;
	}
	public void reset() {
		this.codeSize = 0;
		this.currentAcc = 0;
		this.currentIP = this.startcodeIndex;
		this.currentState = States.NOTHING_LOADED;
	}
}
