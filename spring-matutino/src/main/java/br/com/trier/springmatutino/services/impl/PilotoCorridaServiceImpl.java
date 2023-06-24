package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.repositories.PilotoCorridaRepository;
import br.com.trier.springmatutino.services.PilotoCorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PilotoCorridaServiceImpl implements PilotoCorridaService {
	@Autowired
	PilotoCorridaRepository repository;

	@Override
	public PilotoCorrida salvar(PilotoCorrida pilotoCorrida) {
		validaInsert(pilotoCorrida);
		return repository.save(pilotoCorrida);
	}

	private void validaInsert(PilotoCorrida pilotoCorrida) {
		if (pilotoCorrida == null) {
			throw new ViolacaoIntegridade("O cadastro deve conter um registro");
		} else if (pilotoCorrida.getColocacao() == null || pilotoCorrida.getColocacao() == 0) {
			throw new ViolacaoIntegridade("A colocação não pode ser nula ou = 0");
		}
	}

	@Override
	public PilotoCorrida update(PilotoCorrida pilotoCorrida) {

		if (!listAll().contains(pilotoCorrida)) {
			throw new ObjetoNaoEncontrado("Código não encontrado");
		}
		return salvar(pilotoCorrida);
	}

	@Override
	public void delete(Integer id) {
		PilotoCorrida pilotoCorrida = findById(id);
		repository.delete(pilotoCorrida);

	}

	@Override
	public List<PilotoCorrida> listAll() {
		List<PilotoCorrida> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum dado encontrado");
		}
		return lista;
	}

	@Override
	public PilotoCorrida findById(Integer id) {
		Optional<PilotoCorrida> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Piloto Corrida %s não encontrado".formatted(id)));
	}

	
	@Override
	public List<PilotoCorrida> findByPiloto(Piloto piloto) {
		if(repository.findByPiloto(piloto).size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas desse piloto");
		}
		return repository.findByPiloto(piloto);
	}

	@Override
	public List<PilotoCorrida> findByCorrida(Corrida corrida) {
		if(repository.findByCorrida(corrida).size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos nessa corrida");
		}
		return repository.findByCorrida(corrida);
	}

	@Override
	public List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida) {
		if(repository.findByCorridaOrderByColocacaoAsc(corrida).size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos nessa corrida");
		}
		return repository.findByCorridaOrderByColocacaoAsc(corrida);
	}
}
