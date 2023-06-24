package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;

public interface PilotoCorridaService {
	PilotoCorrida salvar(PilotoCorrida pilotoCorrida);

	PilotoCorrida update(PilotoCorrida pilotoCorrida);

	void delete(Integer id);

	List<PilotoCorrida> listAll();

	PilotoCorrida findById(Integer id);

	List<PilotoCorrida> findByPiloto(Piloto piloto);

	List<PilotoCorrida> findByCorrida(Corrida corrida);

	List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida);

}
