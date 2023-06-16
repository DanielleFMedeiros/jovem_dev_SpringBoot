package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.User;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{

	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste buscar usuário por ID")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		var usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("Usuario teste 1", usuario.getName());
		assertEquals("teste@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());		
	}
	
	@Test
	@DisplayName("Teste incluir usuário")
	void insertUserTest() {
		var usuario = new User(null, "nome", "email", "senha");
		userService.salvar(usuario);
		usuario=userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("nome", usuario.getName());
		assertEquals("email", usuario.getEmail());
		assertEquals("senha", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste alterar usuário")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void UpdateUserTest() {
		var usuario = new User(1, "altera", "altera", "altera");
		userService.update(usuario);
		usuario=userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("altera", usuario.getName());
		assertEquals("altera", usuario.getEmail());
		assertEquals("altera", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste deletar usuário")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void DeleteUserTest() {
		userService.delete(1);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste buscar usuário por nome que inicia com")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findUserByNameStartingWithTest() {
		List<User> lista = userService.findByName("u");
		assertEquals(1, lista.size());
		lista = userService.findByName("usuario teste 2");
		assertEquals(2, lista.size());
		lista = userService.findByName("c");
		assertEquals(0, lista.size());
	}

}
