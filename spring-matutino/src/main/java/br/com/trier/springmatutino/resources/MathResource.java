package br.com.trier.springmatutino.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calc")
public class MathResource {
	@GetMapping("/somar")
	public Integer somar(@RequestParam(name = "n1") Integer n1, @RequestParam(name = "n2") Integer n2) {
		return n1 + n2;
	}

	@GetMapping("/mult/{n1}/{n2}")
	public Integer multiplicar(@PathVariable(name = "n1") Integer n1, @PathVariable(name = "n2") Integer n2) {
		return n1 * n2;
	}

	// http://localhost:8080/calc/sub/15/5
	@GetMapping("/sub/{n1}/{n2}")
	public Integer diminuir(@PathVariable(name = "n1") Integer n1, @PathVariable(name = "n2") Integer n2) {
		return n1 - n2;
	}

	// http://localhost:8080/calc/div?n1=500&n2=50
	@GetMapping("/div")
	public Integer dividir(@RequestParam(name = "n1") Integer n1, @RequestParam(name = "n2") Integer n2) {
		return n1 / n2;
	}

}
