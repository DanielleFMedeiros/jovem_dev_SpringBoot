package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Campeonato;

public interface CampeonatoService {
	Campeonato salvar (Campeonato campeonato);
	List<Campeonato>listAll();
	List<Campeonato> findByDescricao(String descricao);
	Campeonato findById(Integer id);
	Campeonato update(Campeonato campeonato);
	void delete(Integer id);
	List<Campeonato> findByDescricaoStartingWithIgnoreCase(String descricao);
	 List<Campeonato> findByAnoBetween(Integer startYear, Integer endYear);

}
