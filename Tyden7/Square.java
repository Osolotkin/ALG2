public class Square extends Shape {

    private double a;

    public Square(double a) {
        this.a = a;
    }

    public double area() {
        return a*a;
    }

    public boolean isAreaBigger(Shape o) {
        return this.area() > o.area();
    }

    public String getName() {
        return this.getClass().getName();
    }

}
