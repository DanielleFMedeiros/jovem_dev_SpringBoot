package br.com.trier.springmatutino.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PistaService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioResource {
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PistaService pistaService;
	
	@Autowired
	private CorridaService corridaService;
	
	//@GetMapping()
	//equipe//pais
}
