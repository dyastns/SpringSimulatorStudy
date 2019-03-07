package lab2.practice_with_spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;

import java.lang.reflect.Field;

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
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);
                for (Class<?> currentInterface : clazz.getInterfaces()) {
                    if (currentInterface == Printer.class) {
                        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
                        PropertyValue[] propertyValues = mutablePropertyValues.getPropertyValues();
                        for (int i = 0; i < propertyValues.length; i++) {
                            PropertyValue propertyValue = propertyValues[i];
                            Field field = clazz.getDeclaredField(propertyValue.getName());
                            if (String.class == field.getType()) {
                                String oldValue = ((TypedStringValue) propertyValue.getValue()).getValue();
                                String newValue = PropertyRepository.get(oldValue);
                                if (newValue != null) {
                                    propertyValue.setConvertedValue(newValue);
                                    mutablePropertyValues.setPropertyValueAt(propertyValue, i);
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
