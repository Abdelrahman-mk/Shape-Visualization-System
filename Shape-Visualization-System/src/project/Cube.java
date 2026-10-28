package project;

public class Cube extends ThreeDShape {
    private double side;

    
    public Cube() {
        super();
        this.side = 0.0;
    }
    @Override
    public String getname() {
        return "cube";
    }
    public Cube(double side) {
        super();
        if (side < 0) {
            System.out.println("Error: Side length cannot be negative.");
            this.side = 0; 
        } else {
            this.side = side;
        }
    }

    public Cube(String color,double side) {
        super(color);
        this.side = side;
    }


    @Override
    public double getVolume() {
        return side * side * side;
    }

    public String howToDraw() {
        return "Like you draw a box";
    }

    @Override
    public double getArea() {
        return 6 * side * side;
    }

    @Override
    public double getPerimeter() {
        return 12 * side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public String toString() {
        return "Cube [Side=" + side + "], Color = " + getColor() + "]";
    }
}
