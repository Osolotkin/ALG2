public class ukol2Library {

    private ukol2Library() {

    }

    public static ukol2 multiplication(ukol2 fract1, ukol2 fract2) {

        ukol2 ans = new ukol2();

        int uP1 = fract1.getUpperPart();
        int bP1 = fract1.getBottomPart();

        int uP2 = fract2.getUpperPart();
        int bP2 = fract2.getBottomPart();

        ans.setUpperPart(uP1*uP2);
        ans.setBottomPart(bP1*bP2);

        return ans;

    }

    public static ukol2 division(ukol2 fract1, ukol2 fract2) {

        int tmp = fract2.getUpperPart();
        fract2.setUpperPart(fract2.getBottomPart());
        fract2.setBottomPart(tmp);

        ukol2 ans = multiplication(fract1,fract2);

        return ans;

    }

    public static ukol2 addition(ukol2 fract1, ukol2 fract2) {

        ukol2 ans = new ukol2();

        int uP1 = fract1.getUpperPart();
        int bP1 = fract1.getBottomPart();

        int uP2 = fract2.getUpperPart();
        int bP2 = fract2.getBottomPart();

        ans.setUpperPart(uP1*bP2 + uP2*bP1);
        ans.setBottomPart(bP2*bP1);

        return ans;

    }

    public static ukol2 subtraction(ukol2 fract1, ukol2 fract2) {

        ukol2 ans = new ukol2();

        fract2.setUpperPart(-fract2.getUpperPart());
        fract2.setBottomPart(-fract2.getBottomPart());
        ans = addition(fract1, fract2);

        return ans;

    }

}
