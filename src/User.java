import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String clientId;
    private String address;
    private String phone;
    private String email;
    private String password;
    private int loyaltyPoints;
    private List<Voucher> vouchers;
    private List<Order> orders;

    public User(String name, String clientId, String address, String phone, String email, String password) {
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

    /*public void showAccount() {
        System.out.println("Name: " + name);
        System.out.println("Client ID: " + clientId);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("Vouchers:");
        vouchers.forEach(System.out::println);
        System.out.println("Orders:");
        orders.forEach(order -> {
            System.out.println("Order ID: " + order.hashCode());
            System.out.println("Total price: " + order.getTotalPrice());
            System.out.println("Candies:");
            order.getCandies().forEach(System.out::println);
        });
    }*/
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
}