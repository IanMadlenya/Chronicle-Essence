package net.openhft.chronicle.essence.api;

/**
 * Created by peter on 24/07/16.
 */
public class VanillaResourceContext<R> implements ResourceContext<R> {
    private final String fullname;
    private final Class<R> type;
    private final R resource;

    public VanillaResourceContext(String fullname, Class<R> type, R resource) {
        this.fullname = fullname;
        this.type = type;
        this.resource = resource;
    }

    @Override
    public void close() {

    }

    @Override
    public String fullname() {
        return fullname;
    }

    @Override
    public Class<R> type() {
        return type;
    }

    @Override
    public R resource() {
        return resource;
    }
}
