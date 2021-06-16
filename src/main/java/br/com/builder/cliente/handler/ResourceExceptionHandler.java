package br.com.builder.cliente.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import br.com.builder.cliente.handler.dto.ErrorDetailDto;
import br.com.builder.cliente.service.exception.NotFoundException;

@ControllerAdvice
@RestController
public class ResourceExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetailDto> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ErrorDetailDto error = new ErrorDetailDto();
        error.setStatus(400L);
        error.setTitulo("An error ocurred in the application");
        error.setMensagemDesenvolvedor(ExceptionUtils.getRootCauseMessage(ex));
        error.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
	
	 @ExceptionHandler(NotFoundException.class)
	 public ResponseEntity<ErrorDetailDto> handleRecursoNaoEncontradoException(NotFoundException e, HttpServletRequest request) {

	 	ErrorDetailDto error = new ErrorDetailDto();
        error.setStatus(404L);
        error.setTitulo("The feature is not available.");
        error.setMensagemDesenvolvedor(ExceptionUtils.getRootCauseMessage(e));
        error.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	  }
}
