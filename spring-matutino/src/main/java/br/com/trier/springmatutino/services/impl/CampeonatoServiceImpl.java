package br.com.trier.springmatutino.services.impl;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.repositories.CampeonatoRepository;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

	@Autowired
	private CampeonatoRepository repository;

	private void validaCampeonato(Campeonato campeonato) {
	    if (campeonato == null) {
	        throw new ViolacaoIntegridade("Campeonato nulo");
	    }
	    if (campeonato.getDescricao() == null || campeonato.getDescricao().isEmpty()) {
	        throw new ViolacaoIntegridade("A descrição está vazia");
	    }
	    int anoAtual = Year.now().getValue();
	    if (campeonato.getAno() == null || campeonato.getAno() <= 1990 || campeonato.getAno() >= anoAtual + 1) {
	        throw new ViolacaoIntegridade("O ano precisa ser maior ou igual a 1990 e menor ou igual a "+ anoAtual);
	    }
	}

	@Override
	public Campeonato salvar(Campeonato campeonato) {
		validaCampeonato(campeonato);
		return repository.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
	    List<Campeonato> campeonatos = repository.findAll();
	    if (campeonatos.isEmpty()) {
	        throw new ObjetoNaoEncontrado("Não há campeonatos cadastrados");
	    }
	    return campeonatos;
	}


	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> obj = repository.findById(id);
		return obj.orElse(null);
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		validaCampeonato(campeonato);
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<Campeonato> findByDescricao(String descricao) {
		return repository.findByDescricao(descricao);
	}

	@Override
	public List<Campeonato> findByDescricaoStartingWithIgnoreCase(String descricao) {
		return repository.findByDescricaoStartingWithIgnoreCase(descricao);
	}

	@Override
	public List<Campeonato> findByAnoBetween(Integer startYear, Integer endYear) {
		if (!validateYear(startYear) || !validateYear(endYear)) {
			throw new ViolacaoIntegridade("O ano precisa ser maior que 1990 e menor que 2023");
		}
		return repository.findByAnoBetween(startYear, endYear);
	}

	@Override
	public List<Campeonato> findByAno(Integer ano) {
		return repository.findByAno(ano);
	}
//terminar
	@Override
	public List<Campeonato> findByDescricaoLike(String descricao) {
		if (campeonato.getDescricao() == null || campeonato.getDescricao().isEmpty()) {
	        throw new ViolacaoIntegridade("A descrição está vazia");
	    }
		return repository.findByDescricaoLike(descricao);
	}

	@Override
	public boolean validateYear(Integer year) {
		int currentYear = Year.now().getValue();
		return year >= 1990 && year <= currentYear + 1;
	}
}
