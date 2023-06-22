package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RaceDTO {
	private Integer id;
	private String data;
	private Integer speedwayId;
	private String speedwayName;
	private Integer championshipId;
	private String championshipName;
}
