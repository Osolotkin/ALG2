public class Company extends Client {

    public Company(String name) {
        super(name);
    }

    public String addressing() {
        return "firma " + super.getName();
    }

}
