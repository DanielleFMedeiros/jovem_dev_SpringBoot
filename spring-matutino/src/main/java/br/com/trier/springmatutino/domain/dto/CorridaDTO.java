package br.com.trier.springmatutino.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CorridaDTO {
	private Integer id;
	private String data;
	private Integer pistaId;
	private Integer campeonatoId;
	private String campeonatoName;
	

	
}
