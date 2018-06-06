package nl.sourcelabs.spring.cloud.contract.producer

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

data class Order(val id: String)

@Api(description = "Producer api for order data")
@RestController
class OrderService {

    @ApiOperation("Get an order by its unique identifier")
    @GetMapping(path = ["/orders/{id}"], produces = ["application/json"])
    fun getOrderById(@PathVariable id: String): ResponseEntity<Order> = when (id) {
        "500" -> status(INTERNAL_SERVER_ERROR).build()
        "404" -> notFound().build()
        else -> ok(Order(id = id))
    }
}

@EnableSwagger2
@SpringBootApplication
class ProducerApplication {

    @Bean
    fun docket(): Docket = Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage(ProducerApplication::class.java.`package`.name))
            .paths(PathSelectors.any())
            .build()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ProducerApplication>(*args)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ProducerApplication>(*args)
}