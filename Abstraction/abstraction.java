package Abstraction;

interface car{
    void startengine();
    void stopengine();
    void accelerate();
    void brake();   
    void shiftgear();
}


class sportscar implements car{
    String brand;
    String name;
    boolean engineStatus;
    int currentGear;
    int currentSpeed;
    
    public sportscar(String brand, String name){
        this.brand = brand;
        this.name = name;
        this.engineStatus = false;
        this.currentGear = 0;
        this.currentSpeed = 0;
    }

    @Override
    public void startengine() {
        engineStatus = true;
        System.out.println(name + " engine started.");
    }
    @Override
    public void stopengine() {
        engineStatus = false;
        currentSpeed = 0;
        currentGear = 0;
        System.out.println(name + " engine stopped.");
    }
    @Override
    public void accelerate() {
        if (engineStatus) {
            currentSpeed += 10;
            System.out.println(name + " accelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(name + " cannot accelerate. Engine is off.");
        }
    }
    @Override
    public void brake() {
        if (currentSpeed > 0) {
            currentSpeed -= 10;
            System.out.println(name + " decelerated to " + currentSpeed + " km/h.");
        } else {
            System.out.println(name + " is already stationary.");
        }
    }

    @Override
    public void shiftgear() {
        if (engineStatus) {
            currentGear = (currentGear + 1) % 6; // Assuming 5 gears + neutral
            System.out.println(name + " shifted to gear " + currentGear + ".");
        } else {
            System.out.println(name + " cannot shift gear. Engine is off.");
        }
    }
}
class abstraction {
    public static void main(String[] args) {
        car myCar = new sportscar("Ferrari", "F8 Tributo");
        
        myCar.startengine();
        myCar.accelerate();
        myCar.shiftgear();
        myCar.shiftgear();
        myCar.accelerate();
        myCar.shiftgear();
        myCar.brake();
        myCar.stopengine();
    }
}