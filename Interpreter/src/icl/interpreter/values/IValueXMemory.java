package icl.interpreter.values;

import icl.interpreter.ast.Memory;

public class IValueXMemory {

	private IValue ivalue;
	private Memory memory;

	public IValueXMemory(IValue ivalue, Memory memory) {
		super();
		this.ivalue = ivalue;
		this.memory = memory;
	}

	public IValue getIValue() {
		return ivalue;
	}

	public Memory getMemory() {
		return memory;
	}

	@Override
	public String toString() {
		return this.ivalue.toString();
	}
	
	
}
