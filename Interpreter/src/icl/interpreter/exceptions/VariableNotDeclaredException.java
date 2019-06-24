package icl.interpreter.exceptions;

public class VariableNotDeclaredException extends Exception{

	private static final long serialVersionUID = 6886669326485897042L;

	public VariableNotDeclaredException(String idname){
		super("Variable '" + idname + "' not declared.");
	}
	
}
