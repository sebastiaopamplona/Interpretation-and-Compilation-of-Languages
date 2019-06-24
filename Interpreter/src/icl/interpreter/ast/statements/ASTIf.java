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

public class ASTIf implements ASTNode {
	private ASTNode conditionExp;
	private ASTNode thenExp;
	private ASTNode elseExp;

	public ASTIf(ASTNode conditionExp, ASTNode thenExp, ASTNode elseExp) {
		this.conditionExp = conditionExp;
		this.thenExp = thenExp;
		this.elseExp = elseExp;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = conditionExp.eval(m0, e);

		if (m1.getIValue() instanceof VBoolean) {
			VBoolean bool = (VBoolean) m1.getIValue();
			if (bool.getValue())
				return new IValueXMemory(thenExp.eval(m1.getMemory(), e).getIValue(), m1.getMemory());
			else
				return new IValueXMemory(elseExp.eval(m1.getMemory(), e).getIValue(), m1.getMemory());

		} else
			throw new InvalidTypeException(m1.getIValue(), "VBoolean");

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		if (conditionExp.typecheck(e) instanceof TBoolean) {
			IType thenType = thenExp.typecheck(e);
			if(thenType.getClass() == elseExp.typecheck(e).getClass())
				return thenType;
			else
				return TNone.getInstance();
		} else 
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		JasminCode.getInstance().emit(";If");
		conditionExp.compile(envLevel);
		int conditionalCount = JasminCode.getInstance().conditionalCount();
		JasminCode.getInstance().emit("ifeq L" + conditionalCount + "\n");;
		// true
		thenExp.compile(envLevel);
		JasminCode.getInstance().emit("\tgoto L" + (conditionalCount+1) + "\n"
									+ "\tL" + conditionalCount + ":\n");
		elseExp.compile(envLevel);
		JasminCode.getInstance().emit("\tL" + (conditionalCount+1) + ":\n");
	}

}
