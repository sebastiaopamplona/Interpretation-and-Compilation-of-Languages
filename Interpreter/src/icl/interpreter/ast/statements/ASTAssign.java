package icl.interpreter.ast.statements;

import icl.interpreter.ast.Memory;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.exceptions.InvalidTypeException;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TNone;
import icl.interpreter.types.TReference;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VReference;

public class ASTAssign implements ASTNode {

	private ASTNode left;
	private ASTNode right;

	public ASTAssign(ASTNode left, ASTNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = left.eval(m0, e); // (v1, m1)

		if (m1.getIValue() instanceof VReference) {
			IValueXMemory m2 = right.eval(m1.getMemory(), e); // (v2, m2)
			m2.getMemory().mSet((VReference) m1.getIValue(), m2.getIValue()); // m3

			return new IValueXMemory(m2.getIValue(), m2.getMemory());
		} else
			throw new InvalidTypeException(m1.getIValue(), "VReference", ":=");

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		if (left.typecheck(e) instanceof TReference) {
			IType valueType = right.typecheck(e);
			if(!(valueType instanceof TNone))
				return valueType;
			else
				return TNone.getInstance();
		} else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		JasminCode.getInstance().emit(";ASTAssign");
		left.compile(envLevel);
		JasminCode.getInstance().emit("checkcast ref_int");
		right.compile(envLevel);
		JasminCode.getInstance().emit("putfield ref_int/value I");
		
		if(!JasminCode.getInstance().isWhileActive()) {
			left.compile(envLevel);
			JasminCode.getInstance().emit("checkcast ref_int");
			JasminCode.getInstance().emit("getfield ref_int/value I");
		}
	}

}
