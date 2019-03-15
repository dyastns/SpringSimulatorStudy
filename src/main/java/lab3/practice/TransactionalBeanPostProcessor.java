package lab3.practice;

import com.study.applicationcontext.service.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class TransactionalBeanPostProcessor implements BeanPostProcessor {
    Map<String, Class<?>> originalClassMap = new HashMap<>();
    List<ClassMethod> classMethodList = new ArrayList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clazz = bean.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Transactional.class)) {
                originalClassMap.put(beanName, clazz);

                ClassMethod classMethod = new ClassMethod();
                classMethod.setClazz(clazz);
                classMethod.setMethodName(method.getName());
                classMethod.setParameterTypes(method.getParameterTypes());
                classMethodList.add(classMethod);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> clazz = originalClassMap.get(beanName);

        if (clazz != null) {
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    ClassMethod classMethod = new ClassMethod();
                    classMethod.setClazz(clazz);
                    classMethod.setMethodName(method.getName());
                    classMethod.setParameterTypes(method.getParameterTypes());

                    if (classMethodList.contains(classMethod)) {
                        System.out.println("Transaction start");
                        Object result = method.invoke(bean, args);
                        System.out.println("Transaction end");
                        return result;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
            });
        }

        return bean;
    }

    private class ClassMethod {
        private Class<?> clazz;
        private String methodName;
        private Class<?>[] parameterTypes;

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public void setParameterTypes(Class<?>[] parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassMethod that = (ClassMethod) o;
            return Objects.equals(clazz, that.clazz) &&
                    Objects.equals(methodName, that.methodName) &&
                    Arrays.equals(parameterTypes, that.parameterTypes);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(clazz, methodName);
            result = 31 * result + Arrays.hashCode(parameterTypes);
            return result;
        }
    }

}
