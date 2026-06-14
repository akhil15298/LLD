package Encapsulation;

class car{
    private String brand;
    private String name;
    private boolean engineStatus;
    private int currentGear;
    private int currentSpeed;

    private String tyrecompany;

    public String getTyrecompany() {
        return tyrecompany;
    }

    public void setTyrecompany(String tyrecompany) {
        this.tyrecompany = tyrecompany;
    }
    
    public car(String brand, String name){
        this.brand = brand;
        this.name = name;
        this.engineStatus = false;
        this.currentGear = 0;
        this.currentSpeed = 0;
    }

    public void startengine() {
        engineStatus = true;
        System.out.println(brand + " " + name + " engine started.");
    }
    public void stopengine() {
        engineStatus = false;
        currentSpeed = 0;
        currentGear = 0;
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

    public void shiftgear() {
        if (engineStatus) {
            currentGear++;
            System.out.println(brand + " " + name + " shifted to gear " + currentGear + ".");
        } else {
            System.out.println(brand + " " + name + " cannot shift gears. Engine is off.");
        }
    }
}

public class encapsulation {

    public static void main(String[] args) {
        car myCar = new car("Skoda", "Kushak");
        myCar.setTyrecompany("MRF");

        System.out.println("Tyre company: " + myCar.getTyrecompany());

        myCar.startengine();
        myCar.accelerate();
        myCar.shiftgear();
        myCar.accelerate();
        myCar.shiftgear();
        myCar.brake();
        myCar.stopengine();
    }
    
}
