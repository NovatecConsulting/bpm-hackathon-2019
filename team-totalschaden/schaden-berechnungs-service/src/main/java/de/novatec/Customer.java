package de.novatec;

public class Customer {

    public Customer(String name, String customerNo, String contractNo, int contractLevel, Car car) {
        this.name = name;
        this.customerNo = customerNo;
        this.contractNo = contractNo;
        this.contractLevel = contractLevel;
        this.car = car;
    }

    private String name;
    private String customerNo;
    private String contractNo;
    private int contractLevel;
    private Car car;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public int getContractLevel() {
        return contractLevel;
    }

    public void setContractLevel(int contractLevel) {
        this.contractLevel = contractLevel;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
