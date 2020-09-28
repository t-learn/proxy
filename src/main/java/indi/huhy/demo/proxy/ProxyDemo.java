package indi.huhy.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author huhy
 * @version 1.0
 * @date 2020/9/28 14:35
 */
public class ProxyDemo {

    public static void main(String[] args) {
        ProxySubject realProxySubject = new ProxySubject();
        ProxySubjectInterface proxySubject = (ProxySubjectInterface) Proxy.newProxyInstance(realProxySubject.getClass().getClassLoader()
                , realProxySubject.getClass().getInterfaces()
                , new ProxyHandler(realProxySubject));
        proxySubject.request();
    }

}

interface ProxySubjectInterface {

    void request();
}

class ProxySubject implements ProxySubjectInterface {

    @Override
    public void request() {
        System.out.println("request");
    }
}

class ProxyHandler implements InvocationHandler {

    private final ProxySubject proxySubject;

    public ProxyHandler(ProxySubject proxySubject) {
        this.proxySubject = proxySubject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            // 前置增强
            System.out.println("before");
            result = method.invoke(proxySubject, args);
            // 后置增强
            System.out.println("after");
        } catch (Exception e) {
            // 异常增强
            System.out.println("exception");
        }
        return result;
    }
}
