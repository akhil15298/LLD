package Inheritance;


class car{
    private String brand;
    private String name;
    private boolean engineStatus;
    //private int currentGear;
    private int currentSpeed;

    public car(String brand, String name){
        this.brand = brand;
        this.name = name;
        this.engineStatus = false;
        //this.currentGear = 0;
        this.currentSpeed = 0;
    }

    public void startengine() {
        engineStatus = true;
        System.out.println(brand + " " + name + " engine started.");
    }
    public void stopengine() {
        engineStatus = false;
        currentSpeed = 0;
        //currentGear = 0;
        System.out.println(brand + " " + name + " engine stopped.");
    }
    public void accelerate() {
        if (engineStatus) {
            currentSpeed += 10;
            System.out.println(brand + " " + name + " accelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " cannot accelerate. Engine is off.");
        }
    }
    public void brake() {
        if (currentSpeed > 0) {
            currentSpeed -= 10;
            System.out.println(brand + " " + name + " decelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " is already stationary.");
        }
    }
}


class manualcar extends car {

    private int currentGear;
    public manualcar(String brand, String name) {
        super(brand, name);
    }

    public void shiftgear(int gear) {
        //super.shiftgear();
        this.currentGear = gear;
        System.out.println("Manual car gear shifted to " + currentGear + ".");
    }
}

class electriccar extends car {
    private int batteryLevel;
    public electriccar(String brand, String name) {
        super(brand, name);
    }

    public void chargebattery(int level) {
        this.batteryLevel = level;
        System.out.println("Electric car battery charged to " + batteryLevel + "%.");
    }
}
public class inheritance {
    
    public static void main(String[] args) {
        manualcar myManualCar = new manualcar("Skoda", "Kushak");
        myManualCar.startengine();
        myManualCar.accelerate();
        myManualCar.shiftgear(2);
        myManualCar.brake();
        myManualCar.stopengine();

        System.out.println();

        electriccar myElectricCar = new electriccar("Tesla", "Model S");
        myElectricCar.startengine();
        myElectricCar.accelerate();
        myElectricCar.chargebattery(80);
        myElectricCar.brake();
        myElectricCar.stopengine();
    }
}
