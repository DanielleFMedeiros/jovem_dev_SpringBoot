package br.com.trier.springmatutino.services.impl;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.repositories.CampeonatoRepository;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

	@Autowired
	CampeonatoRepository repository;

	private void validaCampeonato(Campeonato campeonato) {
		if(campeonato == null) {
			throw new ViolacaoIntegridade("Campeonato Nulo");
		}
		if(campeonato.getDescricao() == null || campeonato.getDescricao().equals("")) {
			throw new ViolacaoIntegridade("Descrição obrigatória");
		}
	}
	@Override
	public Campeonato salvar(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> obj = repository.findById(id);
		return obj.orElse(null);
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		repository.findById(id).ifPresent(repository::delete);
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
			throw new ViolacaoIntegridade("Intervalo de anos inválido. O ano deve estar entre 1990 e o ano seguinte.");
		}
		return repository.findByAnoBetween(startYear, endYear);
	}

	@Override
	public List<Campeonato> findByAno(Integer ano) {
		return repository.findByAno(ano);
	}

	@Override
	public List<Campeonato> findByDescricaoLike(String descricao) {
		return repository.findByDescricaoLike(descricao);

	}

	@Override
	public boolean validateYear(Integer year) {
		int currentYear = Year.now().getValue();
		return year >= 1990 || year <= currentYear + 1;
	}

}
