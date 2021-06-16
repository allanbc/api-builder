package br.com.builder.cliente.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("builder")
public class ClienteApiProperty {
	
	@Value("${builder.origin-permitida.domain}")
	private String originPermitida;
	
	private final Seguranca seguranca = new Seguranca();
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

	public static class Seguranca {
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		 
	}
}
