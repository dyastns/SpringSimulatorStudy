package lab1.example;



import com.study.applicationcontext.service.BeanPostProcessor;
//import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class InjectIntPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            InjectInt annotation = field.getDeclaredAnnotation(InjectInt.class);
            if (annotation != null) {
                Integer newValue = IntServiceLocator.get(annotation.value());
                field.setAccessible(true);
                //ReflectionUtils.setField(field, bean, newValue);
                try {
                    field.set(bean, newValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                field.setAccessible(false);
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
