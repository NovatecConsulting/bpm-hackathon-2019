package de.novatec.schadenbuchen.flow;

public class BankDetails {

    public BankDetails(String customerId, String iban, String bankcode) {
        this.customerId = customerId;
        this.iban = iban;
        this.bankcode = bankcode;
    }

    private String customerId;
    private String iban;
    private String bankcode;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }




}
