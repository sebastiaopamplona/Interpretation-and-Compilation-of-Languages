package icl.interpreter.ast.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import icl.interpreter.ast.Memory;
import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.types.IType;
import icl.interpreter.types.TFunction;
import icl.interpreter.types.TNone;
import icl.interpreter.types.TReference;
import icl.interpreter.values.*;

public class ASTFunction implements ASTNode {

	private List<Parameter> parameters;
	private ASTNode body;

	//Anotations
	public IType type;
	public int level;
	
	public ASTFunction(List<Parameter> args, ASTNode body) {
		this.parameters = args;
		this.body = body;
	}

	@Override
	public IValueXMemory eval(Memory m, Enviornment<IValue> e) {
		List<String> params = new ArrayList<>(this.parameters.size());
		Iterator<Parameter> iter = this.parameters.iterator();
		while(iter.hasNext())
			params.add(iter.next().getId());
		
		return new IValueXMemory(new VClosure(params, this.body, e), m);
	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws Exception {
		level = e.getLevel();
		IType bodyType = body.typecheck(e);
		if(!(bodyType instanceof TNone)) {
			List<IType> paramTypes = new ArrayList<>(this.parameters.size());
			Iterator<Parameter> iter = this.parameters.iterator();
			while(iter.hasNext())
				paramTypes.add(iter.next().getType());
			
			return type = new TFunction(paramTypes, bodyType);
		}
			
		else
			return type = TNone.getInstance();
	}
	
	public List<Parameter> getParameters(){
		return parameters;
	}
	
	public ASTNode getBody() {
		return body;
	}

	@Override
	public void compile(int envLevel) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
