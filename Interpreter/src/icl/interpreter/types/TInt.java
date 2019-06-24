package icl.interpreter.types;

public class TInt implements IType {
	
	private static TInt instance;
	
	private TInt() {}

	public static TInt getInstance() {
		if (instance == null)
			instance = new TInt();
		
		return instance;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
