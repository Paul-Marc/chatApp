package objects;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Zuordnung zu Person: Marc Palfner
 * 
 * Zweck: Die Klasse umfasst Chatnachrichten mit allen dazugehörigen Informationen. Objekte basieren
 * auf den Datenbank Eintragungen.
 */
public class Message {
    private String message;
    private String owner;
    private Timestamp timeStamp;
    private int roomID;

    /**
     * Konstruktor
     * @param roomID
     * @param message
     * @param owner
     * @param timeStamp
     */
    public Message(int roomID, String message, String owner, Timestamp timeStamp) {
        this.message = message;
        this.owner = owner;
        this.roomID = roomID;
        this.timeStamp = timeStamp;
    }
    //Getter
    public String getMessage() {
        return message;
    }
    //Getter
    public int getRoomID() {
        return roomID;
    }
    //Getter
    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    //Getter
    public String getOwner() {
        return owner;
    }
}
