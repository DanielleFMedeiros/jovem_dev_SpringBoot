package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
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
	@DisplayName("Teste listar todos os campeonatos lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.listAll());
		assertEquals("Não há campeonatos cadastrados", exception.getMessage());
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
	@DisplayName("Teste incluir campeonato inválido")
	void insertCampeonatoInvalidoTest() {
		var campeonato = new Campeonato(null, "Campeonato 8", 2024);
		var exception = assertThrows(ViolacaoIntegridade.class, () -> campeonatoService.salvar(campeonato));
		assertEquals("O ano precisa ser maior ou igual a 1990 e menor ou igual a 2023", exception.getMessage());
		
		var campeonato2 = new Campeonato(null, "", null);
		var exception2 = assertThrows(ViolacaoIntegridade.class, () -> campeonatoService.salvar(campeonato2));
		assertEquals("A descrição está vazia", exception2.getMessage());
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
		List<Campeonato> campeonatos = campeonatoService.findByAnoBetween(2021, 2022);
		assertNotNull(campeonatos);
		assertEquals(1, campeonatos.size());
		Campeonato campeonato1 = campeonatos.get(0);
		assertEquals(2, campeonato1.getId());
		assertEquals("Campeonato 2", campeonato1.getDescricao());
		assertEquals(2021, campeonato1.getAno());

		assertTrue(campeonatoService.validateYear(campeonato1.getAno()));
	}

	@Test
	@DisplayName("Teste buscar campeonato por ano")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByAno() {
		Integer ano = 2022;

		List<Campeonato> campeonatos = campeonatoService.findByAno(ano);
		assertNotNull(campeonatos);
		assertEquals(0, campeonatos.size());

		for (Campeonato campeonato : campeonatos) {
			assertEquals(ano, campeonato.getAno());
		}
	}

	@Test
	@DisplayName("Teste buscar campeonato por descrição (like)")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByDescricaoLike() {
		String descricao = "Campeonato%";

		List<Campeonato> campeonatos = campeonatoService.findByDescricaoLike(descricao);
		assertNotNull(campeonatos);
		assertEquals(2, campeonatos.size());

	}
	
	@Test
    @DisplayName("Teste buscar campeonato por descrição inválida (like)")
    @Sql({ "classpath:/resources/sqls/campeonato.sql" })
    void findByDescricaoLikeInvalid() {
        String descricao = "CampeonatoXYZ%";

        assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findByDescricaoLike(descricao));
    }

}
