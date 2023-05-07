public class Candy {
    private int ID;
    private String name;
    private float price;

    public Candy(int ID, String name, float price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}