package leetcode.hubspot;

public class CustomLogger {
    public static void log(String message) {
        if (!"production".equals(System.getProperty("env"))) {
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        System.setProperty("env", "development"); // You would typically set this outside the app
        log("This is a debug message.");
    }
}

