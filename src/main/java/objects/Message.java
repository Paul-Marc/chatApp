package objects;

public class Message {
    private String message;
    private User owner;
    private int roomID;
    private long timeStamp;

    public Message(String message, User owner, long timeStamp) {
        this.message = message;
        this.owner = owner;
        this.roomID = owner.getCurrentContact().getRoomID();
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public int getRoomID() {
        return roomID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public User getOwner() {
        return owner;
    }
}
