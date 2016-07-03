package net.openhft.chronicle.essence.api;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by peter on 03/07/16.
 */
public enum ApiUtils {
    ;

    private static final Class[] NO_CLASSES = {};
    private static final ClassValue<Class[]> ALL_INTERFACES = new ClassValue<Class[]>() {
        @Override
        protected Class[] computeValue(Class<?> type) {
            Set<Class> interfaces = new LinkedHashSet<>();
            for (Class c = type; c != null; c = c.getSuperclass()) {
                Collections.addAll(interfaces, c.getInterfaces());
            }
            return interfaces.toArray(NO_CLASSES);
        }
    };

    public static Class[] allInterfaces(Class<?> aClass) {
        return ALL_INTERFACES.get(aClass);
    }
}
