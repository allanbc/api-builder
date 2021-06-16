package br.com.builder.cliente.controller;

import java.io.IOException;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.builder.cliente.controller.dto.ClienteFilter;
import br.com.builder.cliente.controller.dto.ClienteRequest;
import br.com.builder.cliente.controller.dto.ClienteResponse;
import br.com.builder.cliente.model.Cliente;
import br.com.builder.cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name="Cadastro de clientes", description = "Endpoint para cadastro de clientes")
@Validated
public class ClienteController {
	
	private final ClienteService service;
	
	public ClienteController(ClienteService service) {
		this.service = service;
	}
	
	@Operation(
			summary = "Consulta",
			description = "Consulta",
			parameters = {
					@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization Token", required =true),
					@Parameter(in = ParameterIn.HEADER, name = "name", description = "Client name", required =true),
					@Parameter(in = ParameterIn.HEADER, name = "codigo", description = "Client code", required =true),
					@Parameter(in = ParameterIn.HEADER, name = "status", description = "Client status", required =false),
			},
			tags = {"Find All Clients"}
			)
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(title = "Resource not found"))),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(title = "Missing or invalid request body"))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(title = "Internal Error"))),
    })
	@GetMapping
	public ResponseEntity<Page<ClienteResponse>> findAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "status", required = false) boolean status,
			@PageableDefault(page = 0, size = 10) Pageable paginacao) {
		
		ClienteFilter clienteFilter = ClienteFilter.builder()
				.name(name)
				.codigo(codigo)
				.status(status)
				.build();
		
		Page<Cliente> cliente = service.findAll(clienteFilter, paginacao);
		
		return ResponseEntity.ok()
				.body(ClienteResponse.toCliente(cliente));
	}
	
	@Operation(
			summary = "Consulta",
			description = "Consulta",
			parameters = {
					@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization Token", required =true)
			},
			tags = {"Find by ID Clients"}
			)
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		
		ClienteResponse response = new ClienteResponse(service.findById(id));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@Operation(
			summary = "Cadastro",
			description = "Cadastro",
			parameters = {
					@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization Token", required =true)
			},
			tags = {"Register Clients"}
			)
	@PostMapping
	@Transactional
	public ResponseEntity<ClienteRequest> create(@RequestBody @Valid ClienteRequest request) throws IOException {
		
		Cliente gd = service.create(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).header("id", gd.getId().toString()).build();
	}
	
	@Operation(
			summary = "Update",
			description = "Update",
			parameters = {
					@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization Token", required =true)
			},
			tags = {"Update Clients"}
			)
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClienteRequest requestPut) {
		
		service.update(id, requestPut);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(
			summary = "Delete",
			description = "Delete",
			parameters = {
					@Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization Token", required =true)
			},
			tags = {"Delete Clients"}
			)
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}