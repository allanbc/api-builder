package br.com.builder.cliente.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.builder.cliente.controller.dto.ClienteFilter;
import br.com.builder.cliente.model.Cliente;

public interface AdvancedClienteSearch {
	
	Page<Cliente> findAll(ClienteFilter filter, Pageable pageable);
}
