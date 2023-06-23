package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoDTO {
	private Integer id;
	private String name;
	private Integer paisId;
	private String paisName;
	private Integer equipeId;
	private String equipeName;
}
