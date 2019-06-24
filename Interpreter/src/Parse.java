import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import icl.interpreter.ast.*;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TNone;
import icl.interpreter.values.IValue;

public class Parse {

	private static final String JASMIN_FILEPATH = "assembler.j";
	
	@SuppressWarnings("static-access")
	public static void main(String args[]) {

		Parser parser = new Parser(System.in);
		ASTNode exp;
		Memory memory = new Memory();

		while (true) {
			System.out.println("Please write an expression: ");
			try {
				Enviornment<IType> typeEnv = new Enviornment<IType>();
				Enviornment<IValue> valueEnv = new Enviornment<IValue>();
				exp = parser.Start();
				if (!(exp.typecheck(typeEnv) instanceof TNone)) {
					
					JasminCode j = JasminCode.getInstance();
					exp.compile(1);
					j.dump(JASMIN_FILEPATH);
					
					compileJasminFiles();
					Thread.sleep(100);
					Process proc = Runtime.getRuntime().exec("java Assembler");
					BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
					System.out.println("Compiler: " + reader.readLine());
					
					System.out.println("Eval: " + exp.eval(memory, valueEnv));
					System.out.println();
				}
				else
					System.err.println("Compile time error.");
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				e.printStackTrace();
				// e.printStackTrace();
				// System.out.println("Syntax Error!");
				parser.ReInit(System.in);
			}
		}
	}
	
	private static void compileJasminFiles() throws IOException {
		File folder = new File("./");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File file = listOfFiles[i];
				String[] str = file.toString().split("./")[1].split("\\.");
					if(str[1].equals("j"))
						Runtime.getRuntime().exec("jasmin " +  str[0] + ".j");
			}
		}
	}

}
