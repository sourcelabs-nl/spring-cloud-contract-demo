package nl.sourcelabs.spring.cloud.contract.consumer;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class ConsumerController {

    private final RestTemplate restTemplate;

    public ConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }

    @GetMapping(value = "/checkout", produces = MediaType.TEXT_HTML_VALUE)
    public String checkout(@RequestParam(value = "id") String id, Model model) {
        ResponseEntity<String> response = restTemplate.getForEntity("/orders/{id}", String.class, id);
        String body = response.hasBody() && response.getStatusCode().is2xxSuccessful() ? response.getBody() : "Unexpected HTTP response, status code: " + response.getStatusCode() + " (" + response.getStatusCode().getReasonPhrase() + ")";
        model.addAttribute("order", body);
        return "checkout";
    }

    @GetMapping(value = "/github", produces = MediaType.TEXT_HTML_VALUE)
    public String github() {
        return "github";
    }
}