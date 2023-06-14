package br.com.trier.springmatutino.resources;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sort")
public class ExercicioResource {

	public static void input(){
		ArrayList<Integer> numeros = new ArrayList<>();

		String input = JOptionPane.showInputDialog("Quantos dados você deseja lançar?");
		int numero = Integer.parseInt(input);
		numeros.add(numero);
		
		String input2 = JOptionPane.showInputDialog("Valor da Aposta?");
		int valor = Integer.parseInt(input2);

		JOptionPane.showMessageDialog(null, "Quantidade de números digitados: " + numeros.size()+ "valor da aposta: " + valor);
	}


	@GetMapping("/retorno")
	public Integer sort(@RequestParam(name = "numero") Integer numero, @RequestParam(name = "dado2") Integer dado2) {
		String mensagem = JOptionPane.showMessageDialog("", dado2);
		return dado1 + dado2;
	}
	// http://localhost:8080/calc/div?n1=500&n2=50
	public Integer somar(@RequestParam(name = "dado1") Integer dado1, @RequestParam(name = "dado2") Integer dado2) {
		String mensagem = JOptionPane.showMessageDialog("", dado2);
		return dado1 + dado2;
	}
}
