package br.com.builder.cliente.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.builder.cliente.controller.dto.ClienteFilter;
import br.com.builder.cliente.controller.dto.ClienteRequest;
import br.com.builder.cliente.model.Cliente;
import br.com.builder.cliente.repository.ClienteRepository;
import br.com.builder.cliente.repository.specification.ClienteSpecification;
import br.com.builder.cliente.service.exception.NotFoundException;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private final ClienteRepository repository;
	
	@Autowired
	public ClienteServiceImpl(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	@CacheEvict(cacheNames = "registerClients", allEntries = true)
	public Cliente create(ClienteRequest request) {
		return repository.save(request.requestDomainBuilder());
	}

	@Override
	public void delete(Long id) {
		Optional<Cliente> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
		}
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = "registerClients", allEntries = true)
	public void update(Long id, ClienteRequest requestPut) {
		
		Cliente cliente = this.findById(id);
		BeanUtils.copyProperties(requestPut, cliente, "id");
	    repository.save(cliente);
	}

	@Override
//	@Cacheable(cacheManager = "redisCacheManager", value="clientes",
//	keyGenerator = "redisKeyGenerator")
	@Cacheable(value = "clientList")
	public Cliente findById(Long id) {
		return  repository.findById(id).orElseThrow(
				() -> new NotFoundException("Client not found"));
	}

	@Override
//	@Cacheable(cacheManager = "redisCacheManager", value="clientes",
//	keyGenerator = "redisKeyGenerator")
	@Cacheable(value = "clientList")
	public Page<Cliente> findAll(ClienteFilter filter, Pageable paginacao) {
		
		Page<Cliente> cliente = this.repository.findAll(ClienteSpecification.buildSpecification(filter), paginacao);
		
		return cliente;
	}

}
