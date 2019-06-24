package icl.interpreter.exceptions;

import icl.interpreter.values.VReference;

public class NullPointerException extends Exception {

	private static final long serialVersionUID = 6941712314340212375L;

	public NullPointerException(VReference reference) {
		super("The memory location '" + reference.getValue() + "' was freed previously.");
	}

}
