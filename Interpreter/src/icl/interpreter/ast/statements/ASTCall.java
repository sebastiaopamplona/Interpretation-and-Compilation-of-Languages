package icl.interpreter.ast.statements;

import java.util.Iterator;
import java.util.List;

import icl.interpreter.ast.Memory;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.exceptions.InvalidFunctionNameException;
import icl.interpreter.types.IType;
import icl.interpreter.types.TFunction;
import icl.interpreter.types.TNone;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VClosure;

public class ASTCall implements ASTNode {

	private ASTNode func;
	private List<ASTNode> args;

	//Anotations
	public IType type;
	public int level;
	
	public ASTCall(ASTNode func, List<ASTNode> args) {
		this.func = func;
		this.args = args;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = func.eval(m0, e);

		if (m1.getIValue() instanceof VClosure) {
			VClosure function = (VClosure) m1.getIValue();
			Enviornment<IValue> e2 = e.beginScope();
			
			//TODO: check size of params VS args
			
			Iterator<String> iterParams = function.getParams().iterator();
			Iterator<ASTNode> iterArgs = this.args.iterator();
			
			while(iterParams.hasNext())
				e2.assoc(iterParams.next(), iterArgs.next().eval(m1.getMemory(), e).getIValue());
			
			IValue val = function.getBody().eval(m1.getMemory(), e2).getIValue();
			e2.endScope();

			return new IValueXMemory(val, m1.getMemory());
		} else
			throw new InvalidFunctionNameException(func.toString());

	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		level = e.getLevel();
		IType type = func.typecheck(e);
		if(type instanceof TFunction) {
			return type = ((TFunction) type).getReturnType();
		} else
			return type = TNone.getInstance();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
