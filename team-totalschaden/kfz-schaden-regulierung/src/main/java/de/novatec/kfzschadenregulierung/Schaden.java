package de.novatec.kfzschadenregulierung;

public class Schaden {

    private String customerId;
    private String schadenTyp;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSchadenTyp() {
        return schadenTyp;
    }

    public void setSchadenTyp(String schadenTyp) {
        this.schadenTyp = schadenTyp;
    }
}
