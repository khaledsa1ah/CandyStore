import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private String name;
    private String clientId;
    private String address;
    private String phone;
    private String email;
    private String password;
    private int loyaltyPoints;
    private List<Voucher> vouchers;
    private List<Order> orders;

    public Client(String name, String clientId, String address, String phone, String email, String password) {
        this.name = name;
        this.clientId = clientId;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.loyaltyPoints = 0;
        this.vouchers = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

    public void removeVoucher(Voucher voucher) {
        vouchers.remove(voucher);
    }

    public void showAccount() {
        System.out.println("Name: " + name);
        System.out.println("Client ID: " + clientId);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("Vouchers:");
        vouchers.forEach(System.out::println);
        System.out.println("Orders:");

        if (orders.isEmpty()) {
            System.out.println("No orders.");
        } else {
            for (Order order : orders) {
                System.out.println("Order ID: " + order.hashCode());

                System.out.println("Total price: " + order.getTotalPrice());

                System.out.println("Candies:");
                for (Candy candy : order.getCandies()) {
                    System.out.println("- " + candy.getName());
                }

                System.out.println("Status: " + order.getStatus());
            }
        }
    }

    public void editData(String name, String address, String phone, String email, String password) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public boolean isRegistered() {
        return true;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    private static final String USER_FILE = "users.txt";
    private static final String READ_DELIMITER = "\\|";
    private static final String WRITE_DELIMITER = "|";



    public static Client register() {
        Scanner scanner = new Scanner(System.in);
        File file = new File(USER_FILE);

// Validate name
        String name;
        do {
            System.out.println("Enter your name:");
            name = scanner.nextLine().trim();
            if (!name.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("Invalid input. Name should contain only letters and spaces.");
            }
        } while (!name.matches("^[a-zA-Z\\s]+$"));


        String clientId;
        boolean isClientIdValid = false;

        do {
            System.out.println("Enter your client ID:");
            clientId = scanner.nextLine().trim();
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(READ_DELIMITER);
                    if (user[1].equals(clientId)) {
                        System.out.println("Invalid input. Client ID already exists.");
                        break;
                    }
                }
                isClientIdValid = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!isClientIdValid);


        String address;
        do {
            System.out.println("Enter your address:");
            address = scanner.nextLine().trim();
        } while (address.isEmpty());

        String phone;
        boolean isPhoneValid = false;
        do {
            System.out.println("Enter your phone number (11 digits, starts with 010, 011, 012, or 015):");
            phone = scanner.nextLine().trim();
            if (phone.matches("^01[0125][0-9]{8}$")) {
                isPhoneValid = true;
            } else {
                System.out.println("Invalid input. Phone number should be 11 digits and start with 010, 011, 012, or 015.");
            }
        } while (!isPhoneValid);

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
                            System.out.println("Email address already exists. Please enter a unique email address.");
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

        // Verify email using OTP
        String otp = OTP.generateOTP();
        new OTP().sendEmail(email, "toffeeshop0@gmail.com", "Toffee Store OTP", "Your OTP is: " + otp);

        System.out.println("Please enter the OTP sent to your email address: ");
        String inputOTP = scanner.nextLine().trim();

        while (!otp.equals(inputOTP)) {
            System.out.println("Invalid OTP. Please enter the OTP sent to your email address: ");
            inputOTP = scanner.nextLine().trim();
        }

        // Validate password
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
        password = Security.encryptPassword(password);


        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(name + WRITE_DELIMITER + clientId + WRITE_DELIMITER + address + WRITE_DELIMITER + phone + WRITE_DELIMITER + email + WRITE_DELIMITER + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Registration successful.");
        return new Client(name, clientId, address, phone, email, password);
    }



    public static Client login() {

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
                    if (password.equals(Security.decryptPassword(storedPassword))) {
                        System.out.println("Login successful.");
                        return new Client(name, clientId, address, phone, storedEmail, storedPassword);
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
}