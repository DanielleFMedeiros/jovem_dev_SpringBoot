package br.com.trier.springmatutino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.springmatutino.services.UserService;
import br.com.trier.springmatutino.services.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
}
