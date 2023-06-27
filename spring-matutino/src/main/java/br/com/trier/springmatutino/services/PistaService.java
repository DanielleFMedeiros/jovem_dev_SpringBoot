package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;

public interface PistaService {
	Pista salvar(Pista pista);

	Pista update(Pista pista);

	void delete(Integer id);

	List<Pista> listAll();

	Pista findById(Integer id);

	List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal);

	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);

	List<Pista> findByPais(Pais pais);

}
