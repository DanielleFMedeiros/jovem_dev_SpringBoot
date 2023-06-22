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

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.services.CampeonatoService;

@RestController
@RequestMapping(value = "/campeonatos")
public class CampeonatoResource {

	@Autowired
	private CampeonatoService service;

	@PostMapping
	public ResponseEntity<Campeonato> insert(@RequestBody Campeonato campeonato) {
		return ResponseEntity.ok(service.salvar(campeonato));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Campeonato> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Campeonato>> listarTodos() {
		return ResponseEntity.ok(service.listAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Campeonato> update(@PathVariable Integer id, @RequestBody Campeonato campeonato) {
		return ResponseEntity.ok(service.update(campeonato));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/ano/{ano}")
	public ResponseEntity<List<Campeonato>> findByAno(@PathVariable Integer ano) {
		return ResponseEntity.ok(service.findByAno(ano));
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Campeonato>> findByDescricaoStartingIgnoreCase(@PathVariable String descricao) {
	    return ResponseEntity.ok(service.findByDescricaoStartingWithIgnoreCase(descricao));
	}


	@GetMapping("/anos/{startYear}/{endYear}")
	public ResponseEntity<Object> findByAnoBetween(@PathVariable Integer startYear, @PathVariable Integer endYear) {
		return ResponseEntity.ok(service.findByAnoBetween(startYear, endYear));
	}
	
	@GetMapping("/like/{descricao}")
	public ResponseEntity<List<Campeonato>> findByDescricaoLike(@PathVariable String descricao){
		return ResponseEntity.ok(service.findByDescricaoLike(descricao));
	}
	
}
