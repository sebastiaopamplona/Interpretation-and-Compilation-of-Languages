package icl.interpreter.exceptions;

import icl.interpreter.values.IValue;

public class InvalidTypeException extends Exception{

	private static final long serialVersionUID = 7497692766727794248L;

	public InvalidTypeException(IValue value, String supposed){
		super("Cannot cast from " + value.getClass().getSimpleName() + " to " + supposed + ".");
	}
	
	public InvalidTypeException(IValue value, String supposed, String op){
		super("Cannot cast from " + value.getClass().getSimpleName() + " to " + supposed + ". (operation: " + op + ")");
	}
	
	public InvalidTypeException(String actual, String supposed, String op){
		super("Cannot cast from " + actual + " to " + supposed + ". (operation: " + op + ")");
	}
	
}
