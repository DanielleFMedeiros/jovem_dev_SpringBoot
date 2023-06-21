package br.com.trier.springmatutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.services.EquipeService;

/*
 * FIXME: buscar por nome de equipe com contains e ignorecase.
 */
@RestController
@RequestMapping(value = "equipes")
public class EquipeResource {

	@Autowired
	private EquipeService service;

	@PostMapping
	public ResponseEntity<Equipe> insert(@RequestBody Equipe equipe) {
		return ResponseEntity.ok(service.salvar(equipe));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Equipe> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Equipe>> listarTodos() {
		return ResponseEntity.ok(service.listAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Equipe> update(@PathVariable Integer id, @RequestBody Equipe equipe) {
		return ResponseEntity.ok(service.update(equipe));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();

	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Equipe>> buscarPorNome(@PathVariable String nome) {
		return ResponseEntity.ok(service.findByNameLike(nome));
	}
	
	@GetMapping("/like/{nome}")
	public ResponseEntity<List<Equipe>> findByNameContainingIgnoreCase(@PathVariable String nome){
		return ResponseEntity.ok(service.findByNameContainingIgnoreCase(nome));
	}

}
