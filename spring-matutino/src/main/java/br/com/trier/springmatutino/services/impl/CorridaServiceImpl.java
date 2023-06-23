package br.com.trier.springmatutino.services.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.CorridaRepository;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
@Service
public class CorridaServiceImpl implements CorridaService{
	@Autowired
	private CorridaRepository repository;
	
	@Override
	public Corrida salvar(Corrida corrida) {
		validaCorrida(corrida);
		return repository.save(corrida);
	}
	
	private void validaCorrida(Corrida corrida) {
		if(corrida == null) {
			throw new ObjetoNaoEncontrado("A corrida está vazia");
		} else if(corrida.getData() == null) {
			throw new ObjetoNaoEncontrado("A data está vazia");
		}
		validarData(corrida);
	}
	
	private void validarData(Corrida corrida) {
		int anoCampeonato = (corrida.getCampeonato().getAno());
		if(corrida.getData().getYear() != anoCampeonato) {
			throw new ViolacaoIntegridade("O ano da corrida precisa ser igual ao ano do campeonato");
		}
	}

	@Override
	public Corrida update(Corrida corrida) {
		if(!listAll().contains(corrida)) {
			throw new ObjetoNaoEncontrado("Corrida não existe");
		}
		return salvar(corrida);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
		
	}

	@Override
	public List<Corrida> listAll() {
		if(repository.findAll().size() == 0) {
			throw new ObjetoNaoEncontrado("Corridas não foram cadastradas");
		}
		return repository.findAll();
	}

	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Corrida id %s não existe".formatted(id)));

	}

	@Override
	public List<Corrida> findByDataBetween(ZonedDateTime dataInicio, ZonedDateTime dataFim) {
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		List<Corrida> corridas = repository.findByDataBetween(dataInicio, dataFim);
		if(corridas.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas entre as datas %s e %s".formatted(
										  formatacao.format(dataInicio), formatacao.format(dataFim)));
		}
		return corridas;
	}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> corridas = repository.findByPista(pista);
		if(corridas.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas nessa pista: " + pista.getId());
		}
		return corridas;
	}

	@Override
	public List<Corrida> findByData(ZonedDateTime data) {
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		if(repository.findByData(data).size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas na data: " + formatacao.format(data));
		}
		return repository.findByData(data);
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> corridas = repository.findByCampeonato(campeonato);
		if(corridas.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas no: " + campeonato.getDescricao());
		}
		return corridas;
	}

}
