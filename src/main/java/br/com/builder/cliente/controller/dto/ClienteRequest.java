package br.com.builder.cliente.controller.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.builder.cliente.model.Cliente;
import br.com.builder.cliente.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
	
	private Long id;
	
	@NotEmpty
	private String name;
	
	private String codigo;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	private boolean status;
	
	private Set<Endereco> enderecos;
	
	public Cliente requestDomainBuilder() {
		return Cliente.builder()
				.name(this.name)
				.codigo(this.codigo)
				.dataNascimento(this.dataNascimento)
				.status(this.status)
				.enderecos(this.enderecos)
				.build();
	}
	
	@Override
	public String toString() {
		return  name + "+" + codigo + "+" + dataNascimento
				+ "+" + status + "+" + enderecos;
	}

}
