package eu.vrtime.restapi.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import eu.vrtime.restapi.exceptions.BookIdMismatchException;
import eu.vrtime.restapi.exceptions.BookNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({BookNotFoundException.class})
	protected ResponseEntity<Object> handleNotFound(
			Exception ex, WebRequest request) {
		return handleExceptionInternal(ex,"Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({BookIdMismatchException.class})
	protected ResponseEntity<Object> handleBadRequest(
			Exception ex, WebRequest request){
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
