package br.com.trier.springmatutino.resources;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

@RestController
@RequestMapping("/relatorios")
public class RelatorioResource {
	@Autowired
	private PaisService paisService;

	@Autowired
	private CampeonatoService campeonatoService;

	@Autowired
	private CorridaService corridaService;

	@Autowired
	private PistaService pistaService;

	@Autowired
	private PilotoService pilotoService;

	@Secured({ "ROLE_USER" })
	@GetMapping("/corridas_by_ano_pais/{ano_campeonato}/{id_pais}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaByPaisAndAno(@PathVariable Integer anoCorrida,
			@PathVariable Integer paisId) {
		Pais pais = paisService.findById(paisId);
		List<CorridaDTO> corridasDTO = pistaService.findByPaisOrderByTamanhoDesc(pais).stream().flatMap(pista -> {
			try {
				return corridaService.findByPista(pista).stream();
			} catch (ObjetoNaoEncontrado e) {
				return Stream.empty();
			}
		}).filter(corrida -> corrida.getData().getYear() == anoCorrida).map(Corrida::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new CorridaPaisAnoDTO(anoCorrida, pais.getName(), corridasDTO));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/corridas_by_campeonato/{campeonato_id}/{ano}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridasPorCampeonato(
			@PathVariable("campeonato_id") Integer campeonatoId, @PathVariable("ano") Integer anoCorrida) {
		Campeonato campeonato = campeonatoService.findById(campeonatoId);
		List<CorridaDTO> corridasDTO = corridaService.findByCampeonato(campeonato).stream()
				.filter(corrida -> corrida.getData().getYear() == anoCorrida).map(Corrida::toDto)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new CorridaPaisAnoDTO(anoCorrida, campeonato.getDescricao(), corridasDTO));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/corridas_por_piloto/{piloto_id}/{anoCorrida}")
	public ResponseEntity<List<CorridaDTO>> findCorridasPorPilotoEAno(@PathVariable("piloto_id") Integer pilotoId,
			@PathVariable("anoCorrida") Integer anoCorrida) {
		Piloto piloto = pilotoService.findById(pilotoId);
		List<CorridaDTO> corridasDTO = corridaService.findByPiloto(piloto).stream()
				.filter(corrida -> corrida.getId() == anoCorrida).collect(Collectors.toList());

		return ResponseEntity.ok(corridasDTO);
	}

}
