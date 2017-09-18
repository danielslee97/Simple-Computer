package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class OldAssembler {
	public static String assemble(File input, File output) {
		String retVal = "success";
		ArrayList<String> code = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		ArrayList<String> inText = new ArrayList<>();
		try {
			Scanner sc = new Scanner(input);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				inText.add(line);
			}
			int maxLine = inText.size();
			for(int i = 0; i < maxLine; i++) {
				String line = inText.get(i);
				if(line.trim().length() == 0) {
					line = inText.get(i + 1);
					if(line.trim().length() > 0) {
						retVal += "\nError: line " + i + " is a blank line";
						maxLine = i;
					}
				}
			}
			for(int i = 0; i < maxLine; i++) {
				String line = inText.get(i);
				if(line.charAt(0) == ' ' || line.charAt(0) == '\t') {
					retVal += "\nError: line " + i + " starts with white space";
					maxLine = i;
				}
			}
			for(int i = 0; i < maxLine; i++) {
				String line = inText.get(i);
				if(line.trim().toUpperCase().equals("DATA")) {
					if(!line.trim().equals("DATA")) {
						retVal += "\nError: line " + i + " does not have DATA in upper case";
						maxLine = i;
					}
				}
			}
			if(maxLine == inText.size()) {
				boolean change = false;
				for(String line : inText) {
					if(line.equals("DATA")) {
						change = true;
					}
					else if(!change) {
						code.add(line);
					}
					else {
						data.add(line);
					}
				}
			}
			sc.close();
			ArrayList<String> outText = new ArrayList<>();
			for(int i = 0; i < code.size(); i++) {
				String line = code.get(i);
				String[] parts = line.trim().split("\\s+");
				if(InstructionMap.sourceCodes.contains(parts[0].toUpperCase()) && !InstructionMap.sourceCodes.contains(parts[0])) {
					retVal += "\nError: line " + i + " does not have the instruction mnemonic in upper case";
				}
				else {
					if(InstructionMap.noArgument.contains(parts[0]) && parts.length != 1) {
						retVal += "\nError: line " + i + " has an illegal argument";
					}
					if(!InstructionMap.noArgument.contains(parts[0])) {
						if(parts.length == 1) {
							retVal += "\nError: line " + i + " is missing an argument";
						}
						if(parts.length == 2) {
							if(InstructionMap.immediateOK.contains(parts[0]) && parts[1].startsWith("#")) {
								if(parts[0].equals("JUMP")) {
									parts[0] = "JMPI";
									parts[1] = parts[1].substring(1);
								}
								else if(parts[0].equals("JMPZ")) {
									parts[0] = "JMZI";
									parts[1] = parts[1].substring(1);
								}
								else {
									parts[0] += "I";
									parts[1] = parts[1].substring(1);
								}
							}
							else if(InstructionMap.indirectOK.contains(parts[0]) && parts[1].startsWith("&")) {
								if(parts[0].equals("JUMP")) {
									parts[0] = "JMPN";
									parts[1] = parts[1].substring(1);
								}
								parts[0] += "N";
								parts[1] = parts[1].substring(1);
							}
							int arg = 0; 
							try {
								arg = Integer.parseInt(parts[1],16); //<<<<< CORRECTION
							} catch (NumberFormatException e) {
								retVal += "\nError: line " + i
									+ " does not have a numberic argument";
							}
						}
						else {
							retVal += "\nError: line " + i + " has more than one argument";
						}
					}
				}
				int opcode = InstructionMap.opcode.get(parts[0]);
				if(parts.length == 1) {
					outText.add(Integer.toHexString(opcode).toUpperCase() + " 0");
				}
				else {
					outText.add(Integer.toHexString(opcode).toUpperCase() + " " + parts[1]);
				}
			}
			outText.add("-1");
			outText.addAll(data);
			
			PrintWriter out = new PrintWriter(output);
			for(int i = 0; i < outText.size(); i++) {
				String line = outText.get(i);
				out.println(line);
			}
			out.close();
			return retVal;
		}
		catch(FileNotFoundException e) {
			return "fail";
		}
	}
	public static void main(String[] args) {
		File in = new File("01.pasm");
		File out = new File("01.pexe");
		assemble(in, out);
	}
}
