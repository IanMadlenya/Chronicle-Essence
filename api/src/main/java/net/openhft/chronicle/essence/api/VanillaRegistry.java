package net.openhft.chronicle.essence.api;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by peter on 03/07/16.
 */
public class VanillaRegistry implements Registry {
    final NavigableMap<String, Map<Class, Object>> map = new TreeMap<>();
    final Set<RegistryListener> listeners = new CopyOnWriteArraySet<>();
    final InstanceFactory instanceFactory;

    public VanillaRegistry() {
        this(new InstanceFactory() {
            @Override
            public <R> R create(String s, Class<R> c) {
                return null;
            }
        });
    }

    public VanillaRegistry(InstanceFactory instanceFactory) {
        this.instanceFactory = instanceFactory;
    }

    @Override
    public <R> void put(String fullname, R remote, Class... exportInterfaces) {
        Class[] interfaces = exportInterfaces.length == 0
                ? ApiUtils.allInterfaces(remote.getClass())
                : exportInterfaces;
        if (interfaces.length == 0)
            throw new IllegalArgumentException("No public interfaces for " + remote.getClass());
        String name = normalise(fullname);
        synchronized (map) {
            map.compute(name, (key, prev) -> {
                Map<Class, Object> map2 = prev == null ? new LinkedHashMap<>() : prev;
                for (Class anInterface : interfaces) {
                    map2.put(anInterface, remote);
                    for (RegistryListener listener : listeners) {
                        try {
                            listener.added(fullname, remote, anInterface);
                        } catch (IllegalStateException ise) {
                            listeners.remove(listener);
                        }
                    }
                }
                return map2;
            });
        }
    }

    @Override
    public <R> R acquire(String fullname, Class<R> rType) throws IllegalStateException {
        String name = normalise(fullname);
        synchronized (map) {
            Map<Class, Object> map2 = map.get(name);
            if (map2 == null) {
                return create(fullname, rType);
            }

            Object o = map2.get(rType);
            if (o != null)
                return (R) o;

            //noinspection unchecked
            return (R) map2.entrySet().stream()
                    .filter(e -> rType.isAssignableFrom(e.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElseGet(() -> create(fullname, rType));
        }
    }

    private <R> R create(String fullname, Class<R> rType) {
        Object service = instanceFactory.create(fullname, rType);
        if (service == null)
            throw new IllegalStateException("Unable to acquire " + fullname + " of type " + rType);

        if (rType.isInterface())
            put(fullname, service, rType);
        else
            put(fullname, service);
        return (R) service;
    }

    @Override
    public <R> R getOrNull(String fullname, Class<R> rType) {
        String name = normalise(fullname);
        synchronized (map) {
            Map<Class, Object> map2 = map.get(name);
            if (map2 == null)
                return null;
            Object o = map2.get(rType);
            if (o == null) {
                o = map2.entrySet().stream()
                        .filter(e -> rType.isAssignableFrom(e.getKey()))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .orElse(null);
            }
            return (R) o;
        }
    }

    @Override
    public <R> R find(String fullname, Class<R> rType) {
        String name = normalise(fullname);
        synchronized (map) {
            for (; ; ) {
                Map<Class, Object> map2 = map.get(name);
                if (map2 != null)
                    return null;
                synchronized (map2) {
                    Object o = map2.get(rType);
                    if (o == null) {
                        o = map2.entrySet().stream()
                                .filter(e -> rType.isAssignableFrom(e.getKey()))
                                .map(Map.Entry::getValue)
                                .findFirst()
                                .orElse(null);
                    }
                    if (o != null)
                        return (R) o;
                }
                if (name.isEmpty())
                    break;
                int pos = name.lastIndexOf('/');
                if (pos >= 0)
                    name = name.substring(0, pos);
                else
                    name = "";
            }
            throw new IllegalStateException("Unable to find a " + rType + " at " + fullname);
        }
    }

    private String normalise(String fullname) {
        return fullname.startsWith("/") ? fullname.substring(1) : fullname;
    }

    @Override
    public void remove(String fullname) {
        String name = normalise(fullname);
        synchronized (map) {
            map.remove(name);
        }
    }

    @Override
    public NavigableSet<String> allNames() {
        synchronized (map) {
            return new TreeSet<>(map.keySet());
        }
    }

    @Override
    public void registerListener(RegistryListener listener) {
        synchronized (map) {
            try {
                for (Map.Entry<String, Map<Class, Object>> entry : map.entrySet()) {
                    for (Map.Entry<Class, Object> entry2 : entry.getValue().entrySet()) {
                        listener.added(entry.getKey(), entry2.getKey(), (Class) entry2.getValue());
                    }
                }
                listeners.add(listener);
            } catch (IllegalStateException ignored) {

            }
        }
    }

    @Override
    public <R> ResourceContext<R> usingService(String fullname, Class<R> rType) {
        return new VanillaResourceContext<R>(fullname, rType, this.<R>find(fullname, rType));
    }
}
