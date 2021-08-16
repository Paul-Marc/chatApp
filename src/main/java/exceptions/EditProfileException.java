package exceptions;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Einfache Exception wenn das eigene Profil nicht richtig bearbeitet wurde.
 */
public class EditProfileException extends Exception {
	 String text = "Error! Ueberpruefe Deine Eingaben!";
	    public String getMessage() {
	        return text;
	    }
	    public void sendError() {
	        System.out.println("Error");
	    }
}
