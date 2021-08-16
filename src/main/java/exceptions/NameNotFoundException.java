package exceptions;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Einfache Exception wenn ein Name nicht gefunden wird.
 */
public class NameNotFoundException extends Throwable {
    public void sendError() {
        System.out.println("Error");
    }
}
