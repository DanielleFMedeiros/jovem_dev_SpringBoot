package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.repositories.EquipeRepository;
import br.com.trier.springmatutino.services.EquipeService;

@Service
public class EquipeServiceImpl implements EquipeService {

	@Autowired
	//posso utilizar instancia que está na memória?
	EquipeRepository repository;
	
	@Override
	public Equipe salvar(Equipe equipe) {
		return repository.save(equipe);
	}

	@Override
	public List<Equipe> listAll() {
		return repository.findAll();
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> obj = repository.findById(id);
		return obj.orElse(null);
	}

	@Override
	public Equipe update(Equipe equipe) {
		return repository.save(equipe);
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
		if(equipe != null) {
			repository.delete(equipe);
		}
		
	} 

}
