package lab1.practice;


import com.study.applicationcontext.service.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * класс спрингового пост процессора, должен имплементировать интерфейс
 *
 * @see BeanPostProcessor
 *
 * Класс отвечает за логику инжекта случайного числа в поле проаннотированное, специально обученной аннотацией
 */
public class InjectRandomPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class clazz = bean.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);

            if (annotation != null) {
                Random random = new Random();
                int value = random.nextInt(annotation.value()) + 1;
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                field.setAccessible(false);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
