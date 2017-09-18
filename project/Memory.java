package project;

public class Memory {
	public static int DATA_SIZE = 2048;
	private int[] data = new int[DATA_SIZE];
	private int changedIndex = -1;
	
	int[] getData() {
		return this.data;
	}
	public int getData(int index) {
		return this.data[index];
	}
	public void setData(int index, int value) {
		this.data[index] = value;
		this.changedIndex = index;
	}
	public int getChangedIndex() {
		return this.changedIndex;
	}
	public void clear(int start, int end) {
		for(int i = start; i < end; i++) {
			this.data[i] = 0;
		}
	}
}
