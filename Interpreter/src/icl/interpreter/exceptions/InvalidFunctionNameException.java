package icl.interpreter.exceptions;

public class InvalidFunctionNameException extends Exception{

	private static final long serialVersionUID = 6210421868348791577L;

	public InvalidFunctionNameException(String function){
		super("'" + function + "' is not a function.");
	}
	
}
