package nl.sourcelabs.spring.cloud.contract.consumer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.server.EnableStubRunnerServer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;

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
    public void checkoutTest() {
        ExtendedModelMap m = new ExtendedModelMap();
        clientController.checkout("1", m);
        Assert.assertTrue("response does not contain expected data", ((String) m.get("order")).contains("{\"id\":1}"));
    }
}
