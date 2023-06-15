package br.com.trier.springmatutino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

} 
