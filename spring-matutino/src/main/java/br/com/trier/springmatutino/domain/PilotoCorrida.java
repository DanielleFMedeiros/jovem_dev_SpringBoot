package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.PilotoCorridaDTO;
import br.com.trier.springmatutino.domain.dto.PilotoDTO;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Corrida;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PilotoCorrida {

	@Id
	@Column
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne()
	private Piloto piloto;
	@ManyToOne()
	private Corrida corrida;
	@Column
	private Integer colocacao;

	public PilotoCorrida(PilotoCorridaDTO dto) {
		this(dto.getId(), 
			 new Piloto(dto.getPilotoId(), dto.getPilotoName(), null, null),
			 new Corrida(dto.getCorridaId(), DataUtils.strToZonedDateTime(dto.getCorridaData()), null, null),
			 dto.getColocacao());
	}
	
	public PilotoCorridaDTO toDTO() {
		return new PilotoCorridaDTO(id, 
									piloto.getId(), 
									piloto.getName(), 
									corrida.getId(), 
									DataUtils.zonedDateTimeToStr(corrida.getData()), 
									colocacao);
	}
	
	public PilotoCorrida(PilotoCorridaDTO dto, Piloto piloto, Corrida corrida) {
		this(dto.getId(), piloto, corrida, dto.getColocacao());
	}
}
