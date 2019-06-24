package icl.interpreter.ast.statements;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import icl.interpreter.ast.Memory;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.types.IType;
import icl.interpreter.types.TFunction;
import icl.interpreter.types.TNone;
import icl.interpreter.values.*;

public class ASTLet implements ASTNode {

	private List<Var> vars;
	private ASTNode right;
	
	//Anotation
	public int level;

	public ASTLet(List<Var> vars, ASTNode e2) {
		this.vars = vars;
		right = e2;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		Enviornment<IValue> e2 = e.beginScope();

		Iterator<Var> iter = this.vars.iterator();
		Var curr = iter.next();
		IValueXMemory m1 = curr.getVal().eval(m0, e);
		e2.assoc(curr.getId(), m1.getIValue());

		while (iter.hasNext()) {
			curr = iter.next();
			m1 = curr.getVal().eval(m0, e);
			e2.assoc(curr.getId(), m1.getIValue());
		}

		IValue v2 = right.eval(m1.getMemory(), e2).getIValue();
		e2.endScope();

		if (v2 instanceof VInt)
			return new IValueXMemory(new VInt((Integer) v2.getValue()), m1.getMemory());

		else if (v2 instanceof VBoolean)
			return new IValueXMemory(new VBoolean((Boolean) v2.getValue()), m1.getMemory());

		else if (v2 instanceof VClosure) {
			VClosure clo = (VClosure) v2.getValue();
			return new IValueXMemory(new VClosure(clo.getParams(), clo.getBody(), clo.getEnv()), m1.getMemory());
		}

		else if (v2 instanceof VReference)
			return new IValueXMemory(new VReference((String) v2.getValue()), m1.getMemory());

		// TODO: take care of exception
		throw new Exception("TODO...");
	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		Enviornment<IType> e2 = e.beginScope();
		level = e2.getLevel();
		Iterator<Var> iter = this.vars.iterator();
		while (iter.hasNext()) {
			Var curr = iter.next();
			if(curr.getType() instanceof TFunction) {
				List<IType> argTypes = ((TFunction) curr.getType()).getArgTypes();
				List<Parameter> params = ((ASTFunction) curr.getVal()).getParameters();
				
				if(argTypes.size() == params.size()) {
					Iterator<IType> argsIter = argTypes.iterator();
					Iterator<Parameter> paramsIter = params.iterator();
					while(argsIter.hasNext()) {
						IType arg = argsIter.next();
						Parameter param = paramsIter.next();
						if(!param.getType().toString().equals(arg.toString()))
							return TNone.getInstance();
						else
							e2.assoc(param.getId(), param.getType());
					}
				} else
					return TNone.getInstance();
				
				if(((TFunction) curr.getType()).getReturnType() != ((ASTFunction) curr.getVal()).getBody().typecheck(e2))
					return TNone.getInstance(); 
				
			}
			System.out.println("Level [" + e2.getLevel() + "]: " + curr.getId());
			e2.assoc(curr.getId(), curr.getType());
		}

		IType bodyType = right.typecheck(e2);
		e2.endScope();

		return bodyType;
	}
	
	/**
	 * Creates a frame, and for every variable, compiles it and stores it in the frame
	 */
	@Override
	public void compile(int envLevel) throws Exception {
		JasminCode jc = JasminCode.getInstance();
		
		//# create frame file and object, and update sl
		jc.createFrame(envLevel, vars);
		//# create frame file and object, and update sl		
		
		
		
		Iterator<Var> iter = vars.iterator();
		while(iter.hasNext()) {
			jc.emit("\taload 4\n");
			Var curr = iter.next();
			curr.getVal().compile(envLevel);
			String iType = curr.getType().getClass().getSimpleName();
			jc.emit("putfield f" + envLevel + "/" + curr.getId() + " " + JasminCode.getInstance().getTypeParser().get(iType) + "\n");
		}
		
		right.compile(envLevel + 1);
		
	}

}
