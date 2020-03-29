import java.text.DecimalFormat;

public class ukol {

    private static double [] polynom = new double [] {1, 2 ,3 ,4};

    public double caculateValueForX(double x) {
        double ans = 0;
        for (int i = 0; i < polynom.length; i++) {
            ans += polynom[i]*Math.pow(x,i);
        }
        return ans;
    }

    public double calculateIntegralOnInterval(double a, double b, double accuracy) {

        if (accuracy < 0) accuracy = 0;
        double sum = 0;
        double delta = accuracy;
        while (a + delta <= b ) {
            sum += ( caculateValueForX(a + delta)  + caculateValueForX(a) )/2*delta;
            a += delta;
        }
        return sum;

    }

    @Override
    public String toString() {

        String ans = "";
        DecimalFormat df = new DecimalFormat("###.#");
        for (int i = polynom.length - 1; i > 0; i--) {
            ans += "" + df.format(polynom[i]) + "x^" + i + " + ";
        }
        ans += df.format(polynom[0]);

        return ans;

    }

}
