package icl.interpreter.values;
import java.util.List;

import icl.interpreter.ast.Enviornment;
import icl.interpreter.ast.ASTNode;

public class VClosure implements IValue {

	private List<String> params;
	private ASTNode body;
	private Enviornment<IValue> env;

	public VClosure(List<String> params, ASTNode body, Enviornment<IValue> env) {
		this.params = params;
		this.body = body;
		this.env = env;
	}

	@Override
	public VClosure getValue() {
		return this;
	}

	public List<String> getParams() {
		return params;
	}

	public ASTNode getBody() {
		return body;
	}

	public Enviornment<IValue> getEnv() {
		return env;
	}

	
	
}
