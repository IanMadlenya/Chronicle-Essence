package net.openhft.chronicle.essence.api;

/**
 * Created by peter on 03/07/16.
 */
@FunctionalInterface
interface RegistryListener {
    <R> void added(String fullname, R remote, Class<R> rType) throws IllegalStateException;

    default <R> void removed(String fullname, R remote, Class<R> rType) throws IllegalStateException {
    }
}
