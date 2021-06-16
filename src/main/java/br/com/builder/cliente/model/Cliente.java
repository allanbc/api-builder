package br.com.builder.cliente.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.builder.cliente.controller.dto.ClienteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cliente")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cliente")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="codigo")
	private String codigo;
	
	@Column(name="dt_nascimento")
	private LocalDate dataNascimento;
	
	@Column(name="status")
	private boolean status;
	
	private int idade;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "CLIENTE_ENDERECO", 
			   joinColumns = @JoinColumn(name = "ID_CLIENTE"),
			   inverseJoinColumns = @JoinColumn(name = "ID_ENDERECO") 
			  )
	private Set<Endereco> enderecos;
	
	public ClienteResponse toClienteResponse() {
		return ClienteResponse.builder()
				.name(this.name)
				.codigo(this.codigo)
				.idade(this.idade)
				.status(this.status)
				.enderecos(this.enderecos)
				.build();
	}

}
