package objects;

import databse.Database;
import exceptions.ContactAlreadyExistsException;
import exceptions.ContactNotFoundException;

import javax.naming.ContextNotEmptyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Zuordnung zu Person: Paul Conrad (Einzelne Erweiterungen) & Marc Palfner (Grundlegende Klasse)
 * 
 * Zweck: Die Klasse "User" umfasst alle Informationen die zu einem Nutzer existieren. Es wird beispielsweise 
 * bei der Anmeldung oder Registrierung ein Objekt der Klasse erzeugt.
 */
public class User {

	private final String userName;
	private String nickname;
	private String password;
	private List<Contact> contacts;
	private List<Message> messages;	
	private Contact currentContact;
	private String biography;
	private String gender;
	private String dateOfBirth;
	private String hobbies;
	private boolean privateprofile;

	/**
	 * 
	 * @param userName
	 * Zweck: Kontruktor
	 */
	public User(String userName) {
		this.userName = userName;
		this.messages = new ArrayList<Message>();
		this.contacts = new ArrayList<Contact>();
	}

	/**
	 * 
	 * @param contacts
	 * Zweck: Setter
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
		if (!contacts.isEmpty()) {
			setCurrentContact(contacts.get(0));
		}
	}

	/**
	 * Zweck: Neue Nachrichten werden geladen
	 */
	public void updateMessages() {
		if (getCurrentContact() != null) {
			messages = Database.getMessagesFromRoom(getCurrentContact().getRoomID());
		}
	}

	/**
	 * Zweck: Lädt die Kontakte nochmal neu
	 */
	public void updateContacts() {
		setContacts(Database.getContacts(this.userName));
	}

	/**
	 * 
	 * @param contact
	 * Zweck: Fügt einen neuen Kontakt hinzu und ruft dessen Chatfenster auf
	 */
	public void addContact(Contact contact) {
		contacts.add(contact);
		setCurrentContact(contact);
	}

	

	/**
	 * Getter
	 * @return
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * Getter
	 * @return
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Getter
	 * @return
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * Setter
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Getter
	 * @return
	 */
	public Contact getCurrentContact() {
		return currentContact;
	}

	/**
	 * Zweck: Getter für einen bestimmten Kontakt
	 * @param userName
	 * @return
	 * @throws ContactNotFoundException
	 */
	public Contact getContact(String userName) throws ContactNotFoundException {
		for (int i = 0; i < getContacts().size(); i++) {
			if (getContacts().get(i).getUserName().equals(userName)) {
				return getContacts().get(i);
			}
		}
		throw new ContactNotFoundException();
	}

	/**
	 * Zweck: Gibt einen Kontakt anhand der roomId zurück
	 * @param roomID
	 * @return
	 * @throws ContactNotFoundException
	 */
	public Contact getContact(int roomID) throws ContactNotFoundException {
		for (int i = 0; i < getContacts().size(); i++) {
			if (getContacts().get(i).getRoomID() == roomID) {
				return getContacts().get(i);
			}
		}
		throw new ContactNotFoundException();
	}

	/**
	 * Zweck: Aktuellen Kontakt festlegen und Nachrichten abrufen
	 * @param contact
	 * @return
	 */
	public boolean setCurrentContact(Contact contact) {
		for (int i = 0; i < getContacts().size(); i++) {
			if (getContacts().get(i).getRoomID() == contact.getRoomID()) {
				//======================================================
				System.out.println("In setCurrentContact(contact)");
				System.out.println("Neuer Kontakt: " + contact.getUserName());
				System.out.println("Dessen Id: " + contact.getRoomID());
				//======================================================
				currentContact = contact;
				try {
				System.out.println("Versucht nachrichten des raumes zu bekommen...");
				messages = Database.getMessagesFromRoom(contact.getRoomID());
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Zweck: Schauen ob Profil privat ist
	 * @return
	 */
	public boolean isPrivateprofile() {
		return privateprofile;
	}

	/**
	 * Setter
	 * @param privateprofile
	 */
	public void setPrivateprofile(boolean privateprofile) {
		this.privateprofile = privateprofile;
	}

	/**
	 * Setter
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Zweck: Schauen ob ein Kontakt bereits existiert
	 * @param userName
	 * @return
	 */
	public boolean contactAlreadyExists(String userName) {
		for (int i = 0; i < getContacts().size(); i++) {
			if (getContacts().get(i).getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	

	/**
	 * Zweck: Kontakt anhand der roomid setzen
	 * @param roomid
	 * @return
	 */
	public boolean setCurrentContact(int roomid) {
		for (int i = 0; i < getContacts().size(); i++) {
			if (getContacts().get(i).getRoomID() == roomid) {
				return setCurrentContact(getContacts().get(i));
			}
		}
		return false;
	}

	//Getter
	public String getUserName() {
		return userName;
	}
	//Getter
	public String getBiography() {
		return biography;
	}
	//Getter
	public String getGender() {
		return gender;
	}
	//Getter
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	//Getter
	public String getHobbies() {
		return hobbies;
	}
	//Setter
	public void setBiography(String biography) {
		this.biography = biography;
	}
	//Setter
	public void setGender(String gender) {
		this.gender = gender;
	}
	//Setter
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	//Setter
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	/**
	 * Zweck: Nur zu Testzwecken
	 */
	@Override
	public String toString() {
		return "User{" + "userName='" + userName + '\'' + ", contacts=" + contacts.toString() + ", messages=" + messages
				+ ", currentContact=" + currentContact + '}';
	}
}
