import java.util.List;
import java.util.ArrayList;

public class Order {
    private List<Candy> candies = new ArrayList<>();
    private float totalPrice;
    private Client client;

    private String status;


    public void setTotalPrice(float totalPrice)
    {
        this.totalPrice=totalPrice;
    }
    public Order(Client client, List<Candy> candies, float totalPrice) {
        this.client = client;
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

    public void setStatus(String status)
    {
        this.status=status;
    }
    public String getStatus()
    {
     return "Created";
    }

}