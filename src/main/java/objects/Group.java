package objects;

public class Group {
    private String groupName;
    private int roomId;


    public Group(String groupName,int roomId) {
        this.groupName = groupName;
        this.roomId = roomId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getRoomId() {
        return roomId;
    }
}
