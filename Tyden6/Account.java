public class Account {

    private double cash;

    public Account() {
        cash = 0;
    }

    public Account(double cash) {
        this.cash = cash;
    }

    public void deposit(double cash) {
        this.cash += cash;
    }

    public void cashOut(double cash) {
        if (cash >= this.cash) {
            this.cash -= cash;
        }
        else {
            this.cash = 0;
        }
    }

    public double cashBalance() {
        return cash;
    }

}
