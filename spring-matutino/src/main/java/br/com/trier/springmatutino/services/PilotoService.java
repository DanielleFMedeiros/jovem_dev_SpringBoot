package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;

public interface PilotoService {

	Piloto salvar(Piloto piloto);

	Piloto update(Piloto piloto);

	void delete(Integer id);

	List<Piloto> listAll();

	Piloto findById(Integer id);

	List<Piloto> findByNameContainsIgnoreCaseOrderByName(String name);

	List<Piloto> findByEquipe(Equipe equipe);

	List<Piloto> findByPais(Pais pais);

}
