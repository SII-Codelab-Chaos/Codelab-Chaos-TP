package fr.sii.atlantique.fusiion.fusiion_gestion_competences.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.DefautException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.ExceptionResponse;

@ControllerAdvice
public class ResponseCheckExeptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseCheckExeptionHandler.class);
	
	/*permet de retourner une reponse de type AccesDeniedException
	 * @param exeption, l'exception AccesDeniedException
	 * @param request, la requête qui a générée l'exception
	 * @return ResponseEntity<ExceptionResponse>, la reponse http de type 403 forbidden
	 * */
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	@ExceptionHandler(AccessDeniedException.class)
	public ExceptionResponse handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),HttpStatus.FORBIDDEN.value(),"AccessDeniedException", ex.getMessage(),
				request.getDescription(false));
			LOGGER.error("Exception Access: {} ", exceptionResponse);
			return exceptionResponse;
	}
	
	/*permet de retourner une reponse de type différente en fonction du type d'exception levé par le controller
	 * @param exeption, l'exception levée
	 * @param request, la requête qui a générée l'exception
	 * @return ResponseEntity<ExceptionResponse>, la reponse http
	 * */
	@ExceptionHandler(DefautException.class)
	public final ResponseEntity<ExceptionResponse> handleDefautException(DefautException exeption, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(LocalDateTime.now(), exeption.getCodeErreur(),exeption.getResourceId(), exeption.getMessage(),
						request.getDescription(false));
		LOGGER.error("{} : {} ",exeption.getResourceId(), exceptionResponse);
	    return genererReponse(exceptionResponse);
	  }
	
	/*permet de retourner une erreur bad_request pour toutes les exceptions de type ConstraintViolationException de hibernate validator sur les paths
	 * @param exeption, l'exception levée
	 * @param request, la requête qui a générée l'exception
	 * */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public final ExceptionResponse handleConstraintViolationException(ConstraintViolationException exeption, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),"ConstraintViolationException", exeption.getMessage(),
						request.getDescription(false));
		LOGGER.error("{} : {} ","ConstraintViolationException", exceptionResponse);
	    return exceptionResponse;
	  }

	/*permet de retourner une erreur 500 pour toutes les runtimeException
	 * @param exeption, l'exception levée
	 * @param request, la requête qui a générée l'exception
	 * */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
	@ExceptionHandler({ RuntimeException.class })
    public ExceptionResponse handleRuntimeException(RuntimeException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),"RuntimeException", 
						ex.getMessage(),
						request.getDescription(false));
		LOGGER.error("{} : {} ","RuntimeException", exceptionResponse);
		exceptionResponse.setErrorMessage("Une erreur interne est survenue, merci de réessayer plus tard");
	    return exceptionResponse;
    }
	 
	/*fonction permettant de définir le status de la reponse qui sera retournée
	 * @param exceptionResponse, le body de la réponse conetenant un status
	 * @return ResponseEntity<ExceptionResponse>, la reponse http
	 * */
	private ResponseEntity<ExceptionResponse> genererReponse(ExceptionResponse exceptionResponse){
		Optional<HttpStatus> httpStatus = Optional.ofNullable(HttpStatus.resolve(exceptionResponse.getStatus()));
		return new ResponseEntity<>(exceptionResponse,httpStatus.orElse(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
}
