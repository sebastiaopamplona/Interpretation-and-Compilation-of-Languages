package icl.interpreter.exceptions;

public class VariableAlreadyDeclaredException extends Exception{

	private static final long serialVersionUID = 7178164880859446758L;

	public VariableAlreadyDeclaredException(String variable){
		super("Variable '" + variable + "' already declared.");
	}
	
}
