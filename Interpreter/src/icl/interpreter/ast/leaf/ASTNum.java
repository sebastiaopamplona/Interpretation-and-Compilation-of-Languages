package icl.interpreter.ast.leaf;

import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.ast.Memory;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TInt;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VInt;

public class ASTNum implements ASTNode {

	private int num;
	
	//Anotaions
	public IType type;

	public ASTNum(int num) {
		this.num = num;
	}

	@Override
	public IValueXMemory eval(Memory m, Enviornment<IValue> e) {
		return new IValueXMemory(new VInt(num), m);
	}

	@Override
	public IType typecheck(Enviornment<IType> e) {
		return type = TInt.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		JasminCode.getInstance().emit("sipush " + num);
	}

	public int getNum() {
		return num;
	}
	
}
