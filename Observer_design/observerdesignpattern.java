package Observer_design;

import java.util.ArrayList;
import java.util.List;

interface Subscribers{
    void update();
}



interface youtubechannel{
         void Subscribe(Subscribers s);
         void unsubscribe(Subscribers s);
         void notifySubscribers();


}

class channel implements youtubechannel{
    private List<Subscribers> subscribers = new ArrayList<>();
    private String state;

    @Override
    public void Subscribe(Subscribers s) {
        subscribers.add(s);
    }

    @Override
    public void unsubscribe(Subscribers s) {
        subscribers.remove(s);
    }

    @Override
    public void notifySubscribers() {
        for (Subscribers s : subscribers) {
            s.update();
        }
    }

    public void setState(String state) {
        this.state = state;
        notifySubscribers();
    }

    public String getState() {
        return state;
    }
}

class subscriber implements Subscribers {
    private String name;
    private channel channel;

    public subscriber(String name, channel channel) {
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void update() {
        System.out.println(name + " received update: " + channel.getState());
    }
}
       
       
       
       
class observerdesignpattern {

    public static void main(String[] args) {
        channel myChannel = new channel();

        subscriber s1 = new subscriber("Alice", myChannel);
        subscriber s2 = new subscriber("Bob", myChannel);

        myChannel.Subscribe(s1);
        myChannel.Subscribe(s2);

        myChannel.setState("New video uploaded!");

        myChannel.unsubscribe(s1);

        myChannel.setState("Another video uploaded!");
    }
}