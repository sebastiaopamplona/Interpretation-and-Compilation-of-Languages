package icl.interpreter.types;

public class TNone implements IType {
	
	private static TNone instance;
	
	private TNone() {}

	public static TNone getInstance() {
		if (instance == null)
			instance = new TNone();
		
		return instance;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
