package indi.huhy.demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author huhy
 * @version 1.0
 * @date 2020/9/28 14:36
 */
public class CglibDemo {

    public static void main(String[] args) {
        Enhancer e = new Enhancer();
        CglibSubject cglibSubject = new CglibSubject();
        // 设置增强回调
        e.setCallback(new ProxyInterceptor(cglibSubject));

        // 对类生成代理对象
        e.setSuperclass(CglibSubject.class);
        e.setInterfaces(null);
        CglibSubject proxySubject = (CglibSubject) e.create();
        proxySubject.request();
    }
}

class CglibSubject {

    public void request() {
        System.out.println("request");
    }
}

class ProxyInterceptor implements MethodInterceptor {

    private final CglibSubject cglibSubject;

    public ProxyInterceptor(CglibSubject cglibSubject) {
        this.cglibSubject = cglibSubject;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        try {
            // 前置增强
            System.out.println("before");
            result = method.invoke(cglibSubject, objects);
            // 后置增强
            System.out.println("after");
        } catch (Exception e) {
            // 异常增强
            System.out.println("exception");
        }
        return result;
    }
}

