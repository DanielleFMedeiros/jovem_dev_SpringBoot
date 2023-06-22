package br.com.trier.springmatutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.domain.Pais;

@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {

	@Autowired
	private TestRestTemplate rest;

	@Test
	@DisplayName("Teste de inserção de país")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	public void insertTest() {
		Pais pais = new Pais();
		pais.setId(1);
		pais.setName("Brasil");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> request = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> response = rest.exchange("/paises", HttpMethod.POST, request, Pais.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().getId());
		assertEquals("Brasil", response.getBody().getName());
	}

	@Test
	@DisplayName("Teste buscar país pelo código")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	public void buscaPorCodigoTest() {
		ResponseEntity<Pais> response = rest.exchange("/paises/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Pais>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());;
		assertEquals(1, response.getBody().getId());
		assertEquals("Brasil", response.getBody().getName());
	}

	@Test
	@DisplayName("Teste de listagem de todos os países")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	public void listarTodosTest() {
		// Execução
		ResponseEntity<List<Pais>> response = rest.exchange("/paises", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Pais>>() {
				});

		// Verificação
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(3, response.getBody().size());
		assertEquals(2, response.getBody().get(0).getId());
		assertEquals("Alemanha", response.getBody().get(0).getName());
		assertEquals(3, response.getBody().get(1).getId());
		assertEquals("Argentina", response.getBody().get(1).getName());
		assertEquals(1, response.getBody().get(2).getId());
		assertEquals("Brasil", response.getBody().get(2).getName());
	}

	@Test
	@DisplayName("Teste alterar pais")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	void updateTest() {
		Pais pais = new Pais(1, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/paises/1", HttpMethod.PUT, requestEntity, Pais.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}

	@Test
	@DisplayName("Teste de exclusão de país")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	public void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/paises/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste buscar pais pelo nome com like")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	void findByNameLikeTest() {
	    ResponseEntity<List<Pais>> response = rest.exchange("/paises/name/Bra%", HttpMethod.GET, null,
	            new ParameterizedTypeReference<List<Pais>>() {});
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    List<Pais> paises = response.getBody();
	    assertNotNull(paises);
	    assertEquals(1, paises.size());
	    assertEquals("Brasil", paises.get(0).getName());
	}

	
}
