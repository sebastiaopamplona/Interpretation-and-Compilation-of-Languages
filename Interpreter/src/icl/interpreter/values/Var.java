package icl.interpreter.values;

import icl.interpreter.ast.ASTNode;
import icl.interpreter.types.IType;

public class Var {

	private IType type;
	private String id;
	private ASTNode val;

	public Var(IType type, String id, ASTNode val) {
		this.type = type;
		this.id = id;
		this.val = val;
	}

	public IType getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public ASTNode getVal() {
		return val;
	}

}
