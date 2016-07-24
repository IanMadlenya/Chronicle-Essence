package net.openhft.chronicle.essence.api;

import java.util.NavigableSet;
import java.util.function.Function;

/**
 * Created by peter on 03/07/16.
 */
public interface Registry {
    /**
     * Bind an actor to a name
     *
     * @param fullname         using the full name which may include / as a branch.
     * @param remote           actor to provide the service
     * @param exportInterfaces interfaces to expose, if none are provide, expose all public methods.
     */
    <R> void put(String fullname, R remote, Class... exportInterfaces);

    /**
     * Return a proxy to the actor which implements the interface provided, creating one as required.
     *
     * @param fullname of the actor
     * @param rType    the actor should implement.
     * @return a proxy to an actor of this type.
     * @throws IllegalStateException if the actor could not be created
     */
    <R> R acquire(String fullname, Class<R> rType) throws IllegalStateException;

    /**
     * Return a proxy to the actor which implements the interface provided
     *
     * @param fullname of the actor
     * @param rType    the actor should implement.
     * @return a proxy to an actor of this type or null if it doesn't exist.
     */
    <R> R getOrNull(String fullname, Class<R> rType);

    /**
     * Finds a actor give a relative location. If not present it will look at the parent and it's parent etc.
     *
     * @param fullname of the actor
     * @throws IllegalStateException if the actor could not be found or created
     */
    <R> R find(String fullname, Class<R> rType);

    /**
     * Removes an actor by name.
     *
     * @param fullname of the actor
     */
    void remove(String fullname);

    <R> ResourceContext<R> usingService(String fullname, Class<R> rType);

    default <R, T> T visit(String fullname, Class<R> rType, Function<R, T> function) {
        try (ResourceContext<R> context = usingService(fullname, rType)) {
            return function.apply(context.resource());
        }
    }

    /**
     * Obtain a set of all available names.
     */
    NavigableSet<String> allNames();

    void registerListener(RegistryListener listener);
}
