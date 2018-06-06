package nl.sourcelabs.spring.cloud.contract.producer.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

    private final String id;
    private final Double totalPrice;

    @JsonCreator
    public Order(@JsonProperty("id") String id, @JsonProperty("totalPrice") Double totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}