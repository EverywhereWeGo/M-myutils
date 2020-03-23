package yanzheng.daili.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Family implements InvocationHandler {

    private Girl girl;

    public Family(Girl girl) {
        this.girl = girl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置检查");
        Object invoke = method.invoke(girl, args);
        System.out.println("后置检查");
        return invoke;
    }


    public Object genInstance() {
        return Proxy.newProxyInstance(girl.getClass().getClassLoader(), girl.getClass().getInterfaces(), this);
    }
}
