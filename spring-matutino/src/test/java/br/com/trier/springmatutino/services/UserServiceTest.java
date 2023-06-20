package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests {

	@Autowired
	UserService userService;

	@Test
	@DisplayName("Teste buscar usuário por ID inexistente")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos erro")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void listAllNonExistTest() {
		List<User> lista = userService.listAll();
		userService.delete(1);
		userService.delete(2);
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.listAll());
		assertEquals("Nenhum usuário encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());

	}

	@Test
	@DisplayName("Teste alterar usuário com email duplicado")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void updateUserWithDuplicateEmailTest() {
		User user = new User(2, "Usuario teste 2", "teste1@teste.com.br", "124");

		var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.update(user));
		assertEquals("E-mail já cadastrado: teste1@teste.com.br", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar usuário por nome que inicia com")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void findUserByNameStartsWithTest() {
		List<User> lista = userService.findByNameStartingWithIgnoreCase("Usuario");
		assertEquals(2, lista.size());
		lista = userService.findByName("Usuario teste 1");
		assertEquals(1, lista.size());

		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findByName("c"));
		assertEquals("Nenhum nome de usuário começa com o nome c", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar usuário")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void UpdateUserTest() {
		var usuario = new User(1, "altera", "altera", "altera");
		userService.update(usuario);
		usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("altera", usuario.getName());
		assertEquals("altera", usuario.getEmail());
		assertEquals("altera", usuario.getPassword());
	}

	@Test
	@DisplayName("Teste excluir usuário inexistente")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void deleteNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findById(20));
		assertEquals("Usuário 20 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste incluir usuário")
	void insertUserTest() {
		var usuario = new User(null, "nome", "email", "senha");
		userService.salvar(usuario);
		usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("nome", usuario.getName());
		assertEquals("email", usuario.getEmail());
		assertEquals("senha", usuario.getPassword());
	}

	@Test
	@DisplayName("Teste incluir usuário com email duplicado")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void insertUserWithDuplicateEmailTest() {
		var usuario = new User(null, "Novo Usuário", "teste1@teste.com.br", "senha");

		var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.salvar(usuario));
		assertEquals("E-mail já cadastrado: teste1@teste.com.br", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar usuário por ID")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void findByIdTest() {
		var usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("Usuario teste 1", usuario.getName());
		assertEquals("teste1@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}

	@Test
	@DisplayName("Teste excluir usuário")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void DeleteUserTest() {
		userService.delete(1);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste alterar usuário inexistente")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void updateUserNonExistentTest() {
		var user = new User(6, "altera", "altera@hotmail", "altera");

		assertThrows(ObjetoNaoEncontrado.class, () -> userService.update(user), "Usuário 6 não encontrado");
	}

	// Outros testes implementados

	@Test
	@DisplayName("Teste salvar usuário")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void salvarTest() {
		var usuario = new User(1, "Usuario teste 1", "teste1@teste.com.br", "123");
		userService.salvar(usuario);
		assertEquals(1, usuario.getId());
		assertEquals("Usuario teste 1", usuario.getName());
		assertEquals("teste1@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}

	@Test
	@DisplayName("Teste buscar usuário por email inválido")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void findByInvalidEmailTest() {
		var email = "email_invalido@teste.com";

		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findByEmail(email));
		assertEquals("Nenhum usuário encontrado com esse email " + email, exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar usuário por email")
	@Sql({ "classpath:/resources/sqls/usuario.sql" })
	void findByEmailTest() {
		var email = "teste1@teste.com.br";

		var usuario = userService.findByEmail(email);

		assertNotNull(usuario);
		assertEquals("Usuario teste 1", usuario.getName());
		assertEquals(email, usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}

}
