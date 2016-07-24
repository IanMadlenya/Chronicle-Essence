package net.openhft.chronicle.essence.api;

import java.io.Closeable;

/**
 * Created by peter on 24/07/16.
 */
public interface ResourceContext<R> extends Closeable {
    void close();

    String fullname();

    Class<R> type();

    R resource();
}
