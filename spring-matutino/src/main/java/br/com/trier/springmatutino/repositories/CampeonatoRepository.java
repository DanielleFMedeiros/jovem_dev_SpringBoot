package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {
	List<Campeonato> findByDescricao(String descricao);

	List<Campeonato> findByDescricaoStartingWithIgnoreCase(String descricao);


	List<Campeonato> findByAnoBetween(Integer startYear, Integer endYear);

	List<Campeonato> findByAno(Integer ano);

	List<Campeonato> findByDescricaoLike(String descricao);
}
