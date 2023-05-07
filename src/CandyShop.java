import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class CandyShop {
    private static final String USER_FILE = "users.txt";
    private static final String DELIMITER = ":";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        Catalog catalog = new Catalog();
        ShoppingCart cart = null;

        while (true) {
            if (currentUser == null) {
                System.out.println("Enter 1 to register, 2 to login, or 3 to quit: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    currentUser = register();
                } else if (choice == 2) {
                    currentUser = login();
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Enter 1 to display catalog, 2 to place order, 3 to show account, or 4 to logout: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    catalog.printCatalog();
                }
                else if (choice == 2) {
                    catalog.printCatalog();
                    cart = placeOrder(catalog,currentUser);
                    if (cart != null) {
                        cart.checkout(currentUser);
                        cart = null;
                    }

                } else if (choice == 3) {
                    currentUser.showAccount();
                } else if (choice == 4) {
                    currentUser = null;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        }

        scanner.close();
    }

    private static User register() {
        Scanner scanner = new Scanner(System.in);
        File file = new File(USER_FILE);

        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        System.out.println("Enter your client ID:");
        String clientId = scanner.nextLine();

        System.out.println("Enter your address:");
        String address = scanner.nextLine();

        System.out.println("Enter your phone number:");
        String phone = scanner.nextLine();

        System.out.println("Enter your email address:");
        String email = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(name + DELIMITER + clientId + DELIMITER + address + DELIMITER + phone + DELIMITER + email + DELIMITER + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Registration successful.");
        return new User(name, clientId, address, phone, email, password);
    }

    private static User login() {
        Scanner scanner = new Scanner(System.in);
        File file = new File(USER_FILE);

        System.out.println("Enter your E-mail:");
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(DELIMITER);

                String name = parts[0];
                String clientId = parts[1];
                String address = parts[2];
                String phone = parts[3];
                String email = parts[4];
                String storedPassword = parts[5];

                if (username.equals(email) && password.equals(storedPassword)) {
                    System.out.println("Login successful.");
                    return new User(name, clientId, address, phone, email, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Invalid username or password.");
        return null;
    }

    private static ShoppingCart placeOrder(Catalog catalog, User user) {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();
        Order order = new Order(user, new ArrayList<>(), 0); // create a new order

        while (true) {
            System.out.println("Enter the ID of the candy you want to buy (or enter 0 to finish):");
            int candyId = scanner.nextInt();
            scanner.nextLine();

            if (candyId == 0) {
                break;
            }

            Candy candy = catalog.getCandyById(candyId);

            if (candy == null) {
                System.out.println("Invalid candy ID.");
                continue;
            }

            System.out.println("Enter the quantity you want to buy:");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            order.addCandyToOrder(candy); // add the candy to the order
            cart.addItem(candy, quantity);

            System.out.println(quantity + " " + candy.getName() + "(s) added to cart.");
        }

        if (cart.getTotalPrice() == 0) {
            System.out.println("You did not select any candies.");
            return null;
        }

        // set the total price of the order
        order.setTotalPrice(cart.getTotalPrice());

        // add the order to the user's orders list
        user.addOrder(order);

        return cart;
    }

}