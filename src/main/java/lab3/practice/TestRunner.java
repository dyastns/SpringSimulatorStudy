package lab3.practice;

import com.study.applicationcontext.ApplicationContext;
import com.study.applicationcontext.impl.ClassPathApplicationContext;

public class TestRunner {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathApplicationContext("lab3/practice/context.xml");
        Printer printer = context.getBean(Printer.class);
        while (true) {
            printer.print();
        }

    }
}
