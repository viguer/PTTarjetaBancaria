package com.prueba.tecnica.tarjetabancaria;

import com.prueba.tecnica.tarjetabancaria.controller.BusinessController;
import com.prueba.tecnica.tarjetabancaria.repositories.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
class TarjetabancariaApplicationTests {


	@Test
	void contextLoads() {
		String[] args = new String[0];
    	TarjetabancariaApplication application = new TarjetabancariaApplication();
		 application.main(args);
	}

}
