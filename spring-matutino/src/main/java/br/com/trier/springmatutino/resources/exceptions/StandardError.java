package br.com.trier.springmatutino.resources.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StandardError {
	private LocalDateTime time;
	private Integer status;
	private String erro;
	private String url;
}
