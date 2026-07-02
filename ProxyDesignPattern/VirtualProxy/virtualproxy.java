package ProxyDesignPattern.VirtualProxy;

interface IDisplay{
    void display();
}

class RealImage implements IDisplay{
    private String fileName;
    public RealImage(String fileName){
        this.fileName = fileName;
        loadFromDisk();
    }

    private void loadFromDisk(){
        System.out.println("Loading from disk" + fileName);
    }

    @Override
    public void display() {
        System.out.println("Displaying " + fileName);
    }
}

class imageproxy implements IDisplay{
    private RealImage realImage;
    private String fileName;

    public imageproxy(String fileName){
        this.fileName = fileName;
        this.realImage = null;
    }

    @Override
    public void display() {
        if(realImage == null){
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}

public class virtualproxy {
    public static void main(String[] args) {
        IDisplay image1 = new imageproxy("image1.jpg");
        IDisplay image2 = new imageproxy("image2.jpg");

        image1.display();
        System.out.println();
        image2.display();
        System.out.println();
        image1.display();
    }
    
}
