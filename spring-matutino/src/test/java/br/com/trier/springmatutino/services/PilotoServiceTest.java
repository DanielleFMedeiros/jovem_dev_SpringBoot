package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import br.com.trier.springmatutino.services.impl.PilotoServiceImpl;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTests {
	@Autowired
	PilotoServiceImpl service;

	@Test
	@DisplayName("Teste buscar piloto por ID")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })

	void findByIdTest() {
		var piloto = service.findById(1);
		assertEquals(1, piloto.getId());

	}

	@Test
	@DisplayName("Teste buscar piloto por id inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(7));
		assertEquals("Piloto id 7 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir piloto ")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void insertTest() {
		var piloto = new Piloto(null, "Bruno", new Pais(1, null), new Equipe(1, null));
		service.salvar(piloto);
		assertEquals(1, service.listAll().size());
		assertEquals("Bruno", piloto.getName());
	}


	@Test
	@DisplayName("Teste inserir piloto invalida")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void insertInvalidTest() {
		var piloto = new Piloto(null, "", new Pais(1, null), new Equipe(1, null));
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.salvar(piloto));
		assertEquals("Preencha corretamente o nome do piloto", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar piloto")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void updateTest() {
		var piloto = new Piloto(1, "Caio", new Pais(1, null), new Equipe(1, null));
		service.update(piloto);
		List<Piloto> pilotos = service.listAll();
		assertEquals("Caio", pilotos.get(0).getName());
	}

	@Test
	@DisplayName("Teste alterar piloto invalido")

	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void updateInvalidTest() {
		var piloto = new Piloto(6, "Jessica", new Pais(1, null), new Equipe(1, null));
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.update(piloto));
		assertEquals("Piloto não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste deletar piloto")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void deleteTest() {
		service.delete(1);
		assertEquals(2, service.listAll().size());
	}

	@Test
	@DisplayName("Teste deletar piloto inválida")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void deleteInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(7));
		assertEquals("Piloto id 7 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos os pilotos")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void listAllTest() {
		assertEquals(3, service.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos os pilotos com a lista vazia")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void listAllInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há pilotos cadastrados", exception.getMessage());
	}

	@Test
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@DisplayName("Teste buscar pilotos pela equipe")
	void findByEquipeTest() {
		var pilotos = service.findByEquipe(new Equipe(1, null));
		assertEquals(3, pilotos.size());
		assertEquals("João", pilotos.get(0).getName());
		assertEquals("Paulo", pilotos.get(1).getName());
		assertEquals("Vanessa", pilotos.get(2).getName());
	}

	@Test
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@DisplayName("Teste buscar pilotos pela equipe sem encontrar")
	void findByEquipeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByEquipe(new Equipe(8, "Equipe")));
		assertEquals("Não há pilotos do(a) equipe Equipe", exception.getMessage());
	}

	@Test
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@DisplayName("Teste buscar piloto pelo nome ordenando")
	void findByNomeContainsIgnoreCaseOrderByNomeTest() {
		var pilotos = service.findByNameContainsIgnoreCaseOrderByName("o");
		assertEquals(2, pilotos.size());
		assertEquals("João", pilotos.get(0).getName());
		assertEquals("Paulo", pilotos.get(1).getName());
	}

	@Test
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@DisplayName("Teste buscar piloto pelo nome ordenando sem achar")
	void findByNomeContainsIgnoreCaseOrderByNomeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,
				() -> service.findByNameContainsIgnoreCaseOrderByName("qualquer"));
		assertEquals("Piloto qualquer não encontrado", exception.getMessage());
	}

	@Test
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@DisplayName("Teste buscar pilotos pelo país")
	void findByPaisTest() {
		var pilotos = service.findByPais(new Pais(1, null));
		assertEquals(2, pilotos.size());
	}

	@Test
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@DisplayName("Teste buscar pilotos pelo país sem achar")
	void findByPaisNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPais(new Pais(7, "Africa")));
		assertEquals("Não há pilotos desse país: Africa", exception.getMessage());
	}

}
