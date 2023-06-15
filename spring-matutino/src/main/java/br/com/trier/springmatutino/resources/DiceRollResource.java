package br.com.trier.springmatutino.resources;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dados")
public class DiceRollResource {

    @GetMapping("/{qnt}/{aposta}")
    public String verificaAposta(@PathVariable("qnt") int qnt, @PathVariable("aposta") int aposta) {
        if (qnt < 1 || qnt > 4) {
            return "Escolha entre 1 e 4 dados.";
        }

        if (qnt * 6 < aposta) {
            return "O número apostado deve condizer com algum possível resultado.";
        }

        List<Integer> resultado = new ArrayList<>();
        Random random = new Random();
        int soma = 0;

        for (int i = 0; i < qnt; i++) {
            int numeroAleatorio = random.nextInt(6) + 1;
            resultado.add(numeroAleatorio);
            soma += numeroAleatorio;
        }

        double porcentagem = (soma / (double) aposta) * 100;

        DecimalFormat df = new DecimalFormat("#.00");
        String porcentagemFormatada = df.format(porcentagem);

        String response = "O número apostado foi: " + aposta + "\n" +
                "O resultado dos " + qnt + " dados foi: " + resultado + "\n" +
                "A soma dos números sorteados foi: " + soma + "\n" +
                "Percentual em relação ao sorteio: " + porcentagemFormatada + "%";

        return response;
    }
}
