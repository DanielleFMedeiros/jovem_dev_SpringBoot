package br.com.trier.springmatutino.services;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;

public interface CorridaService {
	Corrida salvar(Corrida corrida);

	Corrida update(Corrida corrida);

	void delete(Integer id);

	List<Corrida> listAll();

	Corrida findById(Integer id);

	List<Corrida> findByDataBetween(ZonedDateTime dataInicio, ZonedDateTime dataFim);

	List<Corrida> findByPista(Pista pista);

	List<Corrida> findByData(ZonedDateTime data);

	List<Corrida> findByCampeonato(Campeonato campeonato);



}
