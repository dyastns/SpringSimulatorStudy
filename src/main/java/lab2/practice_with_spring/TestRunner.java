package lab2.practice_with_spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRunner {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("lab2/practice_with_spring/context.xml");
        for (Printer printer : new Printer[]{
                context.getBean("printer1", Printer.class),
                context.getBean("printer2", Printer.class),
                context.getBean("printer3", Printer.class)
        }) {
            printer.print();
        }

    }
}
