package project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel {
	public Map<Integer, Instruction> INSTRUCTIONS = new TreeMap<>();
	private CPU cpu = new CPU();
	private Memory memory = new Memory();
	private HaltCallback callback;
	private Code code = new Code();
	private Job currentJob;
	private Job[] jobs = new Job[2];
	
	public MachineModel() {
		this(() -> System.exit(0));
	}
	public MachineModel(HaltCallback callback) {
		this.callback = callback;
		//INSTRUCTION_MAP entry for "NOP"
		INSTRUCTIONS.put(0x0, arg -> {
			cpu.incrementIP();
		});
		//INSTRUCTION_MAP entry for "LODI"
		INSTRUCTIONS.put(0x1, arg -> {
			cpu.setAccumulator(arg);
			cpu.incrementIP();
		});
		//INSTRUCTION_MAP entry for "LOD"
		INSTRUCTIONS.put(0x2, arg -> {
			int arg1 = memory.getData(cpu.getMemoryBase()+arg);
			cpu.setAccumulator(arg1);
			cpu.incrementIP();
		});
		//INSTRUCTION_MAP entry for "LODN"
		INSTRUCTIONS.put(0x3, arg -> {
			int arg1 = memory.getData(cpu.getMemoryBase()+arg);
			int arg2 = memory.getData(cpu.getMemoryBase()+arg1);
			cpu.setAccumulator(arg2);
			cpu.incrementIP();
		});
		//INSTRUCTION_MAP entry for "STO"
		INSTRUCTIONS.put(0x4, arg -> {
			int arg1 = cpu.getMemoryBase()+arg;
			memory.setData(arg1, cpu.getAccumulator());
			cpu.incrementIP();
		});
		//INSTRUCTION_MAP entry for "STON"
		INSTRUCTIONS.put(0x5, arg -> {
			int arg1 = cpu.getMemoryBase()+arg;
			int arg2 = cpu.getMemoryBase() + memory.getData(arg1);
			memory.setData(arg2, cpu.getAccumulator());
			cpu.incrementIP();
		});
		//INSTRUCTION_MAP entry for "JMPI"
		INSTRUCTIONS.put(0x6, arg -> {
			cpu.setInstructionPointer(cpu.getInstructionPointer() + arg);
		});
		//INSTRUCTION_MAP entry for "JUMP"
		INSTRUCTIONS.put(0x7, arg -> {
			int arg1 = memory.getData(cpu.getMemoryBase()+arg);
			cpu.setInstructionPointer(cpu.getInstructionPointer() + arg1);
		});
		//INSTRUCTION_MAP entry for "JMZI"
		INSTRUCTIONS.put(0x8, arg -> {
			if(cpu.getAccumulator() == 0) {
				cpu.setInstructionPointer(cpu.getInstructionPointer() + arg);
			}
			else {
				cpu.incrementIP();
			}
		});
		//INSTRUCTION_MAP entry for "JMPZ"
		INSTRUCTIONS.put(0x9, arg -> {
			if(cpu.getAccumulator() ==0) {
				int arg1 = memory.getData(cpu.getMemoryBase()+arg);
				cpu.setInstructionPointer(cpu.getInstructionPointer() + arg1);
			}
			else {
				cpu.incrementIP();
			}
		});
		 //INSTRUCTION_MAP entry for "ADDI"
        INSTRUCTIONS.put(0xA, arg -> {
            cpu.setAccumulator(cpu.getAccumulator() + arg);
            cpu.incrementIP();
        });

        //INSTRUCTION_MAP entry for "ADD"
        INSTRUCTIONS.put(0xB, arg -> {
            int arg1 = memory.getData(cpu.getMemoryBase()+arg);
            cpu.setAccumulator(cpu.getAccumulator() + arg1);
            cpu.incrementIP();
        });

        //INSTRUCTION_MAP entry for "ADDN"
        INSTRUCTIONS.put(0xC, arg -> {
            int arg1 = memory.getData(cpu.getMemoryBase()+arg);
            int arg2 = memory.getData(cpu.getMemoryBase()+arg1);
            cpu.setAccumulator(cpu.getAccumulator() + arg2);
            cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "SUBI"
        INSTRUCTIONS.put(0xD, arg -> {
        	cpu.setAccumulator(cpu.getAccumulator() - arg);
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "SUB"
        INSTRUCTIONS.put(0xE, arg -> {
        	int arg1 = memory.getData(cpu.getMemoryBase()+arg);
        	cpu.setAccumulator(cpu.getAccumulator() - arg1);;
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "SUBN"
        INSTRUCTIONS.put(0xF, arg -> {
        	int arg1 = memory.getData(cpu.getMemoryBase()+arg);
        	int arg2 = memory.getData(cpu.getMemoryBase()+arg1);
        	cpu.setAccumulator(cpu.getAccumulator() - arg2);;
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "MULI"
        INSTRUCTIONS.put(0x10, arg -> {
        	cpu.setAccumulator(cpu.getAccumulator() * arg);
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "MUL"
        INSTRUCTIONS.put(0x11, arg -> {
        	int arg1 = memory.getData(cpu.getMemoryBase()+arg);
        	cpu.setAccumulator(cpu.getAccumulator() * arg1);
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "MULN"
        INSTRUCTIONS.put(0x12, arg -> {
        	int arg1 = memory.getData(cpu.getMemoryBase()+arg);
        	int arg2 = memory.getData(cpu.getMemoryBase()+arg1);
        	cpu.setAccumulator(cpu.getAccumulator() * arg2);
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "DIVI"
        INSTRUCTIONS.put(0x13, arg -> {
        	if(arg == 0) {
        		throw new DivideByZeroException("Cannot divide by zero");
        	}
        	else {
        		cpu.setAccumulator(cpu.getAccumulator() / arg);
        		cpu.incrementIP();
        	}
        });
        //INSTRUCTION_MAP entry for "DIV"
        INSTRUCTIONS.put(0x14, arg -> {
        	int arg1 = memory.getData(cpu.getMemoryBase()+arg);
        	if(arg1 == 0) {
        		throw new DivideByZeroException("Cannot divide by zero");
        	}
        	else {
        		cpu.setAccumulator(cpu.getAccumulator() / arg1);
        		cpu.incrementIP();
        	}
        });
        //INSTRUCTION_MAP entry for "DIVN"
        INSTRUCTIONS.put(0x15, arg -> {
        	int arg1 = memory.getData(cpu.getMemoryBase()+arg);
        	int arg2 = memory.getData(cpu.getMemoryBase()+arg1);
        	if(arg2 == 0) {
        		throw new DivideByZeroException("Cannot divide by zero");
        	}
        	else {
        		cpu.setAccumulator(cpu.getAccumulator() / arg2);
        		cpu.incrementIP();
        	}
        });
        //INSTRUCTION_MAP entry for "ANDI"
        INSTRUCTIONS.put(0x16, arg -> {
        	if(cpu.getAccumulator() != 0 && arg != 0) {
        		cpu.setAccumulator(1);
        	}
        	else {
        		cpu.setAccumulator(0);
        	}
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "AND"
        INSTRUCTIONS.put(0x17, arg -> {
        	if(cpu.getAccumulator() != 0 && memory.getData(cpu.getMemoryBase()+arg) != 0) {
        		cpu.setAccumulator(1);
        	}
        	else {
        		cpu.setAccumulator(0);
        	}
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "NOT"
        INSTRUCTIONS.put(0x18, arg -> {
        	if(cpu.getAccumulator() != 0) {
        		cpu.setAccumulator(0);
        	}
        	else {
        		cpu.setAccumulator(1);
        	}
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "CMPL"
        INSTRUCTIONS.put(0x19, arg -> {
        	if(memory.getData(cpu.getMemoryBase()+arg) < 0) {
        		cpu.setAccumulator(1);
        	}
        	else {
        		cpu.setAccumulator(0);
        	}
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "CMPZ"
        INSTRUCTIONS.put(0x1A, arg -> {
        	if(memory.getData(cpu.getMemoryBase()+arg) == 0) {
        		cpu.setAccumulator(1);
        	}
        	else {
        		cpu.setAccumulator(0);
        	}
        	cpu.incrementIP();
        });
        //INSTRUCTION_MAP entry for "JMPN"
        INSTRUCTIONS.put(0x1B, arg -> {
        	int target = memory.getData(cpu.getMemoryBase() + arg);
        	cpu.setInstructionPointer(currentJob.getStartcodeIndex()+target);
        });
        //INSTRUCTION_MAP entry for "HALT"
        INSTRUCTIONS.put(0x1F, arg -> {
        	this.callback.halt();
        });
        this.currentJob = new Job();
        this.currentJob.setStartcodeIndex(0);
        this.currentJob.setStartmemoryIndex(0);
        this.jobs[0] = this.currentJob;
        
        Job job1 = new Job();
        job1.setStartcodeIndex(Code.CODE_MAX / 4);
        job1.setStartmemoryIndex(Memory.DATA_SIZE / 2);
        this.jobs[1] = job1;
	}
	public Instruction get(int memory) {
		return INSTRUCTIONS.get(memory);
	}
	public CPU getCpu() {
		return cpu;
	}
	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}
	public Memory getMemory() {
		return memory;
	}
	public void setMemory(Memory memory) {
		this.memory = memory;
	}
	public int getMemoryBase() {
		return this.cpu.getMemoryBase();
	}
	public void setMemoryBase(int memoryBase) {
		this.cpu.setMemoryBase(memoryBase);
	}
	public int getAccumulator() {
		return this.cpu.getAccumulator();
	}
	public void setAccumulator(int accumulator) {
		this.cpu.setAccumulator(accumulator);
	}
	public int getChangedIndex() {
		return memory.getChangedIndex();
	}
	public int[] getData() {
		return this.memory.getData();
	}
	public int getData(int index) {
		return this.memory.getData(index);
	}
	public void setData(int index, int value) {
		this.memory.setData(index, value);
	}
	public int getInstructionPointer() {
		return this.cpu.getInstructionPointer();
	}
	public void setInstructionPointer(int instructionPointer) {
		this.cpu.setInstructionPointer(instructionPointer);
	}
	public Code getCode() {
		return this.code;
	}
	public void setCode(int i, int op, int arg) {
		code.setCode(i, op, arg);
	}
	public Job getCurrentJob() {
		return this.currentJob;
	}
	public void setJob(int i) throws IllegalArgumentException {
		if(i != 0 && i != 1) {
			throw new IllegalArgumentException("parameter has to be either 0 or 1");
		}
		else {
			this.currentJob.setCurrentAcc(cpu.getAccumulator());
			this.currentJob.setCurrentIP(cpu.getInstructionPointer());
			this.currentJob = this.jobs[i];
			this.cpu.setAccumulator(this.currentJob.getCurrentAcc());
			this.cpu.setInstructionPointer(this.currentJob.getCurrentIP());
			this.cpu.setMemoryBase(this.currentJob.getStartmemoryIndex());
		}
	}
	public States getCurrentState() {
		return this.currentJob.getCurrentState();
	}
	public void setCurrentState(States currentState) {
		this.currentJob.setCurrentState(currentState);
	}
	public void clearJob() {
		this.memory.clear(this.currentJob.getStartmemoryIndex(), this.currentJob.getStartmemoryIndex() + (Memory.DATA_SIZE / 2));
		this.code.clear(this.currentJob.getStartcodeIndex(), this.currentJob.getStartcodeIndex() + currentJob.getCodeSize());
		cpu.setAccumulator(0);
		cpu.setInstructionPointer(this.currentJob.getStartcodeIndex());
		this.currentJob.reset();
	}
	public void step() {
		try {
			int ip = cpu.getInstructionPointer();
			if(ip < this.currentJob.getStartcodeIndex() || ip >= this.currentJob.getStartcodeIndex() + this.currentJob.getCodeSize()) {
				throw new CodeAccessException("Instruction Pointer is not between " + this.currentJob.getStartcodeIndex() + " and " + (this.currentJob.getStartcodeIndex() + this.currentJob.getCodeSize())); 
			}
			int opcode = this.code.getOp(ip);
			int arg = this.code.getArg(ip);
			get(opcode).execute(arg);
		}
		catch(Exception e) {
			this.callback.halt();
			throw e;
		}
	}
}
