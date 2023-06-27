package br.com.trier.springmatutino.domain.dto;

import java.util.List;

import br.com.trier.springmatutino.domain.Campeonato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CorridaPaisAnoDTO {
	public CorridaPaisAnoDTO(Integer campeonatoId, Campeonato campeonato, List<CorridaDTO> corridasDTO) {
		// TODO Auto-generated constructor stub
	}
	private Integer ano;
	private String pais;
	private List<CorridaDTO> corridas;
}
