import java.text.SimpleDateFormat;

public class Racer implements CompareInterface {

    int number;
    String fullName;
    String firstName;
    String lastName;
    Time startTime;
    Time finishTime;

    public Racer(String firstName, String lastName, int number, int h, int m, int s) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.number = number;
        startTime = new Time(h, m, s);
        finishTime = new Time(0,0,0);

    }

    public Racer(String firstName, String lastName, String number, String h, String m, String s) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.number = Integer.parseInt(number);
        startTime = new Time(Integer.parseInt(h), Integer.parseInt(m), Integer.parseInt(s));
        finishTime = new Time(0,0,0);

    }

    public void setFinishTime(int h, int m, int s) {
        finishTime.setTime(h, m, s);
    }

    public void setFinishTime(String h, String m, String s) {
        finishTime.setTime(Integer.parseInt(h), Integer.parseInt(m), Integer.parseInt(s));
    }

    private class Time {

        int h;
        int m;
        int s;
        String time;

        public Time(int h, int m, int s) {
            setTime(h, m, s);
        }

        public void setTime(int h, int m, int s) {
            this.h = h;
            this.m = m;
            this.s = s;
            time = formatTime(h, m, s);
        }

        private String formatTime(int h, int m, int s) {
            String ans = "";
            if (h / 10 == 0) ans = 0 + "" + h;
            else ans = "" + h;
            if (m / 10 == 0) ans = ans + ":" + 0 + m;
            else ans = ans + ":" + m;
            if (s / 10 == 0) ans = ans + ":" + 0 + s;
            else ans = ans + ":" + s;
            return ans;
        }

        public int delta(Time time) {
            int s = time.s - this.s;
            int m = time.m - this.m;
            int h = time.h - this.h;
            return h*60*60 + m*60 + s;
        }

    }

    public boolean isFaster(CompareInterface o) {

        int thisDelta = this.startTime.delta(finishTime);
        int otherDelta = ((Racer)o).startTime.delta(((Racer) o).finishTime);
        if (thisDelta < otherDelta) return true;
        return false;

    }

    public int getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStartTime() {
        return startTime.time;
    }

    public String getFinishTime() {
        return finishTime.time;
    }

}
