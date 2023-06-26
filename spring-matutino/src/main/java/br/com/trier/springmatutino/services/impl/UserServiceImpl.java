package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.repositories.UserRepository;
import br.com.trier.springmatutino.services.UserService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Configuration
	public class AppConfig {
	    @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
	}


	private void findByEmail(User obj) {
		Optional<User> userOptional = repository.findByEmail(obj.getEmail());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getId() != obj.getId()) {
				throw new ViolacaoIntegridade("E-mail já cadastrado: %s".formatted(obj.getEmail()));
			}
		}
	}

	@Override
	public User salvar(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		List<User> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum usuário encontrado");
		}
		return lista;
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User update(User user) {
		Optional<User> existingUser = repository.findById(user.getId());
		if (existingUser.isEmpty()) {
			throw new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(user.getId()));
		}
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public List<User> findByName(String name) {
		List<User> lista = repository.findByNameStartingWithIgnoreCase(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de usuário começa com o nome " + name);
		}
		return lista;
	}

	@Override
	public List<User> findByNameStartingWithIgnoreCase(String name) {
		List<User> users = repository.findByNameStartingWithIgnoreCase(name);

		if (users.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum usuário encontrado com o nome iniciando por: " + name);
		}

		return users;
	}

	@Override
	public User findByEmail(String email) {
		User user = repository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new ObjetoNaoEncontrado("Nenhum usuário encontrado com esse email " + email);
		}
		return user;
	}

}
