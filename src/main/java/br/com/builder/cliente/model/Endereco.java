package br.com.builder.cliente.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@Table(name="endereco")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Endereco implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_endereco")
	private Long id;
	
	@Column(name="LOGRADOURO")
	private String logradouro;
	
	@Column(name="NUMERO")
	private String numero;
	
	@Column(name="COMPLEMENTO")
	private String complemento;
	
	@Column(name="BAIRRO")
	private String bairro;
	
	@Column(name="CIDADE")
	private String cidade;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="PAIS")
	private String pais;
	
	@Column(name="CEP")
	private String cep;
	
	@Column(name="CORRESPONDENCIA")
	private String correspondencia;
	
	@Column(name="COBRANCA")
	private String cobranca;
	
	@Column(name="ATIVO")
	private boolean ativo;
	
}
