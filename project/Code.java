package project;

public class Code {
	public static int CODE_MAX = 2048;
	private int[] code = new int[CODE_MAX];
	
	public int getOp(int i) {
		return this.code[2 * i];
	}
	public int getArg(int i) {
		return this.code[2 * i + 1];
	}
	public void clear(int start, int end) {
		for(int i = start; i < end; i++) {
			this.code[2 * i] = 0;
		}
	}
	public int[] getCode() {
		return this.code;
	}
	public void setCode(int i, int op, int arg) {
		 this.code[2 * i] = op;
		 this.code[2 * i + 1] = arg;
	}
	public String getText(int i) {
		String s1 = Integer.toHexString(code[2 * i]).toUpperCase();
		String s2 = Integer.toHexString(code[2 * i + 1]).toUpperCase();
		if(code[2 * i + 1] < 0) {
			s2 = "-" + Integer.toHexString(-code[2 * i + 1]).toUpperCase();
		}
		return s1 + " " + s2;
	}
	public String getHex(int i) {
		return Integer.toHexString(code[2*i]).toUpperCase() + " " + Integer.toHexString(code[2 * i + 1]).toUpperCase();
	}
	public String getDecimal(int i) {
		return InstructionMap.mnemonics.get(code[2 * i]) + " " + code[2 * i + 1];
	}
}
