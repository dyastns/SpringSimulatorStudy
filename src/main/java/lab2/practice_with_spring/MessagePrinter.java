package lab2.practice_with_spring;

public class MessagePrinter implements Printer {
    private String message;

    public void print() {
        System.out.println(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
