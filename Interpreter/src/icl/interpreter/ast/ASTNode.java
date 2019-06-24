package icl.interpreter.ast;

import icl.interpreter.types.IType;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;

public interface ASTNode {

	IValueXMemory eval(Memory m, Enviornment<IValue> e) throws Exception;

	IType typecheck(Enviornment<IType> e) throws Exception;
	
	void compile(int envLevel) throws Exception;
}