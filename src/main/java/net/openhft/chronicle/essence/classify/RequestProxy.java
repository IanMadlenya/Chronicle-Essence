package net.openhft.chronicle.essence.classify;

import java.lang.reflect.Method;

/**
 * Created by Peter on 19/05/2016.
 */
public class RequestProxy implements MethodHandler {
    private final Method method;

    public RequestProxy(Method method) {
        this.method = method;
    }

    public static MethodHandler of(Method method) {
        return new RequestProxy(method);
    }

    @Override
    public String toString() {
        return "RequestProxy{" +
                "method=" + method +
                '}';
    }
}
