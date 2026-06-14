package Polymorphism;


abstract class car{
    protected String brand;
    protected String name;
    protected boolean engineStatus;
    protected int currentSpeed;


    public car(String brand, String name){
        this.brand = brand;
        this.name = name;
        this.engineStatus = false;
        this.currentSpeed = 0;
    }

    public void startengine() {
        engineStatus = true;
        System.out.println(brand + " " + name + " engine started.");
    }
    
    public void stopengine() {
        engineStatus = false;
        currentSpeed = 0;
        System.out.println(brand + " " + name + " engine stopped.");
    }

    public abstract void accelerate();  // Dynamic polymorphism (run-time polymorphism) - method overriding

    public abstract void accelerate(int increment);  // static polymorphism (compile-time polymorphism) - method overloading

    public abstract void brake();  // Dynamic polymorphism (run-time polymorphism) - method overriding  
}

class manualcar extends car {

    private int currentGear;

    public manualcar(String brand, String name) {
        super(brand, name);
    }

    @Override
    public void accelerate() {
        if (engineStatus) {
            currentSpeed += 10;
            System.out.println(brand + " " + name + " accelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " cannot accelerate. Engine is off.");
        }
    }

    @Override
    public void accelerate(int increment) {
        if (engineStatus) {
            currentSpeed += increment;
            System.out.println(brand + " " + name + " accelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " cannot accelerate. Engine is off.");
        }
    }

    @Override
    public void brake() {
        if (currentSpeed > 0) {
            currentSpeed -= 10;
            System.out.println(brand + " " + name + " decelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " is already stationary.");
        }
    }

    public void shiftgear(int gear) {
        this.currentGear = gear;
        System.out.println("Manual car gear shifted to " + currentGear + ".");
    }
}

class automaticcar extends car {

    public automaticcar(String brand, String name) {
        super(brand, name);
    }

    @Override
    public void accelerate() {
        if (engineStatus) {
            currentSpeed += 15;
            System.out.println(brand + " " + name + " accelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " cannot accelerate. Engine is off.");
        }
    }

    @Override
    public void accelerate(int increment) {
        if (engineStatus) {
            currentSpeed += increment;
            System.out.println(brand + " " + name + " accelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " cannot accelerate. Engine is off.");
        }
    }

    @Override
    public void brake() {
        if (currentSpeed > 0) {
            currentSpeed -= 15;
            System.out.println(brand + " " + name + " decelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(brand + " " + name + " is already stationary.");
        }
    }

    public void chargebattery() {
        System.out.println(brand + " " + name + " battery charged.");
    }
}
public class polymorphism {
    public static void main(String[] args) {
        car myManualCar = new manualcar("Skoda", "Kushak");
        myManualCar.startengine();
        myManualCar.accelerate();
        myManualCar.accelerate(20);
        myManualCar.brake();
        ((manualcar) myManualCar).shiftgear(2);
        myManualCar.stopengine();

        System.out.println();

        car myAutomaticCar = new automaticcar("Tesla", "Model S");
        myAutomaticCar.startengine();
        myAutomaticCar.accelerate();
        myAutomaticCar.accelerate(30);
        myAutomaticCar.brake();
        ((automaticcar) myAutomaticCar).chargebattery();
        myAutomaticCar.stopengine();
    }
}
