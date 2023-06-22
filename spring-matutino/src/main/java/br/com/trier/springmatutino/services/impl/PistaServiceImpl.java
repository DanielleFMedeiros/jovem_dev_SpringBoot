package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.PaisRepository;
import br.com.trier.springmatutino.repositories.PistaRepository;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PistaServiceImpl implements PistaService {

	@Autowired
	private PistaRepository repository;

	private void validarPista(Pista pista) {
		if(pista == null) {
			throw new ViolacaoIntegridade("A pista está nula");
		} else if(pista.getTamanho() == null || pista.getTamanho() <= 0) {
			throw new ViolacaoIntegridade("Tamanho inválido");
		}
	}

	@Override
	public Pista salvar(Pista pista) {
		validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public Pista update(Pista pista) {
		findById(pista.getId());
		validarPista(pista);

		return repository.save(pista);
	}

	@Override
	public void delete(Integer id) {
		Pista pista = findById(id);
		repository.delete(pista);

	}

	@Override
	public List<Pista> listAll() {
		List<Pista> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista foi cadastrada");
			
		}
		return lista;
	}

	@Override
	public Pista findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjetoNaoEncontrado("Pista id %s não existe".formatted(id)));
	}

	@Override
	public List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal) {
		List<Pista> lista = repository.findByTamanhoBetween(tamInicial,tamFinal);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada com os tamanhos %s e %s cadastrada".formatted(tamInicial, tamFinal));

		}
		return lista;
	}

	@Override
	public List<Pista> findByPaisOrderByTamanhoDesc(Pais pais) {
		List<Pista> lista = repository.findByPaisOrderByTamanhoDesc(pais);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pistas desse pais");
		}
		return lista;
	}


}
