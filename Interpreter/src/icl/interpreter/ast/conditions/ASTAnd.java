package icl.interpreter.ast.conditions;

import icl.interpreter.ast.Memory;
import icl.interpreter.ast.Tuple;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.exceptions.InvalidTypeException;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TBoolean;
import icl.interpreter.types.TNone;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VBoolean;

public class ASTAnd implements ASTNode {

	private ASTNode left;
	private ASTNode right;

	public ASTAnd(ASTNode left, ASTNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory v1 = left.eval(m0, e);

		if (v1.getIValue() instanceof VBoolean) {
			IValueXMemory v2 = right.eval(v1.getMemory(), e);
			if (v2.getIValue() instanceof VBoolean) {
				if (((VBoolean) v1.getIValue()).getValue() && ((VBoolean) v2.getIValue()).getValue())
					return new IValueXMemory(new VBoolean(true), v2.getMemory());
				else
					return new IValueXMemory(new VBoolean(false), v2.getMemory());
			} else
				throw new InvalidTypeException(v2.getIValue(), "VBoolean");
		} else
			throw new InvalidTypeException(v1.getIValue(), "VBoolean");

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		if(left.typecheck(e) instanceof TBoolean && right.typecheck(e) instanceof TBoolean )
			return TBoolean.getInstance();
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		left.compile(envLevel);
		right.compile(envLevel);
		JasminCode.getInstance().emit("iand");
	}

}
