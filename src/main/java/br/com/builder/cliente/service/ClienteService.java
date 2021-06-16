package br.com.builder.cliente.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.builder.cliente.controller.dto.ClienteFilter;
import br.com.builder.cliente.controller.dto.ClienteRequest;
import br.com.builder.cliente.model.Cliente;

@Service
public interface ClienteService {
	  
	Cliente create(ClienteRequest request);

	void delete(Long id);

	void update(Long id, ClienteRequest requestPut);

	Cliente findById(Long id);

	Page<Cliente> findAll(ClienteFilter clienteFilter, Pageable paginacao);
}
