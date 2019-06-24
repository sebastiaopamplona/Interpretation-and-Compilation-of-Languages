package icl.interpreter.ast.statements;

import icl.interpreter.ast.Memory;
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

public class ASTWhile implements ASTNode {

	private ASTNode condition;
	private ASTNode exp;

	public ASTWhile(ASTNode condition, ASTNode exp) {
		this.condition = condition;
		this.exp = exp;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = condition.eval(m0, e);

		if (m1.getIValue() instanceof VBoolean) {
			VBoolean bool = (VBoolean) m1.getIValue();
			if (bool.getValue()) {
				IValueXMemory m2 = exp.eval(m1.getMemory(), e);
				ASTWhile recursiveWhile = new ASTWhile(condition, exp);
				m1 = recursiveWhile.eval(m2.getMemory(), e);
			}
		} else
			throw new InvalidTypeException(m1.getIValue(), "VBoolean");

		return new IValueXMemory(new VBoolean(false), m1.getMemory());

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		if(condition.typecheck(e) instanceof TBoolean && !(exp.typecheck(e) instanceof TNone))
			return TBoolean.getInstance();
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		JasminCode.getInstance().emit(";While");
		JasminCode.getInstance().toggleWhile();
		int conditionalCount = JasminCode.getInstance().conditionalCount();
		JasminCode.getInstance().emit("WHILE" + conditionalCount + ":\n");
		condition.compile(envLevel);
		JasminCode.getInstance().emit("ifeq WHILE" + (conditionalCount+1) + "\n");;
		// true
		exp.compile(envLevel);
		JasminCode.getInstance().emit("goto "
									+ "WHILE" + conditionalCount + "\n");
		JasminCode.getInstance().emit("WHILE" + (conditionalCount+1) + ":\n");
		//return false
		JasminCode.getInstance().emit("sipush 0");
		JasminCode.getInstance().toggleWhile();
	}

}
