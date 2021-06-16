package br.com.builder.cliente.handler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDetailDto {
	private String titulo;
	private Long status;
	private Long timestamp;
	private String mensagemDesenvolvedor;
}
