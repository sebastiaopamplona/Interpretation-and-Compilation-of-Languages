package icl.interpreter.ast;

import java.util.HashMap;
import java.util.Map;

import icl.interpreter.values.IValue;
import icl.interpreter.values.VReference;

public class Memory {

	private Map<String, IValue> vars;
	private int memCounter;
	
	public Memory() {
		this.vars = new HashMap<>();
		this.memCounter = 0;
	}
	
	public Memory(Memory m) {
		this.vars = m.vars;
		this.memCounter = 0;
	}
	
	public VReference mNew(IValue value) {
		VReference ref = new VReference("loc" + memCounter);
		this.vars.put(ref.getValue(), value);
		memCounter++;
		return ref;
	}
	
	public void mSet(VReference ref, IValue value) {
		this.vars.put(ref.getValue(), value);
	}
	
	public IValue mGet(VReference ref) {
		return this.vars.get(ref.getValue());
	}
	
	public void mFree(VReference ref) {
		this.vars.remove(ref.getValue());
		memCounter--;
	}
	
}
