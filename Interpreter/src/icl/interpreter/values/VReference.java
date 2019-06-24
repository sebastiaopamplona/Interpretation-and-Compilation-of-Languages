package icl.interpreter.values;

public class VReference implements IValue {

	private String ref;

	public VReference(String ref) {
		this.ref = ref;
	}

	@Override
	public String getValue() {
		return ref;
	}

	@Override
	public String toString() {
		return ref;
	}
}
