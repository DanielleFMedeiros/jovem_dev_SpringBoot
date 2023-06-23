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

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.utils.DataUtils;

@RestController
@RequestMapping(value = "/corrida")
public class CorridaResource {

	@Autowired
	private CorridaService service;

	@Autowired
	private CampeonatoService campeonatoService;

	@Autowired
	private PistaService pistaService;

	@PostMapping
	public ResponseEntity<CorridaDTO> salvar(@RequestBody CorridaDTO corridaDTO) {
		return ResponseEntity
				.ok(service.salvar(new Corrida(corridaDTO, campeonatoService.findById(corridaDTO.getCampeonatoId()),
						pistaService.findById(corridaDTO.getPistaId()))).toDto());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corridaDTO) {
		Corrida corrida = new Corrida(corridaDTO, campeonatoService.findById(corridaDTO.getCampeonatoId()),
				pistaService.findById(corridaDTO.getPistaId()));
		corrida.setId(id);

		return ResponseEntity.ok(service.update(corrida).toDto());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<CorridaDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((corrida) -> corrida.toDto()).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CorridaDTO> buscaPorCodigo(@PathVariable Integer id) {
		Corrida corrida = service.findById(id);
		return ResponseEntity.ok(corrida.toDto());
	}

	@GetMapping("/data/{data}")
	public ResponseEntity<List<CorridaDTO>> buscaPorData(@PathVariable String data) {
		return ResponseEntity.ok(service.findByData(DataUtils.strToZonedDateTime(data)).stream().map((corrida)->corrida.toDto()).toList());
	}

	@GetMapping("/datas/{dataInicio}/{dataFinal}")
	public ResponseEntity<List<CorridaDTO>> buscaPorDataEntre(@PathVariable String dataInicio, @PathVariable String dataFinal) {
		
		return ResponseEntity
				.ok(service.findByDataBetween(DataUtils.strToZonedDateTime(dataInicio), DataUtils.strToZonedDateTime(dataFinal)).stream().map((corrida) -> corrida.toDto())
						   .toList());
	}

	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<CorridaDTO>> findByPista(@PathVariable Integer idPista){
		return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista)).stream().map((corrida) -> corrida.toDto()).toList());
	}
	
	@GetMapping("/campeonato/{idCampeonato}")
	public ResponseEntity<List<CorridaDTO>> findByCampeonato(@PathVariable Integer idCampeonato){
		return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato)).stream().map((corrida) -> corrida.toDto()).toList());
	}

}
