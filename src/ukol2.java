public class ukol2 {

    private int upperPart;
    private int bottomPart;

    public ukol2() {
        upperPart = 0;
        bottomPart = 1;
    }

    public ukol2(int upperPart, int bottomPart) {

        this.upperPart = upperPart;
        if (bottomPart != 0) {
            this.bottomPart = bottomPart;
        } else {
            this.bottomPart = 1;
        }

    }

    public void simplifyFract() {

        int maxDiv;
        if (upperPart > bottomPart) {
            maxDiv = mrEuclides(upperPart, bottomPart);
        } else {
            maxDiv = mrEuclides(bottomPart, upperPart);
        }
        upperPart /= maxDiv;
        bottomPart /= maxDiv;

    }

    private int mrEuclides(int a, int b) {
        while (a % b > 0) {
            a = b;
            b = a % b;
        }
        return b;
    }

    @Override
    public String toString() {
        if (bottomPart != 1) {
            return upperPart + "/" + bottomPart;
        } else {
            return "" + upperPart;
        }
    }

    public int getUpperPart() {
        return upperPart;
    }

    public int getBottomPart() {
        return bottomPart;
    }

    public void setBottomPart(int bottomPart) {
        if (bottomPart != 0) {
            this.bottomPart = bottomPart;
        } else {
            this.bottomPart = 1;
        }
    }

    public void setUpperPart(int upperPart) {
        this.upperPart = upperPart;
    }
}
