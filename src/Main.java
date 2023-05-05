import java.util.ArrayList;

class Candy {
    private String name;
    private String id;
    private float price;
    private int quantity;
    private String description;
    private String type;

    public Candy(String name, String id, float price, int quantity, String description, String type) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.type = type;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    // Setter methods
    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void printCandy() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + quantity);
        System.out.println("Description: " + description);
        System.out.println("Type: " + type);
    }
}

class ShoppingCart {
    private ArrayList<Candy> candies;
    private float totalPrice;

    public ShoppingCart() {
        candies = new ArrayList<>();
        totalPrice = 0;
    }

    public void addCandy(Candy candy) {
        candies.add(candy);
        totalPrice += candy.getPrice();
    }

    public void removeCandy(Candy candy) {
        for (int i = 0; i < candies.size(); i++) {
            if (candies.get(i).getId().equals(candy.getId())) {
                candies.remove(i);
                totalPrice -= candy.getPrice();
                break;
            }
        }
    }

    public void editQuantity(Candy candy, int newQuantity) {
        for (int i = 0; i < candies.size(); i++) {
            if (candies.get(i).getId().equals(candy.getId())) {
                totalPrice -= candies.get(i).getPrice() * candies.get(i).getQuantity();
                candies.get(i).setQuantity(newQuantity);
                totalPrice += candies.get(i).getPrice() * newQuantity;
                break;
            }
        }
    }

    public void viewCart() {
        for (Candy candy: candies) {
            candy.printCandy();
        }
        System.out.println("Total Price: " + totalPrice);
    }

    public float getTotalPrice() { return totalPrice; }

    public void redeemLoyaltyPoints() {
        // TODO: Implement this method
        System.out.println("Loyalty points redeemed");
    }

    public void redeemVoucher() {
        // TODO: Implement this method
        System.out.println("Voucher redeemed");
    }

    public void choosePaymentMethod() {
        // TODO: Implement this method
        System.out.println("Payment method chosen");
    }

    public void checkout() {
        // TODO: Implement this method
        System.out.println("Checkout complete");
    }

    public ArrayList<Candy> getCandies() {
        return candies;
    }
}

class Order {
    private String id;
    private String clientId;
    private ArrayList<Candy> candies;
    private float totalPrice;
    private String paymentMethod;

    public Order(String id, String clientId, ArrayList<Candy> candies, float totalPrice, String paymentMethod) {
        this.id = id;
        this.clientId = clientId;
        this.candies = candies;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    // Getter methods
    public String getId() { return id; }
    public String getClientId() { return clientId; }
    public ArrayList<Candy> getCandies() { return candies; }
    public float getTotalPrice() { return totalPrice; }
    public String getPaymentMethod() { return paymentMethod; }

    // Setter methods
    public void setClientId(String clientId) { this.clientId = clientId; }
    public void setCandies(ArrayList<Candy> candies) { this.candies = candies; }
    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public void printOrder() {
        System.out.println("Order ID: " + id);
        System.out.println("Client ID: " + clientId);
        System.out.println("Candies: ");
        for (Candy candy : candies) {
            candy.printCandy();
        }
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Payment Method: " + paymentMethod);
    }
}

public class Main {
    public static void main(String[] args) {
        Candy candy1 = new Candy("Snickers", "1234", 1.50f, 10, "A chocolate bar with peanuts and caramel", "Chocolate");
        Candy candy2 = new Candy("Skittles", "5678", 1.00f, 20, "Fruit-flavored chewy candies", "Candy");
        ShoppingCart cart = new ShoppingCart();
        cart.addCandy(candy1);
        cart.addCandy(candy2);
        System.out.println("Shopping Cart:");
        cart.viewCart();
        System.out.println();
        Order order = new Order("1001", "john_doe", cart.getCandies(), cart.getTotalPrice(), "Credit Card");
        System.out.println("Order Details:");
        order.printOrder();
    }
}