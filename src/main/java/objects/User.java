package objects;

import databse.Database;
import exceptions.ContactAlreadyExistsException;
import exceptions.ContactNotFoundException;

import javax.naming.ContextNotEmptyException;
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
        if (!contacts.isEmpty()) {
            setCurrentContact(contacts.get(0));
        }
    }

    public void updateMessages() {
        if (getCurrentContact() != null) {
            messages = Database.getMessagesFromRoom(getCurrentContact().getRoomID());
        }
    }

    public void updateContacts() {
        setContacts(Database.getContacts(this.userName));
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

    public Contact getCurrentContact() {
        return currentContact;
    }

    public Contact getContact(String userName) throws ContactNotFoundException {
        for (int i = 0; i < getContacts().size(); i++) {
            if (getContacts().get(i).getUserName().equals(userName)) {
                return getContacts().get(i);
            }
        }
        throw new ContactNotFoundException();
    }

    public Contact getContact(int roomID) throws ContactNotFoundException {
        for (int i = 0; i < getContacts().size(); i++) {
            if (getContacts().get(i).getRoomID() == roomID)  {
                return getContacts().get(i);
            }
        }
        throw new ContactNotFoundException();
    }

    public boolean setCurrentContact(Contact contact) {
        for (int i = 0; i < getContacts().size(); i++) {
            if (getContacts().get(i).getRoomID() == contact.getRoomID()) {
                currentContact = contact;
                messages = Database.getMessagesFromRoom(contact.getRoomID());
                return true;
            }
        }
        return false;
    }

    public boolean contactAlreadyExists(String userName) {
        for (int i = 0; i < getContacts().size(); i++) {
            if (getContacts().get(i).getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean setCurrentContact(int roomid) {
        for (int i = 0; i < getContacts().size(); i++) {
            if (getContacts().get(i).getRoomID() == roomid) {
                return setCurrentContact(getContacts().get(i));
            }
        }
        return false;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", contacts=" + contacts.toString() +
                ", messages=" + messages +
                ", currentContact=" + currentContact +
                '}';
    }
}
