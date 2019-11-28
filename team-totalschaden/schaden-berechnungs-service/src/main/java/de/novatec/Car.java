package de.novatec;

public class Car {

    public int getOriginalValue() {
        return originalValue;
    }

    private final int originalValue;
    private int age;
    private String brand;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    private int ps;

    public Car(int originalValue, int age, String brand, int ps) {
        this.originalValue = originalValue;
        this.age = age;
        this.brand = brand;
        this.ps = ps;
    }
}
