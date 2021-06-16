package br.com.builder.cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteFilter {
	
	private String name;
	
	private String codigo;
	
	private boolean status;
}
