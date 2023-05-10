import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;


public class CandyShop {
    private static final String USER_FILE = "users.txt";
    private static final String READ_DELIMITER = "\\|";
    private static final String WRITE_DELIMITER = "|";

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
                } else if (choice == 2) {
                    catalog.printCatalog();
                    cart = placeOrder(catalog, currentUser);
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

        // Validate name
        String name = "";
        boolean isNameValid = false;
        while (!isNameValid) {
            System.out.println("Enter your name:");
            name = scanner.nextLine().trim();
            if (name.matches("^[a-zA-Z\\s]+$")) {
                isNameValid = true;
            } else {
                System.out.println("Invalid input. Name should contain only letters and spaces.");
            }
        }

        String clientId = "";
        boolean isClientIdValid = false;
        while (!isClientIdValid) {
            System.out.println("Enter your client ID:");
            clientId = scanner.nextLine().trim();
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(READ_DELIMITER);
                    if (user[1].equals(clientId)) {
                        System.out.println("Invalid input. Client ID already exists.");
                        isClientIdValid = false;
                        break;
                    } else {
                        isClientIdValid = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String address = "";
        while (address.isEmpty()) {
            System.out.println("Enter your address:");
            address = scanner.nextLine().trim();
        }

        String phone = "";
        boolean isPhoneValid = false;
        while (!isPhoneValid) {
            System.out.println("Enter your phone number (11 digits, starts with 010, 011, 012, or 015):");
            phone = scanner.nextLine().trim();
            if (phone.matches("^01[0125][0-9]{8}$")) {
                isPhoneValid = true;
            } else {
                System.out.println("Invalid input. Phone number should be 11 digits and start with 010, 011, 012, or 015.");
            }
        }

        // Validate email
        String email = "";
        boolean isEmailValid = false;
        boolean isEmailUnique = true;
        while (!isEmailValid || !isEmailUnique) {
            System.out.println("Enter your email address:");
            email = scanner.nextLine().trim();
            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                isEmailValid = true;
                // Check if email is unique
                try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] user = line.split(READ_DELIMITER);
                        if (user[4].equals(email)) {
                            System.out.println("\"Email address already exists. Please enter a unique email address.");
                            isEmailUnique = false;
                            break;
                        } else {
                            isEmailUnique = true;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid input. Email address should be in the format of username@domain.com.");
            }
        }

        String password = "";
        boolean isPasswordStrong = false;
        while (!isPasswordStrong) {
            System.out.println("Enter your password (at least 8 characters, containing letters, numbers, and symbols):");
            password = scanner.nextLine().trim();
            if (password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
                isPasswordStrong = true;
            } else {
                System.out.println("Invalid input. Password should contain at least 8 characters, including letters, numbers, and symbols.");
            }
        }
        //encrypt password using MD5
        password = encryptPassword(password);


        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(name + WRITE_DELIMITER + clientId + WRITE_DELIMITER + address + WRITE_DELIMITER + phone + WRITE_DELIMITER + email + WRITE_DELIMITER + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Registration successful.");
        return new User(name, clientId, address, phone, email, password);
    }

    private static String encryptPassword(String password) {
        int shift = 3;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            char encrypted = (char) (c + shift);
            result.append(encrypted);
        }
        return result.toString();
    }
    private static String decryptPassword(String encryptedPassword) {
        int shift = 3;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < encryptedPassword.length(); i++) {
            char c = encryptedPassword.charAt(i);
            char decrypted = (char) (c - shift);
            result.append(decrypted);
        }
        return result.toString();
    }



    private static User login() {
        Scanner scanner = new Scanner(System.in);
        File file = new File(USER_FILE);

        System.out.println("Enter your E-mail:");
        String email = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(READ_DELIMITER);

                String name = parts[0];
                String clientId = parts[1];
                String address = parts[2];
                String phone = parts[3];
                String storedEmail = parts[4];
                String storedPassword = parts[5];

                if (email.equals(storedEmail)) {
                    if (password.equals(decryptPassword(storedPassword))) {
                        System.out.println("Login successful.");
                        return new User(name, clientId, address, phone, storedEmail, storedPassword);
                    } else {
                        System.out.println("Incorrect password.");
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Invalid email or password.");
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