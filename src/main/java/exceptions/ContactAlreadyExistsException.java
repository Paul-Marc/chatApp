package exceptions;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Einfach Exception wenn ein Kontakt bereits existiert.
 */
public class ContactAlreadyExistsException extends Throwable{

    public void sendError() {
        System.out.println("error");
    }
}
