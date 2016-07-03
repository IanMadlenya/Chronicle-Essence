package net.openhft.chronicle.essence.classify;

import java.lang.reflect.Method;

/**
 * Created by Peter on 19/05/2016.
 */
public class RequestResponse implements MethodHandler {
    private final Method method;
    private final CallMode synchronous;

    public RequestResponse(Method method, CallMode synchronous) {
        this.method = method;
        this.synchronous = synchronous;
    }

    public static RequestResponse of(Method method, CallMode synchronous) {
        return new RequestResponse(method, synchronous);
    }

    @Override
    public String toString() {
        return "RequestResponse{" +
                "method=" + method +
                ", synchronous=" + synchronous +
                '}';
    }
}
