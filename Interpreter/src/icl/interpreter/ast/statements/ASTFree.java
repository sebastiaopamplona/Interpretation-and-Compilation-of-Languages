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
import icl.interpreter.values.VBoolean;
import icl.interpreter.values.VReference;

public class ASTFree implements ASTNode {

	private ASTNode exp;

	public ASTFree(ASTNode exp) {
		this.exp = exp;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = this.exp.eval(m0, e);

		if (m1.getIValue() instanceof VReference) {
			m1.getMemory().mFree((VReference) m1.getIValue());

			return new IValueXMemory(new VBoolean(true), m1.getMemory());
		} else
			throw new InvalidTypeException(m1.getIValue(), "VReference");
	}

	//TODO: confirmar se o free deve ser do tipo TReference ou TNone
	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		IType type = exp.typecheck(e);
		if(type instanceof TReference)
			return type;
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		JasminCode.getInstance().emit(";ASTFree");
		JasminCode.getInstance().toggleDeleteVariable();
		exp.compile(envLevel);
		JasminCode.getInstance().toggleDeleteVariable();
		JasminCode.getInstance().emit("sipush 1");
	}

}
