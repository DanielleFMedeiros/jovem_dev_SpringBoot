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

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.config.jwt.LoginDTO;
import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders("email1", "senha1")), UserDTO.class);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(getHeaders("email1", "senha1")),
				new ParameterizedTypeReference<List<UserDTO>>() {
				});
	}

	@Test
	@DisplayName("Buscar por id")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testGetIdOk() {

		ResponseEntity<UserDTO> response = getUser("/usuarios/3");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("Usuario teste 1", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testGetIdNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("email1", "senha1");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios", HttpMethod.POST, requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());

	}

	@Test
	@DisplayName("Buscar por nome")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })

	public void testGetNameOk() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/Usuario teste 1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(1, response.getBody().size());
		UserDTO user =response.getBody().get(0);
		assertEquals(3, user.getId());
		assertEquals("teste1@teste.com.br", user.getEmail());
	}

	@Test
	@DisplayName("Listar todos os usuários cadastrados")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testGetListAllOk() {
		ResponseEntity<List<UserDTO>> response =  rest.exchange(
				"/usuarios", 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("email1", "senha1")),
				new ParameterizedTypeReference<List<UserDTO>>() {} 
				);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}

	@Test
	@DisplayName("Buscar usuários por nome contendo ")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testFindByNameStartingWithIgnoreCase() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/Usuario");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> userList = response.getBody();
		assertEquals(2, userList.size());

		UserDTO user = userList.get(0);
		assertEquals(3, user.getId());
		assertEquals("teste1@teste.com.br", user.getEmail());

		UserDTO user2 = userList.get(1);
		assertEquals(4, user2.getId());
		assertEquals("teste2@teste.com.br", user2.getEmail());
	}

	@Test
	@DisplayName("Teste delete")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void deleteTest() {
		HttpHeaders headers = getHeaders("email1", "senha1");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/usuarios/3", 
				HttpMethod.DELETE,  
				requestEntity, 
				Void.class
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste update")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void updateTest() {

		UserDTO dto = new UserDTO(3, "Usuario Test", "test@teste.com.br", "123", "ADMIN");

		HttpHeaders headers = getHeaders("email1", "senha1");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/3", HttpMethod.PUT, requestEntity,
				UserDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("Usuario Test", user.getName());
	}

	@Test
	@DisplayName("Buscar usuário por email")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testGetUserByEmail() {
		String email = "teste1@teste.com.br";
		ResponseEntity<List<UserDTO>> response = getUsers("/users/email/"+email);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(1, response.getBody().size());

	}

}