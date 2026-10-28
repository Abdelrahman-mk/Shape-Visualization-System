package project;
public class circle extends Shape {

    private double radius;

   
    public circle() {
        super();
        this.radius = 0.0;
    }
    
    @Override
    public String getname() {
        return "circle";
    }
    public circle(double radius) {
        super();
        if (radius < 0) {
            System.out.println("Error: Radius cannot be negative.");
            this.radius = 0;
        } else {
            this.radius = radius;
        }
    }

    public circle(double radius, String color) {
        super(color);
        this.radius = radius;
    }


    @Override
    public String howToDraw() {
        return "Like you draw a ball";
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String toString() {
        return "Circle [radius=" + radius + "], Color = " + getColor() + "]";
    }
}

