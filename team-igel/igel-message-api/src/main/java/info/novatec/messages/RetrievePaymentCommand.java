package info.novatec.messages;

public class RetrievePaymentCommand {
    private String orderId;
    private String item;
    private int amount;

    public RetrievePaymentCommand(String orderId, String item, int amount) {
        this.orderId = orderId;
        this.item = item;
        this.amount = amount;
    }

    public RetrievePaymentCommand() {
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
