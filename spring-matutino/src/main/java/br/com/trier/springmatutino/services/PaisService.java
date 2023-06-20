package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Pais;

public interface PaisService {
	Pais salvar(Pais pais);

	List<Pais> listAll();

	List<Pais> findByName(String name);

	List<Pais> findByNameStartingWithIgnoreCase(String name);

	Pais findById(Integer id);

	Pais update(Pais pais);

	void delete(Integer id);

	List<Pais> findByNameLike(String name);

}
