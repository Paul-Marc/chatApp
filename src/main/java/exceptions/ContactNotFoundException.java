package exceptions;

public class ContactNotFoundException extends Throwable{

    public void sendError() {
        System.out.println("Error");
    }
}
