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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.amount;
        result = prime * result + ((this.item == null) ? 0 : this.item.hashCode());
        result = prime * result + ((this.orderId == null) ? 0 : this.orderId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderPlacedEvent other = (OrderPlacedEvent) obj;
        if (this.amount != other.amount)
            return false;
        if (this.item == null) {
            if (other.item != null)
                return false;
        } else if (!this.item.equals(other.item))
            return false;
        if (this.orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!this.orderId.equals(other.orderId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderId='" + orderId + '\'' +
                ", item='" + item + '\'' +
                ", amount=" + amount +
                '}';
    }
}
