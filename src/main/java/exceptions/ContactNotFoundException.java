package exceptions;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Einfach Exception wenn ein Kontakt nicht gefunden werden kann.
 */
public class ContactNotFoundException extends Throwable{

    public void sendError() {
        System.out.println("Error");
    }
}
