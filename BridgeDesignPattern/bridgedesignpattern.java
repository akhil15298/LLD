package BridgeDesignPattern;


//Implementor(LLP)
interface Engine {
    void start();
}

class PetrolEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Petrol engine started.");
    }
}

class DieselEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Diesel engine started.");
    }
}

class electricEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Electric engine started.");
    }
}


//Abstraction(HLP)

abstract class car{
    protected Engine engine;
    public car(Engine engine) {
        this.engine = engine;
    }
    public abstract void start();
}

class sedan extends car {
    public sedan(Engine engine) {
        super(engine);
    }
    @Override
    public void start() {
        System.out.print("Driving a sedan on Highway: ");
        engine.start();
    }
}

class suv extends car {
    public suv(Engine engine) {
        super(engine);
    }
    @Override
    public void start() {
        System.out.print("Driving an SUV on Off-Road: ");
        engine.start();
    }
}   

class hatchback extends car {
    public hatchback(Engine engine) {
        super(engine);
    }
    @Override
    public void start() {
        System.out.print("Driving a hatchback in the city: ");
        engine.start();
    }
}


public class bridgedesignpattern {

    public static void main(String[] args) {
        Engine petrolEngine = new PetrolEngine();
        Engine dieselEngine = new DieselEngine();
        Engine electricEngine = new electricEngine();

        car sedanCar = new sedan(petrolEngine);
        car suvCar = new suv(dieselEngine);
        car hatchbackCar = new hatchback(electricEngine);

        sedanCar.start();
        suvCar.start();
        hatchbackCar.start();
    }
    
}
