package nl.sourcelabs.spring.cloud.contract.producer.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Very awesome order microservice")
@RestController
public class OrderController {

    @ApiOperation(value = "Finds an order by its unique identifier (string)")
    @GetMapping(path = "/orders/{id}", produces = "application/json")
    public ResponseEntity<Order> getOrderById(@ApiParam("Unique order identifier") @PathVariable("id") String id) {
        switch (id) {
            case "500": return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            case "404": return ResponseEntity.notFound().build();
            default:    return ResponseEntity.ok(new Order(id, 34.99));
        }
    }
}