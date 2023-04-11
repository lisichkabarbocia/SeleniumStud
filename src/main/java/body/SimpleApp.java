package body;

public class SimpleApp {
    private static final String MESSAGE = "Hello World!";

    public SimpleApp() {}

    public static void main(String[] args) {
        System.out.println(MESSAGE);
    }

    public String getMessage() {
        return MESSAGE;
    }
}