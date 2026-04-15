package banking;

import banking.service.BankingService;
import banking.ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
            BankingService service = new BankingService();
        ConsoleMenu app = new ConsoleMenu(service);
        
        app.start();
    }
}
