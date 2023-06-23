package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.impl.CorridaServiceImpl;
import jakarta.transaction.Transactional;

@Transactional
public class CorridaServiceTest extends BaseTests {
	@Autowired
	CorridaServiceImpl service;

	@Test
	@DisplayName("Teste buscar corrida por ID")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdTest() {
		ZonedDateTime data = ZonedDateTime.parse("2024-05-11T13:30Z");
		var corrida = service.findById(1);
		assertEquals(2, corrida.getPista().getId());
		assertEquals(data, corrida.getData());
	}

	@Test
	@DisplayName("Teste buscar corrida por id inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(7));
		assertEquals("Corrida id 7 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir corrida ")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void insertTest() {
		ZonedDateTime data = ZonedDateTime.parse("2023-09-14T12:34:00Z");
		var corrida = new Corrida(null, data, new Pista(1, null, null), new Campeonato(1, null, 2023));
		service.salvar(corrida);
		assertEquals(1, service.listAll().size());
	}

	@Test
	@DisplayName("Teste inserir corrida invalida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void insertInvalidTest() {
		ZonedDateTime data = ZonedDateTime.parse("2022-05-14T12:34:00Z");
		var corrida = new Corrida(null, data, new Pista(1, null, null), new Campeonato(1, null, 2023));
		
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.salvar(corrida));
		assertEquals("O ano da corrida deve ser igual ao ano do campeonato", exception.getMessage());

		
		
	}

	@Test
	@DisplayName("Teste alterar corrida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void updateTest() {
		ZonedDateTime data = ZonedDateTime.parse("2023-09-14T12:34:00Z");
		var corrida = new Corrida(1, data, new Pista(1, null, null), new Campeonato(1, null, 2023));
		service.update(corrida);
		assertEquals(data, service.findById(1).getData());
	}

	@Test
	@DisplayName("Teste alterar corrida invalida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void updateInvalidTest() {
		ZonedDateTime data = ZonedDateTime.parse("2023-09-14T12:34:00Z");
		var corrida = new Corrida(6, data, new Pista(1, null, null), new Campeonato(1, null, 2023));
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.update(corrida));
		assertEquals("Corrida não existe", exception.getMessage());
		
		
	}

	@Test
	@DisplayName("Teste deletar corrida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void deleteTest() {
		service.delete(1);
		assertEquals(2, service.listAll().size());
	}

	@Test
	@DisplayName("Teste deletar corrida inválida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void deleteInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(7));
		assertEquals("Corrida id 7 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todas as corridas")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	void listAllTest() {
		assertEquals(3, service.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos os corridas com a lista vazia")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void listAllInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Corridas não foram cadastradas", exception.getMessage());
	}
	

	@Test
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@DisplayName("Teste buscar corridas por pista")
	void findByPaisTest() {
		var corridas = service.findByPista(new Pista(1, null, null));
		assertEquals(2, corridas.size());
	}

	@Test
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@DisplayName("Teste buscar corridas pelo país sem achar")
	void findByPaisNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPista(new Pista(7, null, null)));
		assertEquals("Não há corridas nessa pista: 7", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar corridas pela data")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDataTest() {
		ZonedDateTime data = ZonedDateTime.parse("2024-05-11T13:30:00Z");
		var corridas = service.findByData(data);
		assertEquals(1, corridas.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste buscar corrida por data inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDataNotFoundTest() {
		ZonedDateTime data = ZonedDateTime.parse("2025-10-14T12:34:00Z");
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByData(data));
		assertEquals("Não há corridas na data: 14/10/2025 12:34", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar corridas entre datas")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDataBetweenTest() {
		ZonedDateTime dataInicial = ZonedDateTime.parse("2023-10-14T12:34:00Z");
		ZonedDateTime dataFinal = ZonedDateTime.parse("2023-12-14T12:34:00Z");
		var corridas = service.findByDataBetween(dataInicial, dataFinal);
		assertEquals(1, corridas.size());
	}
	
	@Test
	@DisplayName("Teste buscar corridas entre datas inexistentes")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDataBetweenNotFoundTest() {
		ZonedDateTime dataInicial = ZonedDateTime.parse("2025-10-14T12:34:00Z");
		ZonedDateTime dataFinal = ZonedDateTime.parse("2025-12-14T12:34:00Z");
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByDataBetween(dataInicial, dataFinal));
		assertEquals("Não há corridas entre as datas 14/10/2025 12:34 e 14/12/2025 12:34", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar corridas por pista sem achar")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByCampeonatoNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByCampeonato(new Campeonato(3, "Campeonato 3", null)));
		assertEquals("Não há corridas no: Campeonato 3", exception.getMessage());
	}
	

}
