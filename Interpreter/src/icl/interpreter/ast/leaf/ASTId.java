package icl.interpreter.ast.leaf;

import icl.interpreter.ast.ASTNode;
import icl.interpreter.ast.Enviornment;
import icl.interpreter.ast.Memory;
import icl.interpreter.ast.Tuple;
import icl.interpreter.exceptions.VariableNotDeclaredException;
import icl.interpreter.jasmin.JasminCode;
import icl.interpreter.types.IType;
import icl.interpreter.values.IValue;
import icl.interpreter.values.IValueXMemory;
import icl.interpreter.values.VBoolean;
import icl.interpreter.values.VClosure;
import icl.interpreter.values.VInt;
import icl.interpreter.values.VReference;

public class ASTId implements ASTNode {

	private String idname;

	//Anotations
	public IType type;
	public int level;
	
	public ASTId(String idname) {
		this.idname = idname;
	}

	@Override
	public IValueXMemory eval(Memory m0, Enviornment<IValue> e) throws Exception {
		IValueXMemory m1 = new IValueXMemory(e.find(idname).getValue(), m0);

		if (m1.getIValue() instanceof VInt)
			return new IValueXMemory(new VInt((Integer) m1.getIValue().getValue()), m1.getMemory());

		else if (m1.getIValue() instanceof VBoolean)
			return new IValueXMemory(new VBoolean((Boolean) m1.getIValue().getValue()), m1.getMemory());

		else if (m1.getIValue() instanceof VClosure) {
			VClosure clo = (VClosure) m1.getIValue().getValue();
			return new IValueXMemory(new VClosure(clo.getParams(), clo.getBody(), clo.getEnv()), m1.getMemory());
		}

		else if (m1.getIValue() instanceof VReference)
			return new IValueXMemory(new VReference((String) m1.getIValue().getValue()), m1.getMemory());

		throw new VariableNotDeclaredException(idname);

	}

	@Override
	public String toString() {
		return this.idname;
	}

	@Override
	public IType typecheck(Enviornment<IType> e) throws VariableNotDeclaredException {
		Tuple<IType> t = e.find(idname);
		level = t.getLevel();
		return type = t.getValue();
	}

	@Override
	public void compile(int envLevel) throws Exception {
		//TODO: ver slide 22 para subir de frame em frame para saber a frame correspondente ao level
		
		JasminCode.getInstance().emit("aload 4\n");
		for(int i = envLevel - 1; i > level; i--)
			JasminCode.getInstance().emit("getfield f" + i + "/sl Lf" + (i-1) + ";\n");
		
		String jasminType = JasminCode.getInstance().getTypeParser().get(type.getClass().getSimpleName());
		
		if(JasminCode.getInstance().isDeleteVariableActive()) {
			JasminCode.getInstance().emit("aconst_null");
			JasminCode.getInstance().emit("putfield f" + level + "/" + idname + " " + jasminType + "\n");
		}
		else
			JasminCode.getInstance().emit("getfield f" + level + "/" + idname + " " + jasminType + "\n");
		
	}

}
