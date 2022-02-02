public abstract class Descriptor {
    public String name;
    public Type type;
    public boolean isLocal;

    public Descriptor(String name, Type type, boolean isLocal) {
        this.name = name;
        this.type = type;
        this.isLocal = isLocal;
    }

}
