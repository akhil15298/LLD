package CommandDesignPattern;

//command interface
interface command {
    void execute();
    void undo();
}

//receiver class
class light {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}

class fan {
    public void on() {
        System.out.println("Fan is ON");
    }

    public void off() {
        System.out.println("Fan is OFF");
    }
}

//concrete command for light
class lightCommand implements command {
    private light light;

    public lightCommand(light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}   

class fanCommand implements command {
    private fan fan;

    public fanCommand(fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        fan.on();
    }

    @Override
    public void undo() {
        fan.off();
    }
}

//invoker class
class remotecontroller{
    private static final int numbuttons = 4;
    private command[] buttons;
    private boolean[] buttonStates;

    public remotecontroller() {
        buttons = new command[numbuttons];
        buttonStates = new boolean[numbuttons];
        for (int i = 0; i < numbuttons; i++) {
            buttons[i] = null;
            buttonStates[i] = false;
        }
    }

    public void setCommand(int buttonIndex, command command) {
        if (buttonIndex >= 0 && buttonIndex < numbuttons) {
            buttons[buttonIndex] = command;
            buttonStates[buttonIndex] = false; // default state is off
        } else {
            System.out.println("Invalid button index");
        }
    }

    public void pressButton(int buttonIndex) {
        if (buttonIndex >= 0 && buttonIndex < numbuttons && buttons[buttonIndex] != null) {
            if (!buttonStates[buttonIndex]) {
                buttons[buttonIndex].execute();
                buttonStates[buttonIndex] = true; // update state to on
            } else {
                buttons[buttonIndex].undo();
                buttonStates[buttonIndex] = false; // update state to off
            }
        } else {
            System.out.println("Invalid button index or command not set");
        }
    }
}

public class commanddesignpattern {
    public static void main(String[] args) {
        light livingRoomLight = new light();
        fan bedroomFan = new fan();

        command lightOnCommand = new lightCommand(livingRoomLight);
        command fanOnCommand = new fanCommand(bedroomFan);

        remotecontroller remote = new remotecontroller();
        remote.setCommand(0, lightOnCommand);
        remote.setCommand(1, fanOnCommand);

        // Pressing buttons
        remote.pressButton(0); // Turns on the light
        remote.pressButton(1); // Turns on the fan

        // Pressing buttons again to toggle off
        remote.pressButton(0); // Turns off the light
        remote.pressButton(1); // Turns off the fan

        remote.pressButton(2); // Invalid button index
    }
}
