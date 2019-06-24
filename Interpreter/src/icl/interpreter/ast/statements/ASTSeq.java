package icl.interpreter.ast.statements;

import icl.interpreter.ast.Memory;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.types.IType;
import icl.interpreter.types.TNone;
import icl.interpreter.types.TReference;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;

public class ASTSeq implements ASTNode {

	private ASTNode left;
	private ASTNode right;

	public ASTSeq(ASTNode left, ASTNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = left.eval(m0, e);
		IValueXMemory m2 = right.eval(m1.getMemory(), e);
		return new IValueXMemory(m2.getIValue(), m2.getMemory());
	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		if(!(left.typecheck(e) instanceof TNone)) {
			IType type = right.typecheck(e);
			if(!(type instanceof TNone))
				return type;
			else
				return TNone.getInstance();
		} else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		left.compile(envLevel);
		JasminCode.getInstance().emit("pop\n");
		right.compile(envLevel);
	}

}
