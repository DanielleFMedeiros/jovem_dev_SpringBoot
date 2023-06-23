package br.com.trier.springmatutino.domain;


import java.time.ZonedDateTime;

import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.utils.DataUtils;
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

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Corrida {
	
	@Id
	@Setter
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private ZonedDateTime data;
	
	@ManyToOne
	private Pista pista;
	
	@ManyToOne
	private Campeonato campeonato;
	
	public Corrida(CorridaDTO dto) {
		this(dto.getId(),DataUtils.strToZonedDateTime(dto.getData()),
				new Pista(dto.getPistaId(), null,null),
				new Campeonato(dto.getCampeonatoId(), dto.getCampeonatoName(),null));
	}
	
	public Corrida(CorridaDTO dto, Campeonato campeonato, Pista pista) {
		this(dto.getId(), DataUtils.strToZonedDateTime(dto.getData()), pista, campeonato);
	}
	
	public CorridaDTO toDto() {
		return new CorridaDTO(id, DataUtils.zonedDateTimeToStr(data), pista.getId(), campeonato.getId(), campeonato.getDescricao());
	}
	
	
}