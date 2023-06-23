package br.com.trier.springmatutino.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoCorridaDTO {
	private Integer id;
	private Integer pilotoId;
	private String pilotoName;
	private Integer corridaId;
	private String corridaData;
	private Integer colocacao;
}
