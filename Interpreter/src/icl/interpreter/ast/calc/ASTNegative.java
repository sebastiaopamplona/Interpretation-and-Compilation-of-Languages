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

public class ASTNegative implements ASTNode {

	private ASTNode exp;

	public ASTNegative(ASTNode exp) {
		this.exp = exp;
	}

	@Override
	public IValueXMemory eval(Memory m, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = exp.eval(m, e);

		if (m1.getIValue() instanceof VInt) {
			return new IValueXMemory(new VInt(-((VInt) m1.getIValue()).getValue()), m1.getMemory());
		} else
			throw new InvalidTypeException(m1.getIValue(), "VInt");

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		if(exp.typecheck(e) instanceof TInt)
			return TInt.getInstance();
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		exp.compile(envLevel);
		JasminCode.getInstance().emit("ineg");
	}

}
