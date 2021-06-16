package br.com.builder.cliente.repository.specification;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import br.com.builder.cliente.controller.dto.ClienteFilter;
import br.com.builder.cliente.model.Cliente;

public class ClienteSpecification {
	
	
	public static Specification<Cliente> likeName(String name) {
       return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), name);
    }

    public static Specification<Cliente> equalCodigo(String codigo) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("codigo"), codigo);
    }

    public static Specification<Cliente> equalStatus(boolean status) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
    
    public static Specification<Cliente> noCondition() {
        return null;
    }
    
	public static Specification<Cliente> buildSpecification(ClienteFilter filter) {
		
		Specification<Cliente> specification = Objects.nonNull(filter.getName()) ?
				Specification.where(ClienteSpecification.likeName("%" + filter.getName().toUpperCase() + "%")) :
					Specification.where(ClienteSpecification.noCondition());

        if (Objects.nonNull(filter.getCodigo())) {
            specification = specification.and(ClienteSpecification.equalCodigo(filter.getCodigo()));
        }

        if (filter.isStatus()) {
            specification = specification.and(ClienteSpecification.equalStatus(filter.isStatus()));
        }
        
		return specification;
	}
}
