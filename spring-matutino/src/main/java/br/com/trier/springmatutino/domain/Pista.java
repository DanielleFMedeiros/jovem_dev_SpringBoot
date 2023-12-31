package br.com.trier.springmatutino.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "pista")
public class Pista {

	@Id
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pista")
	private Integer id;

	@Column(name = "tamanho_pista")
	private Integer tamanho;

	@ManyToOne()
	private Pais pais;

	
}
