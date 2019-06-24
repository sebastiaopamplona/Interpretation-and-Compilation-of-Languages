package icl.interpreter.ast;

import java.util.HashMap;
import java.util.Map;

import icl.interpreter.exceptions.VariableAlreadyDeclaredException;
import icl.interpreter.exceptions.VariableNotDeclaredException;

public class Enviornment<T> {

	private Enviornment<T> parent;
	private Map<String, T> vars;
	private int level;
	//TODO: pensar em guardar o frame_id correspondente ao level
	
	public Enviornment() {
		vars = new HashMap<>();
		level = 0;
	}

	public Enviornment(Enviornment<T> parent, int level) {
		this.parent = parent;
		//vars = parent.vars;
		vars = new HashMap<>();
		this.level = level;
	}

	public Enviornment<T> beginScope() {
		//if(level > 0)
			//merge();
		return new Enviornment<T>(this, level+1);
	}

	public Enviornment<T> endScope() {
		return this.parent;
	}

	public void assoc(String idname, T value) throws Exception {
		if(vars.containsKey(idname))
			throw new VariableAlreadyDeclaredException(idname);

		vars.put(idname, value);
	}

	public Tuple<T> find(String idname) throws VariableNotDeclaredException {
		//TODO: pensar em gerar o codigo jasmin para ir buscar o valor a frame
		//TODO: ver slide 22 para subir de frame em frame para saber a frame correspondente ao level
		if(vars.containsKey(idname))
			return new Tuple<T>(vars.get(idname), level);
		
		Enviornment<T> p = parent;
		while(p != null) {
			if(p.getVars().containsKey(idname))
				return new Tuple<T>(p.getVars().get(idname), p.level);
			else
				p = p.parent;	
		}
			throw new VariableNotDeclaredException(idname);
	}

	public Map<String, T> getVars() {
		return vars;
	}

	public void merge() {
		Map<String, T> parents = parent.getVars();
		parents.putAll(vars);
		vars = parents;
	}
	
	public int getLevel() {
		return level;
	}
	
}
