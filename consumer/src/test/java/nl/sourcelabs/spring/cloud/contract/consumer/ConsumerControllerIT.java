package nl.sourcelabs.spring.cloud.contract.consumer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.server.EnableStubRunnerServer;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration test using WireMock for stubbing the producer responses.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableStubRunnerServer
public class ConsumerControllerIT {

    @Autowired
    private ConsumerController clientController;

    @Test
    public void indexPage() {
        String indexPage = clientController.index("1");
        Assert.assertTrue("response does not contain expected data", indexPage.contains("Your order data: Order{id='1'}"));
    }
}
