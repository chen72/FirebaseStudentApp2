package sg.edu.rp.webservices.firebasestudentapp2;

import java.io.Serializable;

public class Inventory implements Serializable {
    private String id;
    private String name;
    private Double price;

    public Inventory() {
    }

    public Inventory(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }


}
