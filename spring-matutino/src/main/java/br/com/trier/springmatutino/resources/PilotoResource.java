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

import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.dto.PilotoDTO;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PilotoService;

@RestController
@RequestMapping(value = "pilotos")
public class PilotoResource {

	@Autowired
	private PilotoService service;
	@Autowired
	private EquipeService equipeService;
	@Autowired
	private PaisService paisService;

	@PostMapping
	public ResponseEntity<PilotoDTO> salvar(@RequestBody PilotoDTO pilotoDTO) {
		equipeService.findById(pilotoDTO.getEquipeId());
		paisService.findById(pilotoDTO.getPaisId());
		return ResponseEntity.ok(service.salvar(new Piloto(pilotoDTO)).toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PilotoDTO> update(@RequestBody PilotoDTO pilotoDTO, @PathVariable Integer id) {
		equipeService.findById(pilotoDTO.getEquipeId());
		paisService.findById(pilotoDTO.getPaisId());
		Piloto piloto = new Piloto(pilotoDTO);
		piloto.setId(id);
		return ResponseEntity.ok(service.update(piloto).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<PilotoDTO>> listarTodos() {
		return ResponseEntity.ok(service.listAll().stream().map((piloto) -> piloto.toDTO()).toList());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();

	}

	@GetMapping("/{id}")
	public ResponseEntity<PilotoDTO> buscarPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<PilotoDTO>> findByNameContainsIgnoreCaseOrderByName(@PathVariable String nome){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCaseOrderByName(nome).stream().map((piloto) -> piloto.toDTO()).toList());
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<PilotoDTO>> findByPais(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByPais(paisService.findById(id)).stream().map((piloto) -> piloto.toDTO()).toList());
	}
	
	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<PilotoDTO>> findByEquipe(@PathVariable Integer id){
		return ResponseEntity.ok(service.findByEquipe(equipeService.findById(id)).stream().map((piloto) -> piloto.toDTO()).toList());
	}
	

}
