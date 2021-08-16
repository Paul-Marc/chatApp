package exceptions;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Einfach Exception wenn ein Nutzer sich selbst als Kontakt hinzufuegen will.
 */
public class CantChatWithYourSelfException extends Throwable{
    public void sendError(){
        System.out.println("Error");
    }
}
