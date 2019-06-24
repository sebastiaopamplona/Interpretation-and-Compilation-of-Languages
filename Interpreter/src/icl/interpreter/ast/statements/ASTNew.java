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
import icl.interpreter.values.VReference;

public class ASTNew implements ASTNode {

	private IType iType;
	private ASTNode exp;

	public ASTNew(ASTNode exp) {
		this.exp = exp;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = this.exp.eval(m0, e);
		VReference ref = m1.getMemory().mNew(m1.getIValue());
		Memory m2 = new Memory(m1.getMemory());
		return new IValueXMemory(ref, m2);
	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		IType type = exp.typecheck(e);
		if(!(type instanceof TNone))
			return iType = new TReference(type);
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		if(exp instanceof ASTNew) {
			JasminCode.getInstance().emit("\n;ASTNew with New");
			JasminCode.getInstance().emit("new ref_class");
			JasminCode.getInstance().emit("dup");
			JasminCode.getInstance().emit("invokespecial ref_class/<init>()V");
			JasminCode.getInstance().emit("dup");
			exp.compile(envLevel);
			JasminCode.getInstance().emit("putfield ref_class/value Ljava/lang/Object;");
		}
		else {
			JasminCode.getInstance().emit("\n;ASTNew with Int/Boolean");
			JasminCode.getInstance().emit("new ref_int");
			JasminCode.getInstance().emit("dup");
			JasminCode.getInstance().emit("invokespecial ref_int/<init>()V");
			JasminCode.getInstance().emit("dup");
			exp.compile(envLevel);
			JasminCode.getInstance().emit("putfield ref_int/value I");
		}
	}

}
