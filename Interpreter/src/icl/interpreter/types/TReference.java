package icl.interpreter.types;

public class TReference implements IType {

	private IType refType;

	public TReference(IType refType) {
		this.refType = refType;
	}

	public IType getRefType() {
		return refType;
	}

	@Override
	public String toString() {
		return "Reference to " + refType.toString();
	}
	
}
