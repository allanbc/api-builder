package br.com.builder.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import br.com.builder.cliente.config.property.ClienteApiProperty;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableConfigurationProperties(ClienteApiProperty.class)
public class ClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
	}

}
