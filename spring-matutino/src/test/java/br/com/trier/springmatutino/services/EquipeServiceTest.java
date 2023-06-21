package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Equipe;
import jakarta.transaction.Transactional;

@Transactional
public class EquipeServiceTest extends BaseTests {
	@Autowired
	EquipeService equipeService;

	@Test
	@DisplayName("Teste buscar equipe por ID")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void findByIdTest() {
		var equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("Equipe 1", equipe.getName());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void listAllTest() {
		List<Equipe> lista = equipeService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste incluir equipe")
	void insertEquipeTest() {
		var equipe = new Equipe(null, "Equipe 4");
		equipeService.salvar(equipe);
		equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("Equipe 4", equipe.getName());
	}

	@Test
	@DisplayName("Teste alterar equipe")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void UpdateEquipeTest() {
		var equipe = new Equipe(1, "Equipe 3");
		equipeService.update(equipe);
		equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("Equipe 3", equipe.getName());
	}

	@Test
	@DisplayName("Teste deleter equipe")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void DeleteEquipeTest() {
		equipeService.delete(1);
		List<Equipe> lista = equipeService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste delete equipe invalido")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void deleteNonExistentTest() {
		equipeService.delete(20);
		List<Equipe> lista = equipeService.listAll();
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste buscar equipe por nome que inicia com")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void findEquipeByNameStartsWithTest() {
		List<Equipe> lista = equipeService.findByNameStartingWithIgnoreCase("e");
		assertEquals(2, lista.size());

	}

	@Test
	@DisplayName("Teste buscar equipe por nome com LIKE")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void findEquipeByNameWithLikeTest() {
		List<Equipe> lista = equipeService.findByNameLike("%1");
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());
		assertEquals("Equipe 1", lista.get(0).getName());

		lista = equipeService.findByNameLike("%i%");
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
		assertEquals("Equipe 1", lista.get(0).getName());
		assertEquals(2, lista.get(1).getId());
		assertEquals("Equipe 2", lista.get(1).getName());

		lista = equipeService.findByNameLike("XXX%");
		assertEquals(0, lista.size());
	}

	@Test
	@DisplayName("Teste buscar equipe por nome contendo (ignorando caso)")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void testFindByNameContainingIgnoreCase() {
		List<Equipe> lista = equipeService.findByNameContainingIgnoreCase("equipe");
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
		assertEquals("Equipe 1", lista.get(0).getName());
		assertEquals(2, lista.get(1).getId());
		assertEquals("Equipe 2", lista.get(1).getName());

		lista = equipeService.findByNameContainingIgnoreCase("1");
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());
		assertEquals("Equipe 1", lista.get(0).getName());

		lista = equipeService.findByNameContainingIgnoreCase("x");
		assertEquals(0, lista.size());
	}

}
