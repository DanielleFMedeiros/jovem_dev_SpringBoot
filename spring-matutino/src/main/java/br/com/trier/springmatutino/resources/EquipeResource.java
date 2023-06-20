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
		Equipe newEquipe = service.salvar(equipe);
		return newEquipe != null ? ResponseEntity.ok(newEquipe) : ResponseEntity.badRequest().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Equipe> buscaPorCodigo(@PathVariable Integer id) {
		Equipe equipe = service.findById(id);
		return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Equipe>> listarTodos() {
		List<Equipe> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Equipe> update(@PathVariable Integer id, @RequestBody Equipe equipe) {
		equipe.setId(id);
		equipe = service.update(equipe);
		return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
		
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Equipe>> buscarPorNome(@PathVariable String nome) {
	    List<Equipe> equipes = service.findByNameStartingWithIgnoreCase(nome);
	    return equipes.size() > 0 ? ResponseEntity.ok(equipes) : ResponseEntity.noContent().build();
	}

}
