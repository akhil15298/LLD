package ProxyDesignPattern.ProtectionProxy;

interface IDocumentReader{
    void unlockPDF(String fileName, String password);
}

class RealDocumentReader implements IDocumentReader{
    @Override
    public void unlockPDF(String fileName, String password) {
        System.out.println("Unlocking PDF: " + fileName + " with password: " + password);
        System.out.println(fileName + ": PDF unlocked successfully!");
        System.out.println("Displaying PDF: " + fileName);
    }
}

class user{
    public String name;
    public boolean isPremiumUser;
    public user(String name, boolean isPremiumUser){
        this.name = name;
        this.isPremiumUser = isPremiumUser;
    }

}

class DocumentReaderProxy implements IDocumentReader{
    private RealDocumentReader realDocumentReader;
    private user user;

    public DocumentReaderProxy(user user){
        this.user = user;
        this.realDocumentReader = new RealDocumentReader();
    }

    @Override
    public void unlockPDF(String fileName, String password) {
        if(user.isPremiumUser){
            realDocumentReader.unlockPDF(fileName, password);
        } else {
            System.out.println("Access Denied: " + user.name + " is not a premium user. Cannot unlock PDF: " + fileName);
        }
    }
}


public class protectionproxy {
    public static void main(String[] args) {
        user premiumUser = new user("Alice", true);
        user regularUser = new user("Bob", false);

        IDocumentReader premiumUserProxy = new DocumentReaderProxy(premiumUser);
        IDocumentReader regularUserProxy = new DocumentReaderProxy(regularUser);

        System.out.println("Premium User trying to unlock PDF:");
        premiumUserProxy.unlockPDF("premium_document.pdf", "securepassword");

        System.out.println("\nRegular User trying to unlock PDF:");
        regularUserProxy.unlockPDF("regular_document.pdf", "password123");
    }
}
