package icl.interpreter.ast.conditions;


import icl.interpreter.ast.Memory;
import icl.interpreter.ast.Tuple;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.exceptions.IncompatibleOperandTypesException;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TBoolean;
import icl.interpreter.types.TNone;
import icl.interpreter.values.*;

public class ASTEquals implements ASTNode {

	private ASTNode left;
	private ASTNode right;

	public ASTEquals(ASTNode left, ASTNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = left.eval(m0, e);
		IValueXMemory m2 = right.eval(m1.getMemory(), e);

		if (m1.getIValue().getClass() == m2.getIValue().getClass()) {
			if (m1.getIValue().getValue() == m2.getIValue().getValue())
				return new IValueXMemory(new VBoolean(true), m2.getMemory());
			else
				return new IValueXMemory(new VBoolean(false), m2.getMemory());
		} else
			throw new IncompatibleOperandTypesException(m1.getIValue(), m2.getIValue());

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		IType leftType = left.typecheck(e);
		if(leftType.getClass() == right.typecheck(e).getClass() && !(leftType instanceof TNone))
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
									+ "\tifeq L" + conditionalCount + "\n"
									+ "\tsipush 0\n"
									+ "\tgoto L" + (conditionalCount+1) + "\n"
									+ "\tL" + conditionalCount + ":\n"
									+ "\tsipush 1\n"
									+ "\tL" + (conditionalCount+1) + ":\n");
	}

}
