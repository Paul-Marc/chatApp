package exceptions;

public class InvalidInputException extends Exception{
    String text = "Deine Eingaben stimmen nicht.";
    public String getMessage() {
        return text;
    }
    public void sendError() {
        System.out.println("Error");
    }
}
