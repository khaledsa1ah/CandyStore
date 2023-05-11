import java.util.List;
import java.util.ArrayList;
/*
public class Order {
    private User user;
    private List<Candy> candies;
    private float totalPrice;

    public Order(User user, List<Candy> candies, float totalPrice) {
        this.user = user;
        this.candies = candies;
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public List<Candy> getCandies() {
        return candies;
    }

    public float getTotalPrice() {
        return totalPrice;
    }


}*/

public class Order {
    private List<Candy> candies = new ArrayList<>();
    private float totalPrice;
    private User user;

    public void setTotalPrice(float totalPrice)
    {
        this.totalPrice=totalPrice;
    }
    public Order(User user, List<Candy> candies, float totalPrice) {
        this.user = user;
        this.candies = candies;
        this.totalPrice = totalPrice;
    }
    public void addCandyToOrder(Candy candy) {
        candies.add(candy);
        totalPrice += candy.getPrice();
    }

    public List<Candy> getCandies() {
        return candies;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}