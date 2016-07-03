package net.openhft.chronicle.essence.api;

/**
 * Created by peter on 03/07/16.
 */
@FunctionalInterface
public interface InstanceFactory {
    <R> R create(String fullname, Class<R> rType);
}
