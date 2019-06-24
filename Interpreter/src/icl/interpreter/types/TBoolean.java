package icl.interpreter.types;

public class TBoolean implements IType {

	private static TBoolean instance;
	
	private TBoolean() {}

	public static TBoolean getInstance() {
		if (instance == null)
			instance = new TBoolean();
		
		return instance;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
