package icl.interpreter.ast.leaf;

import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.ast.Memory;
import icl.interpreter.ast.Tuple;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TBoolean;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VBoolean;

public class ASTBool implements ASTNode{

	private Boolean bool;
	
	public ASTBool(String bool) {
		if(bool.equals("true"))
			this.bool = new Boolean(true);
		else 
			this.bool = new Boolean(false);
	}
	
	@Override
	public IValueXMemory eval(Memory m, Enviornment<IValue> e) {
		return new IValueXMemory(new VBoolean(bool), m);
	}

	@Override
	public IType typecheck(Enviornment<IType> e) {
		return TBoolean.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		if(bool)
			JasminCode.getInstance().emit("sipush 1");
		else
			JasminCode.getInstance().emit("sipush 0");
	}

}
