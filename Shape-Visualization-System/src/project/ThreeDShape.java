package project;

public abstract class ThreeDShape extends Shape {

    public abstract double getVolume();

    public ThreeDShape() {
        super();
    }

    public ThreeDShape(String color) {
        super(color);
    }
}
