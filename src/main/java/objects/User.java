package objects;

import java.util.List;

public class User {

    private List<Contact> contacts;
    private List<Message> messages;
    private final String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getUserName() {
        return userName;
    }
}
