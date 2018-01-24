package nl.sourcelabs.spring.cloud.contract.producer.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

/**
 * Unit test which tests the different scenarios in the OrderController.
 * <p>
 * In the unit test we leave the environment configuration out of scope (Spring).
 * <p>
 * We focus on testing the component: OrderController
 */
public class OrderControllerTest {

    private OrderController orderController;

    @Before
    public void setup() {
        orderController = new OrderController();
    }

    @Test
    public void assertOk() {
        ResponseEntity<Order> response200 = orderController.getOrderById("1");
        Assert.assertEquals(200, response200.getStatusCodeValue());
        Assert.assertEquals("1", response200.getBody().getId());
    }

    @Test
    public void assertNotFound() {
        ResponseEntity<Order> response404 = orderController.getOrderById("404");
        Assert.assertEquals(404, response404.getStatusCodeValue());
    }

    @Test
    public void assertError() {
        ResponseEntity<Order> response500 = orderController.getOrderById("500");
        Assert.assertEquals(500, response500.getStatusCodeValue());
    }
}