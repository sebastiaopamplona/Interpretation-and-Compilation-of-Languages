package icl.interpreter.jasmin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import icl.interpreter.values.Var;

public class JasminCode {
        	
	private static final String ASSEMBLER_INTRO = ".class public Assembler\n" + 
			".super java/lang/Object\n" + 
			"\n" + 
			".method public <init>()V\n" + 
			"\taload_0\n" + 
			"\tinvokenonvirtual java/lang/Object/<init>()V\n" + 
			"\treturn\n" + 
			".end method\n" + 
			"\n" + 
			".method public static main([Ljava/lang/String;)V\n" + 
			"\t; set limits used by this method\n" + 
			"\t.limit locals 10\n" + 
			"\t.limit stack 256\n" + 
			"\n" + 
			"\t; 1 - the PrintStream object held in java.lang.out\n" + 
			"\tgetstatic java/lang/System/out Ljava/io/PrintStream;\n" + 
			"\n" + 
			"\taconst_null\n" + 
			"\tastore 4\n" +
			"\t; ----- END OF INTRO CODE\n \n";
	private static final String ASSEMBLER_EPILOGUE = "\t; ----- START OF EPILOGUE CODE\n" + 
			"\n" + 
			"\t; convert to String;\n" + 
			"\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n" + 
			"\t; call println\n" + 
			"\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" + 
			"\n" + 
			"\treturn\n" + 
			".end method\n";
	private static final String FRAME_EPILOGUE = "\n.method public <init>()V\n" +
			"\taload_0\n" +
			"\tinvokenonvirtual java/lang/Object/<init>()V\n" + 
			"\treturn\n" + 
			".end method\n";
	
	private static JasminCode instance;
	private static String output;
	private static Map<String, String> typeParser;
	private static int closureCount;
	private static int conditionalCount;
	private static boolean activeWhile;
	private static String reverseOutput;
	private static boolean deleteVariable;
	
	private JasminCode() {}
	
	public static JasminCode getInstance() {
		if (instance == null) {
			instance = new JasminCode();
			output = ASSEMBLER_INTRO;
			typeParser = new HashMap<String, String>(4);
			typeParser.put("TInt", "I");
			typeParser.put("TReference", "Ljava/lang/Object;");
			typeParser.put("TBoolean", "I"); 
			closureCount = -1;
			conditionalCount = -2;
			activeWhile = false;
			reverseOutput = "\n";
			deleteVariable = false;
		}
		
		return instance;
	}
	
	public void reverseEmit(String command) {
		reverseOutput += "\t" + command + "\n";
	}
	
	public String getReverOutput() {
		String temp = reverseOutput;
		reverseOutput = "";
		return temp;
	}
	
	public void emit(String command) {
		output += "\t" + command + "\n";
	}
	
	public void dump(String filepath) throws IOException {
		output += "\n" + ASSEMBLER_EPILOGUE;
		
		FileOutputStream fos = new FileOutputStream(filepath);
		fos.write(output.getBytes());
		fos.close();
		output = ASSEMBLER_INTRO;
	}
	
	public void createFrame(String filepath, String frame) throws IOException{
		FileOutputStream fos = new FileOutputStream(filepath);
		fos.write((frame + FRAME_EPILOGUE).getBytes());
		fos.close();
	}
	
	public void createFrame(int level, List<Var> vars) throws IOException {
		String frameName = "f" + level;
		String frame = ".class " + frameName+ "\n"
					  + ".super java/lang/Object\n";
		if(level == 1)
			frame += ".field public sl Ljava/lang/Object;\n";
		else
			frame += ".field public sl Lf" + (level-1) + ";\n";
		
		for(Var var: vars) {
			String iType = var.getType().getClass().getSimpleName();
			frame += ".field public " + var.getId() + " " + JasminCode.getInstance().getTypeParser().get(iType) + "\n";
		}
		
		FileOutputStream fos = new FileOutputStream("./" + frameName + ".j");
		fos.write((frame + FRAME_EPILOGUE).getBytes());
		fos.close();
		
		emit("new " + frameName + "\n" 
				+ "\tdup\n"
				+ "\tinvokespecial " + frameName + "/<init>()V\n"
				+ "\tdup\n");
		
		emit("aload 4\n");
		if(level == 1)
			emit("putfield f1/sl Ljava/lang/Object;\n");
		else
			emit("putfield f" + level + "/sl Lf" + (level-1) + ";");
		
		emit("astore 4\n");
	}
	
	public Map<String, String> getTypeParser(){
		return typeParser;
	}
	
	public int closureCount() {
		return closureCount += 1; 
	}
	
	public int conditionalCount() {
		return conditionalCount += 2; 
	}
	
	public void toggleWhile() {
		activeWhile = !activeWhile;
	}
	
	public boolean isWhileActive() {
		return activeWhile;
	}
	
	public void toggleDeleteVariable() {
		deleteVariable = !deleteVariable;
	}
	
	public boolean isDeleteVariableActive() {
		return deleteVariable;
	}
}
