package br.com.trier.springmatutino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

}
