package br.com.builder.cliente.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.github.javafaker.Faker;

import br.com.builder.cliente.controller.dto.ClienteFilter;
import br.com.builder.cliente.controller.dto.ClienteRequest;
import br.com.builder.cliente.controller.dto.ClienteResponse;
import br.com.builder.cliente.model.Cliente;
import br.com.builder.cliente.model.Endereco;
import br.com.builder.cliente.repository.ClienteRepository;
import br.com.builder.cliente.service.ClienteServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {
	
	private Long id = 1L;
	
	private String name;
	
	private String codigo;
	
	private boolean status;
	
	private LocalDate dataNascimento;
	
	private Set<Endereco> enderecos = new HashSet<>();
	
	private Cliente cliente;
	
	@Mock
	private Page<Cliente> clientePage;
	
	@InjectMocks
	private ClienteServiceImpl serviceImpl;
	
	private ClienteRepository repository;
	
	private ClienteFilter filter;
	
	private ClienteResponse response;
	
	private ClienteRequest request;
	
	private ClienteRequest requestPut;
	
	@Mock
	private Faker faker;
	
	private PageRequest paginacao = PageRequest.of(0, 10);
	
	@Mock
	ArgumentCaptor<Cliente> captor;
	
	@BeforeEach
	void setup() {
		//serviceImpl = Mockito.mock(ClienteServiceImpl.class);
		repository = Mockito.mock(ClienteRepository.class);
		filter = Mockito.mock(ClienteFilter.class);
		response = Mockito.mock(ClienteResponse.class);
		request = Mockito.mock(ClienteRequest.class);
		cliente = Mockito.mock(Cliente.class);
		faker = Mockito.mock(Faker.class);
		
		this.name = "Allan";
		this.codigo = "001C02";
		this.dataNascimento = LocalDate.of(1978, 10, 10);
		this.status = true;
		
		Endereco endereco = new Endereco();
		endereco.setId(1L);
		endereco.setAtivo(true);
		endereco.setBairro("Olavo Bilac");
		endereco.setCep("25036240");
		endereco.setCidade("Caxias");
		endereco.setEstado("RJ");
		endereco.setPais("Brasil");
		endereco.setNumero("236");
		this.enderecos.add(endereco);		
		
		Mockito.lenient().when(filter.getName()).thenReturn(this.name);
		Mockito.lenient().when(filter.getCodigo()).thenReturn(this.codigo);
		Mockito.lenient().when(filter.isStatus()).thenReturn(this.status);
		
		request = ClienteRequest.builder()
				.id(id)
				.name(name)
				.codigo(codigo)
				.dataNascimento(dataNascimento)
				.status(status)
				.enderecos(enderecos)
				.build();
		
		Mockito.lenient().when(repository.findAll()).thenReturn(List.of(cliente));
		
		serviceImpl = new ClienteServiceImpl(repository);
		
		filter = ClienteFilter.builder()
				.name(name)
				.codigo(codigo)
				.status(status)
				.build();
	}
	
	@Test
	@DisplayName("Lista todos os clientes")
	void getAllClientesNotThrowException() {
		Mockito.lenient().when(repository.findAll()).thenReturn(List.of(cliente));
		
		Mockito.lenient().when(cliente.toClienteResponse()).thenReturn(response);
		
		Mockito.lenient().when(serviceImpl.findAll(filter, paginacao)).thenReturn(clientePage);
		
		assertThat((clientePage).toList());
	}
	
	@Test
	@DisplayName("Cadastra um cliente com sucesso")
	void postClientWithSucessfully() {
		
		serviceImpl.create(request);
		
		verify(repository).save(request.requestDomainBuilder());
		
		assertThat(cliente).isNotNull();
		
	}
	
	@Test
	@DisplayName("Atualiza um cliente com sucesso")
	void putClientWithSucessfully() {
		codigo = "1234DA";
		
		requestPut = ClienteRequest.builder()
				.id(id)
				.name(name)
				.codigo(codigo)
				.dataNascimento(dataNascimento)
				.status(status)
				.enderecos(enderecos)
				.build();
		
		serviceImpl.create(request);
		
		ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
		
		lenient().when(repository.findById(id)).thenReturn(Optional.empty());
		
		BeanUtils.copyProperties(requestPut, request, "id");
		
		verify(repository).save(captor.capture());
		
		assertThat(captor.getValue()).isNotNull();
		
		assertNotEquals(requestPut.getCodigo(), cliente.getCodigo());
	}
	
	@Test
	@DisplayName("Excluir um cliente com sucesso")
	void deleteClientSucessfully() {
		
		lenient().when(repository.findById(id)).thenReturn(Optional.empty());
		
		serviceImpl.delete(id);
		
		assertThat(cliente.getId()).isZero();
		
	}
}
