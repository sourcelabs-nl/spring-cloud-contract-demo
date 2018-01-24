package nl.sourcelabs.spring.cloud.contract.producer.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping(path = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {
        switch (id) {
            case "500": return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            case "404": return ResponseEntity.notFound().build();
            default:    return ResponseEntity.ok(new Order(id));
        }
    }
}