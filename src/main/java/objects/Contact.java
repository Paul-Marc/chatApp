package objects;

public class Contact {
    private String userName;
    private String bio;
    private int roomID;

    public Contact(String userName, String bio, int roomID) {
        this.userName = userName;
        this.bio = bio;
        this.roomID = roomID;
    }

    public String getUserName() {
        return userName;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getBio() {
        return bio;
    }
}
