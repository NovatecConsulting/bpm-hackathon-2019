package info.novatec.messages;

public class OrderPlacedEvent {

    private String orderId;
    private String item;
    private int amount;

    public OrderPlacedEvent(String orderId, String item, int amount) {
        super();
        this.orderId = orderId;
        this.item = item;
        this.amount = amount;
    }

    public OrderPlacedEvent() {

    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
