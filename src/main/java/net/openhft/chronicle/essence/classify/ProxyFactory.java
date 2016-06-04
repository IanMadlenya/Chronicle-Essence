package net.openhft.chronicle.essence.classify;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.stream.Stream;

/**
 * Created by Peter on 19/05/2016.
 */
public class ProxyFactory<I> {

    static final ThreadLocal<ProxyFactory> lastProxyFactory = new ThreadLocal<>();
    static final ThreadLocal<List<ArgFilter>> lastFilters = ThreadLocal.withInitial(ArrayList::new);
    private static final ClassValue<ProxyFactory> PROXY_FACTORY_CLASS_VALUE = new ClassValue<ProxyFactory>() {
        @Override
        protected ProxyFactory computeValue(Class<?> type) {
            return new ProxyFactory(type);
        }
    };
    private final Class<I> iClass;
    private final Map<Method, MethodHandler> methodMap = new TreeMap<Method, MethodHandler>((a, b) -> {
        int cmp = javaSignature(a).compareTo(javaSignature(b));
        if (cmp == 0)
            return a.toString().compareTo(b.toString());
        return cmp;
    });
    Method lastMethod;
    Object[] lastArgs;

    private ProxyFactory(Class<I> iClass) {
        this.iClass = iClass;
        Method[] methods = iClass.getMethods();
        Map<String, Class> abstractSet = new LinkedHashMap<>();
        for (Method method : methods) {
            if (Modifier.isAbstract(method.getModifiers())) {
                String key = javaSignature(method);
                abstractSet.putIfAbsent(key, method.getDeclaringClass());
            }
        }
        for (Method method : methods) {
            if (method.getName().equals("parallelStream"))
                Thread.yield();
            MethodHandler classify;
            if (Modifier.isAbstract(method.getModifiers()) && abstractSet.get(javaSignature(method)) != method.getDeclaringClass())
                continue;
            if ((method.isBridge() || method.isDefault() || method.isSynthetic()) && abstractSet.containsKey(javaSignature(method)))
                classify = DefaultCall.of(method);
            else
                classify = classify(method);
            methodMap.put(method, classify);
        }
    }

    public static <I> ProxyFactory<I> proxyFactory1(Class<I> iClass) {
        return PROXY_FACTORY_CLASS_VALUE.get(iClass);
    }

    public static <I> ProxyFactory<I> proxyFactory(Class<I> iClass) {
        return new ProxyFactory<I>(iClass);
    }

    public static <I> I mock(Class<I> iClass) {
        return proxyFactory(iClass).mock();
    }

    public static ProxyFactory.WhenClause when(Object o) {
        return lastCall();
    }

    public static <S> S subscriptionAny() {
        lastFilters.get().add(ArgFilters.SUBSCRIPTION_ANY);
        return null;
    }

    public static Object any() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return null;
    }

    public static <S> S any(Class<S> sClass) {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return null;
    }

    public static Boolean anyBoolean() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return true;
    }

    public static Byte anyByte() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return (byte) 0;
    }

    public static Short anyShort() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return (short) 0;
    }

    public static Character anyChar() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return '\0';
    }

    public static Integer anyInt() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return 0;
    }

    public static Float anyFloat() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return 0.0f;
    }

    public static Long anyLong() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return 0L;
    }

    public static Double anyDouble() {
        lastFilters.get().add(ArgFilters.ANY_VALUE);
        return 0.0;
    }

    public static ProxyFactory.WhenClause lastCall() {
        ProxyFactory proxyFactory = lastProxyFactory.get();
        return proxyFactory.new WhenClause();
    }

    private String javaSignature(Method method) {
        return method.getName() + Arrays.toString(method.getParameterTypes());
    }

    protected MethodHandler classify(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType == void.class) {
            return AsyncLambda.of(method);
        } else if (Future.class.isAssignableFrom(returnType) || (method.getParameterCount() == 0 && isProxyable(returnType))) {
            return RequestProxy.of(method);
        } else if (useSubscriptionByReturnType(returnType)) {
            return RequestSubscription.of(method, CallMode.SYNCHRONOUS, Collections.emptyList());
        } else if (useDefaultByReturnType(returnType)) {
            return DefaultCall.of(method);
        } else {// Request
            return RequestResponse.of(method, CallMode.SYNCHRONOUS);
        }
    }

    protected boolean useSubscriptionByReturnType(Class<?> returnType) {
        return Iterator.class.isAssignableFrom(returnType);
    }

    protected boolean useDefaultByReturnType(Class<?> returnType) {
        return Spliterator.class.isAssignableFrom(returnType)
                || Stream.class.isAssignableFrom(returnType);
    }

    protected boolean isProxyable(Class<?> returnType) {
        return Iterable.class.isAssignableFrom(returnType)
                || Condition.class.isAssignableFrom(returnType)
                || Map.class.isAssignableFrom(returnType);
    }

    public I mock() {
        return (I) Proxy.newProxyInstance(iClass.getClassLoader(), new Class[]{iClass}, new PFInvocationHandler());
    }

    public Map<Method, MethodHandler> methodMap() {
        return methodMap;
    }

    private void withCallMode(List<ArgFilter> argFilters, CallMode callMode) {
        MethodHandler mh;
        if (argFilters.stream().allMatch(f -> f == ArgFilters.ANY_VALUE)) {
            if (callMode == CallMode.ASYNCHRONOUS)
                mh = AsyncLambda.of(lastMethod);
            else
                mh = RequestResponse.of(lastMethod, callMode);
        } else if (argFilters.stream().allMatch(f -> f == ArgFilters.ANY_VALUE || f == ArgFilters.SUBSCRIPTION_ANY)) {
            mh = RequestSubscription.of(lastMethod, callMode, argFilters);
        } else {
            throw new UnsupportedOperationException("args: " + argFilters);
        }
        methodMap.put(lastMethod, mh);
    }

    private void useDefault(List<ArgFilter> argFilters) {
        DefaultCall dc = DefaultCall.of(lastMethod);
        methodMap.put(lastMethod, dc);
    }

    public class WhenClause {

        public WhenClause returnProxy() {
            if (lastFilters.get().stream().anyMatch(f -> f != ArgFilters.ANY_VALUE))
                throw new UnsupportedOperationException("All args must be any()");
            methodMap.put(lastMethod, RequestProxy.of(lastMethod));
            lastFilters.get().clear();
            return this;
        }

        public void waitSynchronously() {
            List<ArgFilter> argFilters = lastFilters.get();
            ProxyFactory proxyFactory = lastProxyFactory.get();
            proxyFactory.withCallMode(argFilters, CallMode.SYNCHRONOUS);
            lastFilters.get().clear();
        }

        public void passAsynchronously() {
            List<ArgFilter> argFilters = lastFilters.get();
            ProxyFactory proxyFactory = lastProxyFactory.get();
            proxyFactory.withCallMode(argFilters, CallMode.ASYNCHRONOUS);
            lastFilters.get().clear();
        }

        public void useDefault() {
            List<ArgFilter> argFilters = lastFilters.get();
            ProxyFactory proxyFactory = lastProxyFactory.get();
            proxyFactory.useDefault(argFilters);
            lastFilters.get().clear();
        }
    }

    class PFInvocationHandler implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            lastProxyFactory.set(ProxyFactory.this);
            lastMethod = method;
            lastArgs = args;
            return null;
        }
    }
}
