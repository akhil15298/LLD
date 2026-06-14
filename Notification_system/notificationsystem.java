package Notification_system;

import java.util.ArrayList;
import java.util.List;


/*============================
      Notification & Decorators
=============================*/


interface Notification {
    String getMessage();
}

class simpleNotification implements Notification {

    private String text;
    public simpleNotification(String text) {
        this.text = text;
    }

    @Override
    public String getMessage() {
        return text;
    }
}


abstract class notificationDecorator implements Notification {
    protected Notification decoratedNotification;

    public notificationDecorator(Notification decoratedNotification) {
        this.decoratedNotification = decoratedNotification;
    }
}

class timestampDecorator extends notificationDecorator {
    public timestampDecorator(Notification decoratedNotification) {
        super(decoratedNotification);
    }

    @Override
    public String getMessage() {
        return decoratedNotification.getMessage() + " [Timestamp: " + System.currentTimeMillis() + "]";
    }
}   

class signatureDecorator extends notificationDecorator {
    public signatureDecorator(Notification decoratedNotification) {
        super(decoratedNotification);
    }

    @Override
    public String getMessage() {
        return decoratedNotification.getMessage() + " [Signature: Akhil Ratna]";
    }
}


/*============================
  Observer Pattern Components
=============================*/

interface observer {
    void update(String message);
}

interface observable {
    void addObserver(observer o);
    void removeObserver(observer o);
    void notifyObservers(String message);
}

class NotificationObservable implements observable {
    private List<observer> observers = new ArrayList<>();
    private Notification currentNotification = null;

    @Override
    public void addObserver(observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (observer o : observers) {
            o.update(message);
        }
    }
    public void setNotification(Notification notification) {
    this.currentNotification = notification;
    notifyObservers(notification.getMessage());
}

    public Notification getNotificationObservable() {
        // This method can be used to retrieve the current notification if needed
        return currentNotification;
    }
}

/*============================
       ConcreteObservers
=============================*/

class logger implements observer {
    private NotificationObservable notificationObservable;

    public logger(NotificationObservable notificationObservable) {
        this.notificationObservable = notificationObservable;
        notificationObservable.addObserver(this);
    }

   
    @Override
    public void update(String message) {
        System.out.println("Logger received notification: " + message);
    }
}

/*============================
  Strategy Pattern Components (Concrete Observer 2)
=============================*/

interface notificationStrategy {
    void send(String message);
}

class emailNotificationStrategy implements notificationStrategy {
    @Override
    public void send(String message) {
        System.out.println("Email sent with message: " + message);
    }
}

class smsNotificationStrategy implements notificationStrategy {
    @Override
    public void send(String message) {
        System.out.println("SMS sent with message: " + message);
    }
}

class pushNotificationStrategy implements notificationStrategy {
    @Override
    public void send(String message) {
        System.out.println("Push notification sent with message: " + message);
    }
}

class Notificationengine implements observer {
    private NotificationObservable notificationObservable;
    private notificationStrategy strategy;

    public Notificationengine(
            NotificationObservable notificationObservable,
            notificationStrategy strategy) {

        this.notificationObservable = notificationObservable;
        this.strategy = strategy;

        notificationObservable.addObserver(this);
    }

    @Override
    public void update(String message) {
        strategy.send(message);
    }
}

/*============================
       NotificationService
=============================*/


class Notificationservice {
    private NotificationObservable notificationObservable;
    private List<Notification> notifications = new ArrayList<>();

    public Notificationservice(NotificationObservable notificationObservable) {
        this.notificationObservable = notificationObservable;
    }

    public static Object getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }

    public void sendNotification(Notification notification) {
        notificationObservable.setNotification(notification);
        notifications.add(notification);

    }
}





public class notificationsystem {

    public static void main(String[] args) {
        NotificationObservable notificationObservable = new NotificationObservable();
        Notificationservice notificationService = new Notificationservice(notificationObservable);

        // Create observers
        logger loggerObserver = new logger(notificationObservable);
        Notificationengine emailEngine =
        new Notificationengine(
                notificationObservable,
                new emailNotificationStrategy());

Notificationengine smsEngine =
        new Notificationengine(
                notificationObservable,
                new smsNotificationStrategy());

Notificationengine pushEngine =
        new Notificationengine(
                notificationObservable,
                new pushNotificationStrategy());

        // Create and send notifications
        Notification notification1 = new simpleNotification("Hello, this is a simple notification.");
        notificationService.sendNotification(notification1);

        Notification decoratedNotification = new signatureDecorator(new timestampDecorator(new simpleNotification("This is a decorated notification.")));
        notificationService.sendNotification(decoratedNotification);
    }
    
}
