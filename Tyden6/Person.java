public class Person extends Client {

    public Person(String name) {
        super(name);
    }

    public String addressing() {
        String ans;
        if (super.getName().endsWith("ova")) {
            ans = "paní " + super.getName();
        } else {
            ans = "pan " + super.getName();
        }
        return ans;
    }

}
