public class Circle extends Shape {

    private double r;

    public Circle(double r) {
        this.r = r;
    }

    public double area() {
        return Math.PI*r*r;
    }

    public boolean isAreaBigger(Shape o) {
        return this.area() > o.area();
    }

    public String getName() {
        return this.getClass().getName();
    }

}
