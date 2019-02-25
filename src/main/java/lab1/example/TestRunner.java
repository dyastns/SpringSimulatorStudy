package lab1.example;

import com.study.applicationcontext.ApplicationContext;
import com.study.applicationcontext.impl.ClassPathApplicationContext;

public class TestRunner {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathApplicationContext("lab1/example/context.xml");
        //Printer printer = context.getBean(Printer.class); // НЕ РАБОТАЕТ в моем АппКонтексте
        Printer printer = context.getBean(MessagePrinter.class);
        //Printer printer = (Printer) context.getBean("printer");
        //Printer printer = context.getBean("printer", MessagePrinter.class);
        printer.print();
    }
}
