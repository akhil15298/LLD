package SOLID_PRINCIPLES.InterfaceSegrigationPrinciple;

interface twodshape{
    void area();
}

interface threedshape{
    void volume();
    void area();
}

class square implements twodshape{
    private double side;

    public square(double side) {
        this.side = side;
    }

    @Override
    public void area() {
        System.out.println("Area of square: " + (side * side));
    }
}

class Rectangle implements twodshape{
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public void area() {
        System.out.println("Area of rectangle: " + (length * width));
    }
}


class cube implements threedshape{
    private double side;

    public cube(double side) {
        this.side = side;
    }

    @Override
    public void volume() {
        System.out.println("Volume of cube: " + (side * side * side));
    }

    @Override
    public void area() {
        System.out.println("Surface area of cube: " + 6 * (side * side));
    }
}



public class interfacesegrigationprinciple {

    public static void main(String[] args) {
        twodshape square = new square(5);
        square.area();

        twodshape rectangle = new Rectangle(4, 6);
        rectangle.area();

        threedshape cube = new cube(3);
        cube.volume();
        cube.area();
    }
    
}
