package icl.interpreter.values;

import icl.interpreter.types.IType;

public class Parameter {

	private String id;
	private IType type;

	public Parameter(String id, IType type) {
		this.id = id;
		this.type = type;
	}

	public IType getType() {
		return type;
	}

	public String getId() {
		return id;
	}

}
