package lab2.practice;

import com.study.applicationcontext.entity.BeanDefinition;
import com.study.applicationcontext.service.BeanFactoryPostProcessor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

public class PropertyPlaceholder implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);

                for (Class<?> currentInterface : clazz.getInterfaces()) {
                    if (currentInterface == Printer.class) {
                        Map<String, String> dependencies = beanDefinition.getDependencies();

                        for (String fieldName : dependencies.keySet()) {
                            Field field = clazz.getDeclaredField(fieldName);
                            if (String.class == field.getType()) {
                                String oldValue = dependencies.get(fieldName);
                                String newValue = PropertyRepository.get(oldValue);
                                if (newValue != null) {
                                    dependencies.put(fieldName, newValue);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

