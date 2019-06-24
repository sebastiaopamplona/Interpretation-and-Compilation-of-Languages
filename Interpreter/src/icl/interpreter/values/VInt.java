package icl.interpreter.values;

public class VInt implements IValue {

    private Integer num;

    public VInt(Integer num) {
        this.num = num;
    }

    @Override
    public Integer getValue() {
        return num;
    }

    @Override
    public String toString() {
        return "" + num;
    }

}
