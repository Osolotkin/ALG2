public class StudentsComparing {

    public static void main(String [] args) {

        Student [] std = {
                new Student("Bob", "Her", 1998, 1489, new int [] {1,2,3}),
                new Student("Bobek", "Itman", 1997, 1458, new int [] {5,2,3}),
                new Student("Axle", "Jud", 2000, 1467, new int [] {1,1,2})
        };


        sortStudents(std,"name");
        System.out.println(toString(std));
        sortStudents(std,"number");
        System.out.println(toString(std));
        sortStudents(std,"age");
        System.out.println(toString(std));
        sortStudents(std,"average");
        System.out.println(toString(std));


    }

    private static void sortStudents(Student[] array, String var){
        for(int i = 0; i < array.length; i++) {
            for(int j = 1; j < array.length; j++) {
                boolean exchange = false;
                if (var.equals("name")) {
                    if(!array[j-1].isLexicographicFirst(array[j])) {
                        exchange = true;
                    }
                } else if (var.equals("number")) {
                    if(!array[j-1].hasHigherNumber(array[j])) {
                        exchange = true;
                    }
                } else if (var.equals("age")) {
                    if(array[j-1].isOlder(array[j])) {
                        exchange = true;
                    }
                } else if (var.equals("average")) {
                    if(array[j-1].isAverageHigher(array[j])) {
                        exchange = true;
                    }
                }
                if(exchange){
                    Student tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                }
            }
        }
    }

    private static String toString(Student[] array) {
        String ans = "";
        for (Student student : array) {
            ans += "first name = " + student.getFirstName() +
                    " last name = " + student.getLastName() +
                    " age of birth = " + student.getAgeOfBirth() +
                    " number = " + student.getNumber() +
                    " average = " + student.getAverage() + "\n";
        }
        return ans;
    }

}
