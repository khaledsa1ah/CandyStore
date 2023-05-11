public class Voucher {
    private String code;
    private float discount;

    public Voucher(String code, float discount) {
        this.code = code;
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public float getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return code + " (" + discount + "%)";
    }
}