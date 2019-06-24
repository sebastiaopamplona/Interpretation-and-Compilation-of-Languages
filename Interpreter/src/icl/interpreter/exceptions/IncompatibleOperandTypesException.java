package icl.interpreter.exceptions;

import icl.interpreter.values.IValue;

public class IncompatibleOperandTypesException extends Exception{

	private static final long serialVersionUID = -4723320663076052067L;

	public IncompatibleOperandTypesException(IValue v1, IValue v2){
		super("Incompatible types '" + v1.getClass().getSimpleName() 
				+ "' and '" + v2.getClass().getSimpleName() + "'.");
	}
	
}
