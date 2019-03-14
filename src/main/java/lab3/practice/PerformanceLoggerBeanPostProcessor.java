package lab3.practice;

import com.study.applicationcontext.service.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class PerformanceLoggerBeanPostProcessor implements BeanPostProcessor {
    Map<String, Class<?>> originalBeanMap = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clazz = bean.getClass();

        if (clazz.isAnnotationPresent(Logger.class)) {
            originalBeanMap.put(beanName, clazz);
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> clazz = originalBeanMap.get(beanName);

        if (clazz != null) {
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    long start = System.currentTimeMillis();
                    Object result = method.invoke(bean, args);
                    long end = System.currentTimeMillis();
                    System.out.println("Execution took = " + (end - start) + " ms");
                    return result;
                }
            });
        }

        return bean;
    }
}
