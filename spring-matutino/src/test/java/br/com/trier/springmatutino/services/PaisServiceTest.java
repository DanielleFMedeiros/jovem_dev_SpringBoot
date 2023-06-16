package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pais;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{
	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste buscar país por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		var pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllTest() {
		List<Pais> lista = paisService.listAll();
		assertEquals(3, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste incluir país")
	void insertPaisTest() {
		var pais = new Pais(null, "França");
		paisService.salvar(pais);
		pais=paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("França", pais.getName());
	}
	
	@Test
	@DisplayName("Teste alterar país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void UpdatePaisTest() {
		var pais = new Pais(1, "EUA");
		paisService.update(pais);
		pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("EUA", pais.getName());
	}
	
	@Test
	@DisplayName("Teste deleter país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void DeletePaisTest() {
		paisService.delete(1);
		List<Pais>lista = paisService.listAll();
		assertEquals(2, lista.size());
		assertEquals(2, lista.get(0).getId());	
	}
	
	@Test
	@DisplayName("Teste buscar país por nome que inicia com")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findPaisByNameStartsWithTest() {
		List<Pais>lista = paisService.findByName("u");
		assertEquals(0, lista.size());
		lista = paisService.findByName("Argentina");
		assertEquals(1, lista.size());
		lista = paisService.findByName("França");
		assertEquals(0, lista.size());
	}
}
