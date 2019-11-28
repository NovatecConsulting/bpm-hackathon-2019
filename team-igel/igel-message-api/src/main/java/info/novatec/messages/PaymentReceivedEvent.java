package info.novatec.messages;

public class PaymentReceivedEvent {
    private String orderId;
    private String item;
    private int amount;
    private double price;

    public PaymentReceivedEvent(String orderId, String item, int amount, double price) {
        this.orderId = orderId;
        this.item = item;
        this.amount = amount;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PaymentReceivedEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
