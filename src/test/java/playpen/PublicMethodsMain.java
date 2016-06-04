package playpen;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Peter on 21/05/2016.
 */
public class PublicMethodsMain {
    interface A {
        void method();
    }

    interface B extends A {
        void method();
    }

    interface C extends B {
        void method();
    }

    interface ABC extends A, B, C {

    }

    interface CBA extends C, B, A {

    }

    interface BAC extends B, A, C {

    }

    public static void main(String[] args) {
        for (Method method : ABC.class.getMethods()) {
            System.out.println(method);
        }

        A a = createProxy(A.class);
        a.method();
        A b = createProxy(B.class);
        b.method();
        A c = createProxy(C.class);
        c.method();
        A abc = createProxy(ABC.class);
        abc.method();
        A bac = createProxy(BAC.class);
        bac.method();
        A cba = createProxy(CBA.class);
        cba.method();
    }

    static A createProxy(Class aClass) {
        return (A) Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{aClass}, (proxy, method, args) -> {
            System.out.println(aClass.getSimpleName()+" calls " + method);
            return null;
        });
    }
}
