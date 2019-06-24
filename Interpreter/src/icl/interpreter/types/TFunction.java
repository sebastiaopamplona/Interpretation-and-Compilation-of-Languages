package icl.interpreter.types;

import java.util.Iterator;
import java.util.List;

import icl.interpreter.values.Parameter;

public class TFunction implements IType {

	private List<IType> argTypes;
	private IType returnType;

	public TFunction(List<IType> argTypes, IType returnType) {
		this.argTypes = argTypes;
		this.returnType = returnType;
	}

	public List<IType> getArgTypes() {
		return argTypes;
	}

	public IType getReturnType() {
		return returnType;
	}

	@Override
	public String toString() {
		String tostring = "TFunction(";
		Iterator<IType> iter = this.argTypes.iterator();
		while(iter.hasNext()) {
			tostring += iter.next().toString();
			if(iter.hasNext())
				tostring += ", ";
		}
		tostring += ") " + returnType.toString();
		
		return tostring;
	}

}
