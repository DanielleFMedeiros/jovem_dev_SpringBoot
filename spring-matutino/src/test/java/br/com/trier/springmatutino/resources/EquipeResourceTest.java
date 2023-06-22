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
import br.com.trier.springmatutino.domain.Equipe;

@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {

	@Autowired
	private TestRestTemplate rest;

	@Test
	@DisplayName("Teste de inserção de equipe")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	public void insertTest() {
		Equipe equipe = new Equipe();
		equipe.setId(1);
		equipe.setName("Equipe 1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> request = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> response = rest.exchange("/equipes", HttpMethod.POST, request, Equipe.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1, response.getBody().getId());
		assertEquals("Equipe 1", response.getBody().getName());
	}

	@Test
	@DisplayName("Teste buscar equipe pelo código")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	public void buscaPorCodigoTest() {
		ResponseEntity<Equipe> response = rest.exchange("/equipes/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<Equipe>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());;
		assertEquals(1, response.getBody().getId());
		assertEquals("Equipe 1", response.getBody().getName());
	}

	@Test
	@DisplayName("Teste de listagem de todos os equipes")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	public void listarTodosTest() {
		// Execução
		ResponseEntity<List<Equipe>> response = rest.exchange("/equipes", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Equipe>>() {
				});

		// Verificação
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
		assertEquals(1, response.getBody().get(0).getId());
		assertEquals("Equipe 1", response.getBody().get(0).getName());
		assertEquals(2, response.getBody().get(1).getId());
		assertEquals("Equipe 2", response.getBody().get(1).getName());
	}

	@Test
	@DisplayName("Teste alterar equipe")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void updateTest() {
		Equipe equipe = new Equipe(1, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipes/1", HttpMethod.PUT, requestEntity, Equipe.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}

	@Test
	@DisplayName("Teste de exclusão de equipe")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	public void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/equipes/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste buscar equipe pelo nome")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void findByNameTest() {
	    ResponseEntity<List<Equipe>> response = rest.exchange("/equipes/name/Equipe 1", HttpMethod.GET, null,
	            new ParameterizedTypeReference<List<Equipe>>() {});
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    List<Equipe> equipes = response.getBody();
	    assertNotNull(equipes);
	    assertEquals(1, equipes.size());
	    assertEquals("Equipe 1", equipes.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome com like")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void findByNameLikeTest() {
	    ResponseEntity<List<Equipe>> response = rest.exchange("/equipes/like/Equipe", HttpMethod.GET, null,
	            new ParameterizedTypeReference<List<Equipe>>() {});
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    List<Equipe> equipes = response.getBody();
	    assertNotNull(equipes);
	    assertEquals(2, equipes.size());
	    assertEquals("Equipe 1", equipes.get(0).getName());
	    assertEquals("Equipe 2", equipes.get(1).getName());
	}


	
}
