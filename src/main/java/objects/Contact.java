package objects;

import java.awt.image.BufferedImage;

public class Contact {
    private User user;
    private String bio;

    public Contact(User user, String bio) {
        this.user = user;
        this.bio = bio;
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getBio() {
        return bio;
    }
}
