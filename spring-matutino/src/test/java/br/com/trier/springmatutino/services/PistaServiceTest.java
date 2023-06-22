package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.services.impl.PistaServiceImpl;
import jakarta.transaction.Transactional;
@Transactional
public class PistaServiceTest extends BaseTests{
	@Autowired
	PistaServiceImpl pistaService;
	
	@Test
	@DisplayName("Teste buscar pista por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	
	void findByIdTest() {
		var pista = pistaService.findById(1);
		assertThat(pista).isNotNull();
		assertEquals(10, pista.getTamanho());

	}
}
