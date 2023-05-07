import java.util.ArrayList;
import java.util.List;

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

    public void redeemLoyaltyPoints(User user) {
        if (user.getLoyaltyPoints() >= 100) {
            user.setLoyaltyPoints(user.getLoyaltyPoints() - 100);
            totalPrice -= 10;
        }
        else {
            System.out.println("You don't have enough loyalty points to redeem.");
        }
    }

    public void redeemVoucher(User user, String code) {
        for (Voucher voucher : user.getVouchers()) {
            if (voucher.getCode().equals(code)) {
                user.removeVoucher(voucher);
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

    /* public Order checkout(User user) {
        Order order = new Order(user, candies, totalPrice);
        System.out.println("Order placed. Total price: " + totalPrice + ".");
        return order;
    }*/
    public List<Candy> getItems() {
        return candies;
    }

    public float getTotalPrice(){return totalPrice;}

    public void clear() {
        candies.clear();
        totalPrice = 0;
    }
    public void checkout(User user) {

        System.out.println("Order placed. Total price: " + totalPrice + ".");
        System.out.println("Thank you for your purchase!");

        clear();
    }
}