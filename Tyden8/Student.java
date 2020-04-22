public class Student {

    private String firstName;
    private String lastName;
    private int number;
    private int ageOfBirth;
    private int [] grades;
    private double average;

    public Student(String firstName, String lastName, int ageOfBirth, int number, int [] grades) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ageOfBirth = ageOfBirth;
        this.number = number;
        this.grades = grades.clone();
        this.average = averageOfGrades(this.grades);
    }

    private double averageOfGrades(int [] grades) {

        double sum = 0;
        for (int i = 0; i < grades.length; i++){
            sum += grades[i];
        }
        return sum/grades.length;

    }

    boolean isAverageHigher(Student student) {

        double eps = 0.001;
        if (Math.abs(this.average - student.average) > eps) {
            return this.average > student.average;
        } else {
            return false;
        }

    }

    boolean isOlder(Student student) {
        return this.ageOfBirth > student.ageOfBirth;
    }

    boolean hasHigherNumber(Student student) {
        return this.number > student.number;
    }

    boolean isLexicographicFirst(Student student) {

        String frNm = this.firstName + " " + this.lastName;
        String lsNm = student.firstName + " " + student.lastName;
        String tmp;

        if (lsNm.length() > frNm.length()) {
            tmp = frNm;
        } else {
            tmp = lsNm;
        }

        for (int i = 0; i <= tmp.length(); i++) {
            if (frNm.charAt(0) < lsNm.charAt(0)) {
                return true;
            } else if (frNm.charAt(0) > lsNm.charAt(0)) {
                return false;
            }
        }

        if (frNm.length() < lsNm.length()) {
            return true;
        } else {
            return false;
        }

    }

    public int getAgeOfBirth() {
        return ageOfBirth;
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

    public double getAverage() {
        return average;
    }
}