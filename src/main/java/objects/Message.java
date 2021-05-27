package objects;

import java.sql.Time;
import java.sql.Timestamp;

public class Message {
    private String message;
    private String owner;
    private Timestamp timeStamp;
    private int roomID;

    public Message(int roomID, String message, String owner, Timestamp timeStamp) {
        this.message = message;
        this.owner = owner;
        this.roomID = roomID;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public int getRoomID() {
        return roomID;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getOwner() {
        return owner;
    }
}
