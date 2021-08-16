package exceptions;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Einfach Exception wenn ein Input ungueltig ist.
 */
public class InvalidInputException extends Exception{
    String text = "Deine Eingaben stimmen nicht.";
    public String getMessage() {
        return text;
    }
    public void sendError() {
        System.out.println("Error");
    }
}
