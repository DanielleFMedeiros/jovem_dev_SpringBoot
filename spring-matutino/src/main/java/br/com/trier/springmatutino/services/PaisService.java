package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Pais;

public interface PaisService {
	Pais salvar (Pais pais);
	List<Pais>listAll();
	Pais findById(Integer id);
	Pais update(Pais pais);
	void delete(Integer id);

}
