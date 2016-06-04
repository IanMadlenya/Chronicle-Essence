package net.openhft.chronicle.essence.classify;

import java.lang.reflect.Method;

/**
 * Created by Peter on 19/05/2016.
 */
public class AsyncLambda implements MethodHandler {
    private final Method method;

    public AsyncLambda(Method method) {
        this.method = method;
    }

    public static MethodHandler of(Method method) {
        return new AsyncLambda(method);
    }

    @Override
    public String toString() {
        return "AsyncLambda{" +
                "method=" + method +
                '}';
    }
}
