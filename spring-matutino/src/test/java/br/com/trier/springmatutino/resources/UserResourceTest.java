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
import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
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
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYXZpZCIsImlhdCI6MTY4Nzc4NzI0OCwiZXhwIjoxNjg3Nzg5MDQ4fQ.k-mjUf144LbiBtWqRWdrMl_CZCIdGcTjPVswTEOm4Us";
		headers.set("Autorização", "Bearer" + token);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("Usuario teste 1", user.getName());
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/user/3");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYXZpZCIsImlhdCI6MTY4Nzc4NzI0OCwiZXhwIjoxNjg3Nzg5MDQ4fQ.k-mjUf144LbiBtWqRWdrMl_CZCIdGcTjPVswTEOm4Us";
		headers.set("Autorização", "Bearer" + token);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}


	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha","ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW5pZWxsZSIsImlhdCI6MTY4Nzc4ODA3MCwiZXhwIjoxNjg3Nzg5ODcwfQ.QyDSGibIjeWHLy6t_y4sgAnHygMt--Je32FlzXb_Lbk";
		headers.set("Autorização", "Bearer" + token);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/usuarios", 
	            HttpMethod.POST,  
	            requestEntity,    
	            UserDTO.class   
	    );
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
		List<UserDTO> userList = response.getBody();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYXZpZCIsImlhdCI6MTY4Nzc4NzI0OCwiZXhwIjoxNjg3Nzg5MDQ4fQ.k-mjUf144LbiBtWqRWdrMl_CZCIdGcTjPVswTEOm4Us";
		headers.set("Autorização", "Bearer" + token);
		assertEquals(1, userList.size());

		UserDTO user = userList.get(0);
		assertEquals(1, user.getId());
		assertEquals("teste1@teste.com.br", user.getEmail());
	}

	@Test
	@DisplayName("Listar todos os usuários cadastrados")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testGetListAllOk() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> userList = response.getBody();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYXZpZCIsImlhdCI6MTY4Nzc4NzI0OCwiZXhwIjoxNjg3Nzg5MDQ4fQ.k-mjUf144LbiBtWqRWdrMl_CZCIdGcTjPVswTEOm4Us";
		headers.set("Autorização", "Bearer" + token);
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
	@DisplayName("Buscar usuários por nome contendo ")
	@Sql({ "classpath:/resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	public void testFindByNameStartingWithIgnoreCase() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/Usuario");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> userList = response.getBody();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYXZpZCIsImlhdCI6MTY4Nzc4NzI0OCwiZXhwIjoxNjg3Nzg5MDQ4fQ.k-mjUf144LbiBtWqRWdrMl_CZCIdGcTjPVswTEOm4Us";
		headers.set("Autorização", "Bearer" + token);
		assertEquals(2, userList.size());

		UserDTO user = userList.get(0);
		assertEquals(1, user.getId());
		assertEquals("teste1@teste.com.br", user.getEmail());

		UserDTO user2 = userList.get(1);
		assertEquals(2, user2.getId());
		assertEquals("teste2@teste.com.br", user2.getEmail());
	}
	
	@Test
	@DisplayName("Teste delete")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/usuarios/1", HttpMethod.DELETE, null, Void.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW5pZWxsZSIsImlhdCI6MTY4Nzc4ODA3MCwiZXhwIjoxNjg3Nzg5ODcwfQ.QyDSGibIjeWHLy6t_y4sgAnHygMt--Je32FlzXb_Lbk";
		headers.set("Autorização", "Bearer" + token);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	@Test
	@DisplayName("Teste update")
	public void updateTest() {
		UserDTO dto = new UserDTO(1, "Usuario Test", "test@teste.com.br", "123","ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW5pZWxsZSIsImlhdCI6MTY4Nzc4ODA3MCwiZXhwIjoxNjg3Nzg5ODcwfQ.QyDSGibIjeWHLy6t_y4sgAnHygMt--Je32FlzXb_Lbk";
		headers.set("Autorização", "Bearer" + token);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/1", HttpMethod.PUT, requestEntity, UserDTO.class);
		
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
	    
	    ResponseEntity<User> response = rest.getForEntity("/usuarios/email/" + email, User.class);
	    HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYXZpZCIsImlhdCI6MTY4Nzc4NzI0OCwiZXhwIjoxNjg3Nzg5MDQ4fQ.k-mjUf144LbiBtWqRWdrMl_CZCIdGcTjPVswTEOm4Us";
		headers.set("Autorização", "Bearer" + token);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    User user = response.getBody();
	    assertNotNull(user);
	    assertEquals(email, user.getEmail());
	   //user eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYXZpZCIsImlhdCI6MTY4Nzc4NzI0OCwiZXhwIjoxNjg3Nzg5MDQ4fQ.k-mjUf144LbiBtWqRWdrMl_CZCIdGcTjPVswTEOm4Us
	}

	


}