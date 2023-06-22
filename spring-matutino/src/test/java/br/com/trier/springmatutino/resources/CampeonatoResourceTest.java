package br.com.trier.springmatutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Pais;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {

	@Autowired
	private TestRestTemplate rest;
	
	private ResponseEntity<Campeonato> getCampeonato(String url) {
		return rest.getForEntity(url, Campeonato.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Campeonato>> getCampeonatos(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Campeonato>>() {
		});
		
		
	}
	
	@Test
	@DisplayName("Teste inserir campeonato")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	void insertTest() {
		Campeonato campeonato = new Campeonato(null, "teste", 2020);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(campeonato, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonatos", HttpMethod.POST, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(1, responseEntity.getBody().getId());
		assertEquals("teste", responseEntity.getBody().getDescricao());
		
		
	}

	@Test
	@DisplayName("Teste buscar campeonato pelo id")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByIdTest() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonatos/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Campeonato 1", response.getBody().getDescricao());
	}


	@Test
	@DisplayName("Teste listar todo os campeonatos")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void listAllTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonatos");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste update campeonato")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void updateTest() {
		Campeonato campeonato = new Campeonato(1, "teste", 2020);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(campeonato, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonatos/1", HttpMethod.PUT, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescricao());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/campeonatos/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste buscar campeonatos pelo ano")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByAnoTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonatos/ano/2023");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste buscar campeonato pela descricao")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByDescriptionStartingIgnoreCaseTest() {
	    ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonatos/descricao/Campeonato 1");
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    
	    List<Campeonato> campeonatos = response.getBody();
	    assertNotNull(campeonatos);
	    assertEquals(1, campeonatos.size());
	    
	    Campeonato campeonato = campeonatos.get(0);
	    assertEquals("Campeonato 1", campeonato.getDescricao());
	}

	
	
	@Test
	@DisplayName("Teste buscar campeonatos entre dois anos")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByAnoBetweenTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonatos/anos/2020/2022");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pela descricao com like")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void findByDescriptionContainsIgnoreCaseTest() {
		ResponseEntity<List<Campeonato>> response = rest.exchange("/campeonatos/like/Campeonato 1", HttpMethod.GET, null,
	            new ParameterizedTypeReference<List<Campeonato>>() {});
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    List<Campeonato> campeonatos = response.getBody();
	    assertNotNull(campeonatos);
	    assertEquals(1, campeonatos.size());
	    assertEquals("Campeonato 1", campeonatos.get(0).getDescricao());

		    
	}
	
}
