package br.com.builder.cliente.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.builder.cliente.model.Cliente;
import br.com.builder.cliente.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClienteResponse {
	
	private Long id;
	
	private String name;
	
	private String codigo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	private boolean status;
	
	private Set<Endereco> enderecos;
	
	public ClienteResponse() {
	}
	
	public ClienteResponse(Cliente cliente) {
		this.id = cliente.getId();
		this.name = cliente.getName();
		this.codigo = cliente.getCodigo();
		this.dataNascimento = cliente.getDataNascimento();
		this.status = cliente.isStatus();
		this.enderecos = cliente.getEnderecos().stream().collect(Collectors.toSet());
	}
	
	public static List<ClienteResponse> toCliente(List<Cliente> clientes) {
		return clientes.stream().map(ClienteResponse::new).collect(Collectors.toList());
	}

	public static Page<ClienteResponse> toCliente(Page<Cliente> clientes) {
		return clientes.map(ClienteResponse::new);
	}

}
