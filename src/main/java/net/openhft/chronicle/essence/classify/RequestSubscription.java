package net.openhft.chronicle.essence.classify;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 19/05/2016.
 */
public class RequestSubscription implements MethodHandler {
    private final Method method;
    private final CallMode callMode;
    private final List<ArgFilter> argFilters;

    public RequestSubscription(Method method, CallMode callMode, List<ArgFilter> argFilters) {

        this.method = method;
        this.callMode = callMode;
        this.argFilters = argFilters;
    }

    public static RequestSubscription of(Method method, CallMode callMode, List<ArgFilter> argFilters) {
        return new RequestSubscription(method, callMode, new ArrayList<>(argFilters));
    }

    @Override
    public String toString() {
        return "RequestSubscription{" +
                "method=" + method +
                ", callMode=" + callMode +
                ", argFilters=" + argFilters +
                '}';
    }
}
