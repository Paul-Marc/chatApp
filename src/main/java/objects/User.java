package objects;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String userName;
    private List<Contact> contacts;
    private List<Message> messages;
    private Contact currentContact;

    public User(String userName) {
        this.userName = userName;
        this.messages = new ArrayList<Message>();
        this.contacts = new ArrayList<Contact>();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        setCurrentContact(contact);
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

    public Contact getCurrentContact() {
        return currentContact;
    }

    public void setCurrentContact(Contact contact) {
        currentContact = contact;
    }

    public String getUserName() {
        return userName;
    }
}
