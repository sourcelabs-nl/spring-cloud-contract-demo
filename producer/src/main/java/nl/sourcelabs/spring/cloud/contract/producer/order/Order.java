package nl.sourcelabs.spring.cloud.contract.producer.order;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Order {

    private final String id;
    private final double totalPrice;

    @JsonCreator
    public Order(final String id, double totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                '}';
    }
}