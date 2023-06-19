package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Campeonato;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTests {
	@Autowired
	CampeonatoService campeonatoService;

	@Test
	@DisplayName("Teste buscar campeonato por ID")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByIdTest() {
		var campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("Campeonato 1", campeonato.getDescricao());
		assertEquals(2023, campeonato.getAno());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void listAllTest() {
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste incluir campeonato")
	void insertCampeonatoTest() {
		var campeonato = new Campeonato(null, "Campeonato 8", 2021);
		campeonatoService.salvar(campeonato);
		campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("Campeonato 8", campeonato.getDescricao());
		assertEquals(2021, campeonato.getAno());
	}

	@Test
	@DisplayName("Teste alterar campeonato")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void UpdateCampeonatoTest() {
		var campeonato = new Campeonato(1, "Campeonato 4", 2023);
		campeonatoService.update(campeonato);
		campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("Campeonato 4", campeonato.getDescricao());
		assertEquals(2023, campeonato.getAno());
	}

	@Test
	@DisplayName("Teste deleter campeonato")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void DeleteCampeonatoTest() {
		campeonatoService.delete(1);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste delete campeonato invalido")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void deleteNonExistentTest() {
		campeonatoService.delete(20);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste buscar campeonato por nome que inicia com")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findCampeonatoByNameStartsWithTest() {
		List<Campeonato> lista = campeonatoService.findByDescricao("c");
		assertEquals(0, lista.size());
		lista = campeonatoService.findByDescricaoStartingWithIgnoreCase("Campeonato");
		assertEquals(2, lista.size());
		lista = campeonatoService.findByDescricao("1");
		assertEquals(0, lista.size());
	}

	@Test
	@DisplayName("Teste buscar campeonato por intervalo de ano")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByAnoBetween() {
		 List<Campeonato> campeonatos = campeonatoService.findByAnoBetween(2021, 2023);
        assertNotNull(campeonatos);
        assertEquals(2, campeonatos.size());
        Campeonato campeonato1 = campeonatos.get(0);
        assertEquals(1, campeonato1.getId());
        assertEquals("Campeonato 1", campeonato1.getDescricao());
        assertEquals(2023, campeonato1.getAno());

        Campeonato campeonato2 = campeonatos.get(1);
        assertEquals(2, campeonato2.getId());
        assertEquals("Campeonato 2", campeonato2.getDescricao());
        assertEquals(2021, campeonato2.getAno());
    
	}
}
