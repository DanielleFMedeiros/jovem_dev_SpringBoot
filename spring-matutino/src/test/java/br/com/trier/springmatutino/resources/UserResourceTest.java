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
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.getForEntity(url, UserDTO.class);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
		});
	}

	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("Usuario teste 1", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios", HttpMethod.POST, requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}

//	@Test
//	@DisplayName("Buscar por nome")
//	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
//	public void testGetNameOk() {
//		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/Usuario teste 1");
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
//		List<UserDTO> userList = response.getBody();
//		assertEquals(2, userList.size());
//		UserDTO user = userList.get(0);
//	    assertEquals(1, user.getId());
//	    assertEquals("teste1@teste.com.br", user.getEmail());
//	    
//	}

	@Test
	@DisplayName("Buscar por nome")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	public void testGetNameOk() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/Usuario teste 1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> userList = response.getBody();

		assertEquals(1, userList.size());

		UserDTO user = userList.get(0);
		assertEquals(1, user.getId());
		assertEquals("teste1@teste.com.br", user.getEmail());
	}

	@Test
	@DisplayName("Listar todos os usuários cadastrados")
	public void testGetListAllOk() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> userList = response.getBody();

		assertEquals(2, userList.size());

		UserDTO user1 = userList.get(0);
		assertEquals(1, user1.getId());
		assertEquals("Usuario teste 1", user1.getName());
		assertEquals("teste1@teste.com.br", user1.getEmail());

		UserDTO user2 = userList.get(1);
		assertEquals(2, user2.getId());
		assertEquals("Usuario teste 2", user2.getName());
		assertEquals("teste2@teste.com.br", user2.getEmail());
	}

	@Test
	@DisplayName("Buscar usuários por nome contendo")
	public void testGetUsersByNameContaining() {
	    String searchText = "Usuario teste 1";

	    // Constrói a URL com o parâmetro de busca
	    String url = "/usuarios/name?contains=@Usuario teste 1" + searchText;

	    // Faz a requisição GET
	    ResponseEntity<List<UserDTO>> responseEntity = rest.exchange(url, HttpMethod.GET,
	            null, new ParameterizedTypeReference<List<UserDTO>>() {});

	    // Verifica o status da resposta
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	    // Obtém a lista de usuários da resposta
	    List<UserDTO> users = responseEntity.getBody();

	    // Verifica se a lista não é nula
	    assertNotNull(users);

	    // Verifica o tamanho da lista
	    assertEquals(2, users.size());

	    UserDTO user1 = users.get(0);
	    assertEquals(1, user1.getId());
	    assertEquals("Usuario teste 1", user1.getName());
	    assertEquals("teste1@teste.com.br", user1.getEmail());
	}


}