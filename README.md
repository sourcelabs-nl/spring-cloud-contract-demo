## Testing microservices with Spring Cloud Contract

This application demonstrates how to use Spring Cloud Contract for testing microservices, covering the following topics:

* Defining contracts for your API
* Verifying the your API implementation against the defined contracts
* Generating stubs for consumers (clients) of your API
* Using a stub server during consumer tests
* Consumers can run a stub server locally, using your API, without using the real service implementation.

Before getting started, make sure you have some basic understanding about microservices and the challenges associated testing them. Some excellent reading material:

* https://martinfowler.com/articles/microservice-testing/
* https://martinfowler.com/bliki/IntegrationTest.html
* https://martinfowler.com/articles/consumerDrivenContracts.html
* https://cloud.spring.io/spring-cloud-contract/

### Defining the API's behaviour

By far the most important thing to do is defining your API's behaviour. In Spring Cloud Contract you have different options for defining contracts that define this behaviour. 

One way of doing this is using the Groovy Contract DSL in which you can define request-response interaction using a Groovy DSL, which is being used in this example application.

An example for the following scenario: requesting an order from the server with `id=1` should return an order containing at least an fields named `id` in the json response.

Groovy file: `assertGetOrderById.groovy`:
```groovy
package contracts.order

import org.springframework.cloud.contract.spec.Contract

/**
 * Contract definition, written in a Groovy DSL
 */
Contract.make {

    /**
     * Request response which succeeds, completes HTTP status code 200
     */
    request {
        method 'GET'
        url('/orders/1')
        headers {
            accept("application/json")
        }
    }
    response {
        status 200
        body(["id" : 1])
        headers {
            contentType(applicationJson())
        }
    }
}
```

Based on this DSL we can generate unit test for verifying the real API implementation. But also (WireMock) stubs which can be used for running a stub server by consumers of you service.

The generated JUnit test in `OrderTest.java` would look like this: 

```java
@Test
public void validate_assertGetOrderById() throws Exception {
    // given:
        MockMvcRequestSpecification request = given()
                .header("Accept", "application/json");

    // when:
        ResponseOptions response = given().spec(request)
                .get("/orders/1");

    // then:
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).matches("application/json.*");
    // and:
        DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
        assertThatJson(parsedJson).field("['id']").isEqualTo(1);
}
```

The WireMock stub for this contract: `assertGetOrderById.json`:

```json
{
  "id" : "a5367421-9668-4ea4-9663-40dc75d46278",
  "request" : {
    "url" : "/orders/1",
    "method" : "GET",
    "headers" : {
      "Accept" : {
        "matches" : "application/json.*"
      }
    }
  },
  "response" : {
    "status" : 200,
    "body" : "{\"id\":1}",
    "headers" : {
      "Content-Type" : "application/json"
    },
    "transformers" : [ "response-template" ]
  },
  "uuid" : "a5367421-9668-4ea4-9663-40dc75d46278"
}
```

### Building the project

You can build the project using maven by executing the following command on top level:

```bash
mvn clean install
```

Or using the maven wrapper

```bash
mvnw clean install
```

### Running the client

The client application is a web application that uses another service for displaying an html page. 

This application can be started in different modes:

* Using the stub server for simulating responses from the other service
* Using the real server dependency

#### Client with stubs

Starting the client with stubs enabled:

```bash
mvn -rf consumer spring-boot:run -Dspring.profiles.active=stub
```


#### Client without stubs

Now you also need to start the server application to get real responses:

The server application can be started by executing the following command:

```bash
mvn -rf producer spring-boot:run
```

Starting the client using the real service:

```bash
mvn -rf consumer spring-boot:run
```

That's it!