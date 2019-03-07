package lab1.practice;

import com.study.applicationcontext.ApplicationContext;
import com.study.applicationcontext.impl.ClassPathApplicationContext;

public class TestRunner {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathApplicationContext("lab1/practice/context.xml");
        Printer printer = context.getBean(Printer.class);
        printer.print();
    }
}
