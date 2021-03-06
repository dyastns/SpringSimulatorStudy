package lab2.practice;

import com.study.applicationcontext.ApplicationContext;
import com.study.applicationcontext.impl.ClassPathApplicationContext;

public class TestRunner {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathApplicationContext("lab2/practice/context.xml");
        for (Printer printer : new Printer[]{
                context.getBean("printer1", Printer.class),
                context.getBean("printer2", Printer.class),
                context.getBean("printer3", Printer.class)
        }) {
            printer.print();
        }

    }
}
