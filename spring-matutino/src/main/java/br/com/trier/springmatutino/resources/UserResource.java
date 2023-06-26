package br.com.trier.springmatutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.domain.dto.UserDTO;
import br.com.trier.springmatutino.services.UserService;

@RestController
@RequestMapping(value = "/usuarios")
public class UserResource {

	@Autowired
	private UserService service;

	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<UserDTO> salvar(@RequestBody UserDTO userDTO){
		User newUser = service.salvar(new User(userDTO));
		return ResponseEntity.ok(newUser.toDto());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> buscaPorCodigo(@PathVariable Integer id) {
		User user = service.findById(id);
		return ResponseEntity.ok(user.toDto());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/email/{email}")
	public ResponseEntity<User> buscaPorEmail(@PathVariable String email) {
		return ResponseEntity.ok(service.findByEmail(email));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<UserDTO>> listarTodos() {
		List<User> lista = service.listAll();
		//return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
		
//		return ResponseEntity.ok(lista.stream()
//				.map((user)-> user.toDto())
//				.toList());
		
		return ResponseEntity.ok(service.listAll().stream()
				.map((user)-> user.toDto())
				.toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO){
		User user = new User(userDTO);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.ok(user.toDto());
	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> buscaPorNomeContains(@PathVariable String name) {
		List<User> lista = service.findByNameStartingWithIgnoreCase(name);
		return ResponseEntity.ok(service.findByNameStartingWithIgnoreCase(name).stream()
				.map((user)-> user.toDto())
				.toList());
	}

}
