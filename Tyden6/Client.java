public abstract class Client {

    private String name;
    private int [] accountID = new int [1];
    private int [] accountBalance = new int [1];

    public Client(String name) {

        this.name = name;

    }

    public Client(String name, int [] accountID, int [] accountBalance) {

        this.name = name;
        this.accountID = accountID;
        this.accountBalance = accountBalance;

    }

    public Client(String name, int accountID, int accountBalance) {

        this.name = name;
        this.accountID[0] = accountID;
        this.accountBalance[0] = accountBalance;

    }

    public void addAccount(int accountID, int accountBalance) {

        int [] tmpA = this.accountID.clone();
        int [] tmpB = this.accountBalance.clone();
        this.accountID = new int [tmpA.length + 1];
        this.accountBalance = new int [tmpB.length + 1];

        for (int i = 0; i < tmpA.length; i++) {
            this.accountID[i] = tmpA[i];
            this.accountBalance[i] = tmpB[i];
        }
        this.accountID[tmpA.length] = accountID;
        this.accountBalance[tmpB.length] = accountBalance;

    }

    public double allAccountsBalance() {

        double ans = 0;

        for (int i = 0; i < accountBalance.length; i++) {
            ans += accountBalance[i];
        }

        return ans;

    }

    public String getName() {
        return name;
    }

    public abstract String addressing();

}
