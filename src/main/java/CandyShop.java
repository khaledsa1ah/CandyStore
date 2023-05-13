import java.util.Scanner;

public class CandyShop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client currentClient = null;
        Catalog catalog = new Catalog();
        ShoppingCart cart;

        while (true) {
            if (currentClient == null) {
                System.out.println("Welcome to Toffee Shop!\n" +
                        "Please select from the following options:\n" +
                        "\n" +
                        "1. Register\n" +
                        "2. Login\n" +
                        "3. Exit\n" +
                        "\n" +
                        "Enter your choice (1-3):");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        currentClient = Client.register();
                        break;
                    case 2:
                        currentClient = Client.login();
                        break;
                    case 3:
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } else {
                System.out.println("Welcome to Toffee Shop!\n" +
                        "Please select from the following options:\n" +
                        "\n" +
                        "1. Display catalog\n" +
                        "2. Search item\n" +
                        "3. Place order\n" +
                        "4. Show account\n" +
                        "5. Logout\n" +
                        "6. Exit\n" +
                        "\n" +
                        "Enter your choice (1-6):");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> catalog.printCatalog();
                    case 2 -> {
                        System.out.println("Enter item name:");
                        String itemName = scanner.nextLine();
                        catalog.searchForItem(itemName);
                    }
                    case 3 -> {
                        catalog.printCatalog();
                        cart = ShoppingCart.placeOrder(catalog, currentClient);
                        if (cart != null) {
                            cart.checkout(currentClient);
                        }
                    }
                    case 4 -> currentClient.showAccount();
                    case 5 -> currentClient = null;
                    case 6 -> {
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }
}
