package icl.interpreter.values;

public class VBoolean implements IValue {

	private Boolean bool;

	public VBoolean(Boolean value) {
		this.bool = value;
	}

	@Override
	public Boolean getValue() {
		return bool;
	}

	@Override
	public String toString() {
		return bool.toString();
	}

}
