public class ArrayDescriptor extends Descriptor {
    public int size;
    public String name;

    public ArrayDescriptor(String name, Type type, boolean isLocal) {
        super(name, type, isLocal);
    }

}
