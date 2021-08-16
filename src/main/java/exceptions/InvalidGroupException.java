package exceptions;


/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Einfache Exception wenn eine Gruppe nicht erstellt werden kann.
 */
public class InvalidGroupException extends Exception {
    String text = "Deine Eingaben sind ungueltig. Überprüfe ob die eingegeben Nutzer wirklich existieren.";
    public String getMessage() {
        return text;
    }
    public void sendError() {
        System.out.println("Error");
    }
}
