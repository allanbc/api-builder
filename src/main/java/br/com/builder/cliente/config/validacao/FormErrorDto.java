package br.com.builder.cliente.config.validacao;

public class FormErrorDto {
	
	private String campo;
	private String erro;
	
	public FormErrorDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
	

}
