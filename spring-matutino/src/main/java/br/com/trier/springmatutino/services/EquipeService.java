package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Equipe;

public interface EquipeService {
	Equipe salvar(Equipe equipe);

	List<Equipe> listAll();

	List<Equipe> findByName(String name);

	List<Equipe> findByNameStartingWithIgnoreCase(String name);

	Equipe findById(Integer id);

	Equipe update(Equipe equipe);

	void delete(Integer id);

}
