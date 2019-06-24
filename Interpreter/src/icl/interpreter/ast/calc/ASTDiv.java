package icl.interpreter.ast.calc;

import icl.interpreter.ast.Memory;
import icl.interpreter.ast.Tuple;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.exceptions.InvalidTypeException;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TInt;
import icl.interpreter.types.TNone;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VInt;

public class ASTDiv implements ASTNode {

	private ASTNode left;
	private ASTNode right;

	public ASTDiv(ASTNode e1, ASTNode e2) {
		left = e1;
		right = e2;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = left.eval(m0, e);

		if (m1.getIValue() instanceof VInt) {
			VInt v1 = (VInt) m1.getIValue();
			IValueXMemory m2 = right.eval(m1.getMemory(), e);

			if (m2.getIValue() instanceof VInt) {
				VInt v2 = (VInt) m2.getIValue();
				return new IValueXMemory(new VInt(v1.getValue() / v2.getValue()), m2.getMemory());
			} else
				throw new InvalidTypeException(m2.getIValue(), "VInt", "/");
		} else
			throw new InvalidTypeException(m1.getIValue(), "VInt", "/");
	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception{
		if (left.typecheck(e) instanceof TInt && right.typecheck(e) instanceof TInt)
			return TInt.getInstance();
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		left.compile(envLevel);
		right.compile(envLevel);
		JasminCode.getInstance().emit("idiv");
	}

}
