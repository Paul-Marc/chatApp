package exceptions;

public class NameInUseException extends Exception{
    String text = "Moin";
    public String getMessage() {
        return text;
    }
    public void sendError() {
        System.out.println("Error");
    }
}
