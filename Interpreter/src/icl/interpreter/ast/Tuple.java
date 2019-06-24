package icl.interpreter.ast;

public class Tuple<T> {
	private T value;
	private int level;
	
	public Tuple(T value, int level) {
		this.value = value;
		this.level = level;
	}

	public T getValue() {
		return value;
	}

	public int getLevel() {
		return level;
	}
	
}
