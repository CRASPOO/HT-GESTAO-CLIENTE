package com.example.order.bdd;

//import com.example.order.webhook.PaymentServicePort; // <--- Confira se o import está certo
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
//@Import(CucumberSpringConfiguration.BddTestConfig.class) // <--- Forçamos a importação aqui
public class CucumberSpringConfiguration {
}