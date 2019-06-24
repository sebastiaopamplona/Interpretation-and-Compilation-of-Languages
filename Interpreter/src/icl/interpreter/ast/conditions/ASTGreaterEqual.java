package icl.interpreter.ast.conditions;

import icl.interpreter.ast.Memory;
import icl.interpreter.ast.Tuple;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.exceptions.*;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TBoolean;
import icl.interpreter.types.TInt;
import icl.interpreter.types.TNone;
import icl.interpreter.values.*;

public class ASTGreaterEqual implements ASTNode {

	private ASTNode left;
	private ASTNode right;

	public ASTGreaterEqual(ASTNode left, ASTNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = left.eval(m0, e);

		if (m1.getIValue() instanceof VInt) {
			VInt v1 = (VInt) m1.getIValue();
			IValueXMemory m2 = right.eval(m1.getMemory(), e);

			if (m2.getIValue() instanceof VInt) {
				VInt v2 = (VInt) m2.getIValue();

				if (v1.getValue() >= v2.getValue())
					return new IValueXMemory(new VBoolean(true), m2.getMemory());
				else
					return new IValueXMemory(new VBoolean(false), m2.getMemory());

			} else
				throw new InvalidTypeException(m2.getIValue(), "VInt", ">=");
		} else
			throw new InvalidTypeException(m1.getIValue(), "VInt", ">=");

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		IType leftType = left.typecheck(e);
		if(leftType.getClass() == right.typecheck(e).getClass() && leftType instanceof TInt)
			return TBoolean.getInstance();
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		left.compile(envLevel);
		right.compile(envLevel);
		int conditionalCount = JasminCode.getInstance().conditionalCount();
		JasminCode.getInstance().emit("isub\n" 
									+ "\tifge L" + conditionalCount + "\n"
									+ "\tsipush 0\n"
									+ "\tgoto L" + (conditionalCount+1) + "\n"
									+ "\tL" + conditionalCount + ":\n"
									+ "\tsipush 1\n"
									+ "\tL" + (conditionalCount+1) + ":\n");
	}
}
