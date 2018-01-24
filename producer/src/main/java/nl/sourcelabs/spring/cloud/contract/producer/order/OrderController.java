package nl.sourcelabs.spring.cloud.contract.producer.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
public class OrderController {

    @GetMapping(path = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {
        if("500".equals(id)) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        if("404".equals(id)) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new Order(id));
    }
}
