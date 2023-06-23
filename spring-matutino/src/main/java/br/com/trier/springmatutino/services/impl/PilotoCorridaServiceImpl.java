package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.repositories.PilotoCorridaRepository;
import br.com.trier.springmatutino.repositories.PilotoRepository;
import br.com.trier.springmatutino.services.PilotoCorridaService;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PilotoCorridaServiceImpl implements PilotoCorridaService {
	@Autowired
	PilotoCorridaRepository repository;
	
	@Override
	public PilotoCorrida salvar(PilotoCorrida pilotoCorrida) {
		return repository.save(pilotoCorrida);
	}

	@Override
	public PilotoCorrida update(PilotoCorrida pilotoCorrida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PilotoCorrida> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PilotoCorrida findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PilotoCorrida> findByPiloto(Piloto piloto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PilotoCorrida> findByCorrida(Corrida corrida) {
		// TODO Auto-generated method stub
		return null;
	}



}
