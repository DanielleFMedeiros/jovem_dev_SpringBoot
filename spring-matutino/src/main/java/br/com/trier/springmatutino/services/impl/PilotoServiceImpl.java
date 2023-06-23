package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.repositories.PilotoRepository;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
@Service
public class PilotoServiceImpl implements PilotoService {

	@Autowired
	 PilotoRepository repository;

	@Override
	public Piloto salvar(Piloto piloto) {
		validarPiloto(piloto);
		return repository.save(piloto);
	}

	private void validarPiloto(Piloto piloto) {
		if(piloto == null) {
			throw new ViolacaoIntegridade("O piloto está vazio");
			
		}else if(piloto.getName() == null || piloto.getName().isEmpty()) {
			throw new ObjetoNaoEncontrado("Preencha corretamente o nome do piloto");
		}
		
	}

	@Override
	public Piloto update(Piloto piloto) {
		if(!listAll().contains(piloto)) {
			throw new ObjetoNaoEncontrado("Piloto não encontrado");
		}
		return salvar(piloto);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
		
	}

	@Override
	public List<Piloto> listAll() {
		if(repository.findAll().size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos cadastrados");
		}
		return repository.findAll();
	}

	@Override
	public Piloto findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Piloto id %s não existe".formatted(id)));
	}

	@Override
	public List<Piloto> findByNameContainsIgnoreCaseOrderByName(String name) {
		List<Piloto> pilotos = repository.findByNameContainsIgnoreCaseOrderByName(name);
		if(pilotos.size() == 0) {
			throw new ObjetoNaoEncontrado("Piloto " + name + " não encontrado");
		}
		return pilotos;
	}

	@Override
	public List<Piloto> findByEquipe(Equipe equipe) {
		List<Piloto> pilotos = repository.findByEquipe(equipe);
		if(pilotos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos do(a) equipe " + equipe.getName());
		}
		return pilotos;
	}

	@Override
	public List<Piloto> findByPais(Pais pais) {
		List<Piloto> pilotos = repository.findByPais(pais);
		if(pilotos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos desse país: " + pais.getName());
		}
		return pilotos;
	}
	
	

}
