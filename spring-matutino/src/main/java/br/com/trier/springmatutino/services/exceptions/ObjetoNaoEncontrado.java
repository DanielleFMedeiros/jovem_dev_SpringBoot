package br.com.trier.springmatutino.services.exceptions;

public class ObjetoNaoEncontrado extends RuntimeException{
	public ObjetoNaoEncontrado(String mensagem) {
		super(mensagem);
	}
}
