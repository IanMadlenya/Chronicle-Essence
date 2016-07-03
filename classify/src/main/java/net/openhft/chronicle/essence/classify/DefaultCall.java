package net.openhft.chronicle.essence.classify;

import java.lang.reflect.Method;

/**
 * Created by Peter on 19/05/2016.
 */
public class DefaultCall implements MethodHandler{
    private final Method method;

    public DefaultCall(Method method) {
        this.method = method;
    }

    public static DefaultCall of(Method method) {
        return new DefaultCall(method);
    }

    @Override
    public String toString() {
        return "DefaultCall{" +
                "method=" + method +
                '}';
    }
}
