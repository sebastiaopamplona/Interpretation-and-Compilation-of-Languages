package icl.interpreter.ast.statements;

import icl.interpreter.ast.Memory;
import icl.interpreter.ast.leaf.ASTId;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.exceptions.InvalidTypeException;
import icl.interpreter.exceptions.NullPointerException;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.types.TNone;
import icl.interpreter.types.TReference;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VReference;

public class ASTDeref implements ASTNode {

	private IType iType;
	private ASTNode exp;

	public ASTDeref(ASTNode exp) {
		this.exp = exp;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = this.exp.eval(m0, e); 

		if (m1.getIValue() instanceof VReference) {
			VReference reference = (VReference) m1.getIValue();

			if (m1.getMemory().mGet(reference) == null)
				throw new NullPointerException(reference);

			return new IValueXMemory(m1.getMemory().mGet(reference), m1.getMemory());
		} else
			throw new InvalidTypeException(m1.getIValue(), "VReference", "!");

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		IType type = exp.typecheck(e);
		if(type instanceof TReference) {
			iType = ((TReference) type).getRefType();
			return iType;
		}
		else
			return TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		JasminCode.getInstance().emit(";ASTDeref");
		if(exp instanceof ASTDeref) {
			// reverse emit to guarantee that recursion doesn't emit 
			// the "getfield ref_int/value I" first
			JasminCode.getInstance().reverseEmit("checkcast ref_class");
			JasminCode.getInstance().reverseEmit("getfield ref_class/value Ljava/lang/Object;");
			exp.compile(envLevel);
		}
		else {
			exp.compile(envLevel);
			JasminCode.getInstance().emit(JasminCode.getInstance().getReverOutput());
			JasminCode.getInstance().emit("checkcast ref_int");
			JasminCode.getInstance().emit("getfield ref_int/value I");
		}
		
	}

}
