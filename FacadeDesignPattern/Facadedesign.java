package FacadeDesignPattern;


class powersupply{
    public void supplyPower() {
        System.out.println("Power supply is ON");
    }
}

class processor{
    public void process() {
        System.out.println("Processor is processing");
    }
}

class memory{
    public void load() {
        System.out.println("Memory is loading");
    }
}

class cpu{
    public void execute() {
        System.out.println("CPU is executing");
    }
}

class coolingsystem{
    public void cool() {
        System.out.println("Cooling system is cooling");
    }
}

class bios{
    public void boot(cpu cpu, memory memory) {
        System.out.println("BIOS is booting");
        cpu.execute();
        memory.load();
    }
}

class os{
    public void start() {
        System.out.println("Operating System is starting");
    }
}

class computerFacade {
    private powersupply powerSupply;
    private processor processor;
    private memory memory;
    private cpu cpu;
    private coolingsystem coolingSystem;
    private bios bios;
    private os operatingSystem;

    public computerFacade() {
        this.powerSupply = new powersupply();
        this.processor = new processor();
        this.memory = new memory();
        this.cpu = new cpu();
        this.coolingSystem = new coolingsystem();
        this.bios = new bios();
        this.operatingSystem = new os();
    }

    public void startComputer() {
        powerSupply.supplyPower();
        processor.process();
        memory.load();
        coolingSystem.cool();
        bios.boot(cpu, memory);
        operatingSystem.start();
    }
}
public class Facadedesign {
    
    public static void main(String[] args) {
        computerFacade computer = new computerFacade();
        computer.startComputer();
    }
}
