package nl.sourcelabs.spring.cloud.contract.producer.order;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * In the integration test if our component works in the runtime environment.
 * <p>
 * We start a Spring application context and test the component inside the context.
 * <p>
 * Note: same test cases as in the OrderControllerTest. But slower to start/run.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerIT {

    @Autowired
    private OrderController orderController;

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
