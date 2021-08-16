package objects;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Die Klasse speichert die Kontakte des aktuellen Users ab.
 */
public class Contact {
    private String userName;    
    private int roomID;

    /**
     * Konstruktor
     * @param userName
     * @param bio
     * @param roomID
     */
    public Contact(String userName, int roomID) {
        this.userName = userName;
        this.roomID = roomID;
    }

    //Getter
    public String getUserName() {
        return userName;
    }

    //Getter
    public int getRoomID() {
        return roomID;
    }
    
    /**
     * Zu testzwecken
     */
    @Override
    public String toString() {
        return "Contact{" +
                "userName='" + userName + '\'' +                
                ", roomID=" + roomID +
                '}';
    }
}
