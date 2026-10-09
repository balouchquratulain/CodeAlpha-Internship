import java.util.ArrayList;
import java.util.Scanner;

// Stock class manages market stocks
class Stock {
    String symbol;
    String companyName;
    double price;

    Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    void displayStock() {
        System.out.printf("%-10s %-20s Rs. %.2f%n",
                symbol, companyName, price);
    }
}

// Transaction class stores buy and sell records
class Transaction {
    String type;
    String symbol;
    int quantity;
    double price;

    Transaction(String type, String symbol,
                int quantity, double price) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    void displayTransaction() {
        System.out.printf("%-8s %-8s %-10d Rs. %.2f%n",
                type, symbol, quantity, price);
    }
}

// User class manages cash and portfolio
class User {
    String name;
    double balance = 100000.0;

    ArrayList<Stock> portfolio = new ArrayList<>();
    ArrayList<Integer> quantities = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<>();

    User(String name) {
        this.name = name;
    }

    int findStock(String symbol) {
        for (int i = 0; i < portfolio.size(); i++) {
            if (portfolio.get(i).symbol.equalsIgnoreCase(symbol)) {
                return i;
            }
        }
        return -1;
    }

    void buyStock(Stock stock, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        double cost = stock.price * quantity;

        if (cost > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= cost;
        int index = findStock(stock.symbol);

        if (index == -1) {
            portfolio.add(stock);
            quantities.add(quantity);
        } else {
            quantities.set(index, quantities.get(index) + quantity);
        }

        transactions.add(new Transaction(
                "BUY", stock.symbol, quantity, stock.price));

        System.out.println("Stock purchased successfully!");
        System.out.printf("Total cost: Rs. %.2f%n", cost);
    }

    void sellStock(Stock stock, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        int index = findStock(stock.symbol);

        if (index == -1 || quantities.get(index) < quantity) {
            System.out.println("You do not own enough shares!");
            return;
        }

        double amount = stock.price * quantity;
        balance += amount;

        quantities.set(index, quantities.get(index) - quantity);

        if (quantities.get(index) == 0) {
            portfolio.remove(index);
            quantities.remove(index);
        }

        transactions.add(new Transaction(
                "SELL", stock.symbol, quantity, stock.price));

        System.out.println("Stock sold successfully!");
        System.out.printf("Amount received: Rs. %.2f%n", amount);
    }

    void showPortfolio() {
        System.out.println("\n===== YOUR PORTFOLIO =====");

        if (portfolio.isEmpty()) {
            System.out.println("You do not own any stocks.");
        } else {
            System.out.printf("%-10s %-10s %-15s%n",
                    "Symbol", "Quantity", "Market Value");

            for (int i = 0; i < portfolio.size(); i++) {
                Stock stock = portfolio.get(i);
                int quantity = quantities.get(i);

                System.out.printf("%-10s %-10d Rs. %-12.2f%n",
                        stock.symbol, quantity,
                        stock.price * quantity);
            }
        }

        System.out.printf("Available Cash: Rs. %.2f%n", balance);
        System.out.printf("Portfolio Value: Rs. %.2f%n",
                getPortfolioValue());
        System.out.printf("Total Assets: Rs. %.2f%n",
                balance + getPortfolioValue());
    }

    double getPortfolioValue() {
        double total = 0;

        for (int i = 0; i < portfolio.size(); i++) {
            total += portfolio.get(i).price * quantities.get(i);
        }

        return total;
    }

    void showTransactions() {
        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction t : transactions) {
            t.displayTransaction();
        }
    }
}

// Main trading platform
public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Stock> market = new ArrayList<>();

    static Stock findMarketStock(String symbol) {
        for (Stock stock : market) {
            if (stock.symbol.equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid whole number: ");
            }
        }
    }

    static void showMarket() {
        System.out.println("\n===== STOCK MARKET =====");
        System.out.printf("%-10s %-20s %s%n",
                "Symbol", "Company", "Price");

        for (Stock stock : market) {
            stock.displayStock();
        }
    }

    static void buy(User user) {
        showMarket();

        System.out.print("Enter stock symbol to buy: ");
        String symbol = sc.nextLine().trim();

        Stock stock = findMarketStock(symbol);

        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = readInt();

        user.buyStock(stock, quantity);
    }

    static void sell(User user) {
        showMarket();

        System.out.print("Enter stock symbol to sell: ");
        String symbol = sc.nextLine().trim();

        Stock stock = findMarketStock(symbol);

        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = readInt();

        user.sellStock(stock, quantity);
    }

    public static void main(String[] args) {

        market.add(new Stock("AAPL", "Apple", 200));
        market.add(new Stock("GOOGL", "Google", 170));
        market.add(new Stock("MSFT", "Microsoft", 420));
        market.add(new Stock("AMZN", "Amazon", 190));
        market.add(new Stock("TSLA", "Tesla", 250));

        System.out.println("===== STOCK TRADING PLATFORM =====");
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            name = "Investor";
        }

        User user = new User(name);

        while (true) {
            System.out.println("\nWelcome, " + user.name + "!");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    showMarket();
                    break;
                case 2:
                    buy(user);
                    break;
                case 3:
                    sell(user);
                    break;
                case 4:
                    user.showPortfolio();
                    break;
                case 5:
                    user.showTransactions();
                    break;
                case 6:
                    System.out.println("Thank you for using the platform!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
