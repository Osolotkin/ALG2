public class BankMain {

    public static void main(String args[]) {

        Client [] clients = {new Person("Pekar"), new Person("Svecova"), new Company("Skoda")};

        clients[0].addAccount(1324,1000);
        clients[0].addAccount(1325,500);
        clients[1].addAccount(1424,1200);
        clients[2].addAccount(1524,120);

        System.out.println(clients[0].addressing() + ", částka na učtech: " + clients[0].allAccountsBalance());
        System.out.println(clients[1].addressing() + ", částka na učtech: " + clients[1].allAccountsBalance());
        System.out.println(clients[2].addressing() + ", částka na učtech: " + clients[2].allAccountsBalance());

    }
}
