package nl.sourcelabs.spring.cloud.contract.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.stubrunner.server.EnableStubRunnerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;
import org.springframework.web.util.DefaultUriTemplateHandler;

@Configuration
public class Config {

    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
        uriTemplateHandler.setBaseUrl(baseUrl);
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().set("Accept", MediaType.APPLICATION_JSON_VALUE);
            return execution.execute(request, body);
        };
        return restTemplateBuilder.uriTemplateHandler(uriTemplateHandler).interceptors(interceptor).build();
    }

    @Bean
    public ScriptTemplateConfigurer handlebarsConfigurer() {
        ScriptTemplateConfigurer configurer = new ScriptTemplateConfigurer();
        configurer.setEngineName("nashorn");
        configurer.setScripts("/public/js/polyfill.js",
                "/META-INF/resources/webjars/handlebars/3.0.0-1/handlebars.js",
                "/public/js/render.js");
        configurer.setRenderFunction("render");
        configurer.setSharedEngine(true);
        return configurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        ScriptTemplateViewResolver viewResolver = new ScriptTemplateViewResolver();
        viewResolver.setPrefix("/public/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Profile("stub")
    @EnableStubRunnerServer
    @Configuration
    public class StubConfiguration {
    }
}