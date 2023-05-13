import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingCart {
    private List<Candy> candies;
    private float totalPrice;

    public ShoppingCart() {
        candies = new ArrayList<>();
        totalPrice = 0;
    }

    public void addItem(Candy candy, float quantity) {
        candies.add(candy);
        totalPrice += candy.getPrice() * quantity;
    }

    public void removeItem(Candy candy) {
        candies.remove(candy);
        totalPrice -= candy.getPrice();
    }

    public void redeemLoyaltyPoints(Client client) {
        if (client.getLoyaltyPoints() >= 100) {
            client.setLoyaltyPoints(client.getLoyaltyPoints() - 100);
            totalPrice -= 10;
        }
        else {
            System.out.println("You don't have enough loyalty points to redeem.");
        }
    }

    public void redeemVoucher(Client client, String code) {
        for (Voucher voucher : client.getVouchers()) {
            if (voucher.getCode().equals(code)) {
                client.removeVoucher(voucher);
                totalPrice *= 1 - voucher.getDiscount() / 100;
                System.out.println("Voucher " + voucher.getCode() + " redeemed.");
                return;
            }
        }

        System.out.println("Voucher not found or already used.");
    }

    public void paymentMethod(String method) {
        System.out.println("Payment method: " + method);
    }

    public List<Candy> getItems() {
        return candies;
    }

    public float getTotalPrice(){return totalPrice;}

    public void clear() {
        candies.clear();
        totalPrice = 0;
    }
    public void checkout(Client client) {

        System.out.println("Order placed. Total price: " + totalPrice + " LE.");
        System.out.println("Thank you for your purchase!");

        clear();
    }

    public static ShoppingCart placeOrder(Catalog catalog, Client client) {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();
        Order order = new Order(client, new ArrayList<>(), 0); // create a new order

        while (true) {
            System.out.println("Enter the ID of the candy you want to buy (or enter 0 to finish, or -1 to remove a candy):");
            int candyId = scanner.nextInt();
            scanner.nextLine();

            if (candyId == 0) {
                break;
            } else if (candyId == -1) {
                System.out.println("Enter the ID of the candy you want to remove:");
                int removeId = scanner.nextInt();
                scanner.nextLine();

                Candy removeCandy = catalog.getCandyById(removeId);
                if (removeCandy == null) {
                    System.out.println("Invalid candy ID.");
                    continue;
                } else if (!cart.getItems().contains(removeCandy)) {
                    System.out.println("The candy is not in your cart.");
                    continue;
                }

                cart.removeItem(removeCandy);
                System.out.println(removeCandy.getName() + " removed from cart.");
                continue;
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
        client.addOrder(order);

        return cart;
    }

}