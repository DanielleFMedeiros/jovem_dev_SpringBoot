package br.com.trier.springmatutino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.UserService;
import br.com.trier.springmatutino.services.impl.CampeonatoServiceImpl;
import br.com.trier.springmatutino.services.impl.EquipeServiceImpl;
import br.com.trier.springmatutino.services.impl.PaisServiceImpl;
import br.com.trier.springmatutino.services.impl.PistaServiceImpl;
import br.com.trier.springmatutino.services.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public PaisService paisService() {
		return new PaisServiceImpl();
	}
	
	@Bean
	public EquipeService equipeService() {
		return new EquipeServiceImpl();
	}
	
	@Bean
	public CampeonatoService campeonatoService() {
		return new CampeonatoServiceImpl();
	}
	
	@Bean
	public PistaService pistaService() {
		return new PistaServiceImpl();
	}
	
	
}

