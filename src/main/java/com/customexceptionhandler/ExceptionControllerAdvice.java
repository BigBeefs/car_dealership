package com.customexceptionhandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.customException.DealershipAlreadyExistsException;
import com.customException.DealershipTeapot;

import jakarta.validation.ConstraintViolationException;

/*
 * Per customizzare la response in base al tipo di eccezione sollevata.
 * 
 * N.B. Nelle ultime versioni di Spring sono gia' gestite automaticamente
 * alcune eccezioni, tra cui MethodArgumentNotValidException. Se non si vuole
 * customizzare il body della response per questa ed altre eccezioni, NON c'e' bisogno
 * di creare ne' questa classe ne' la classe di supporto/template ApiError 
 */

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	// Gestiamo l'eccezione nel caso in cui la Dealership da aggiorna non esista nel DB
	@ExceptionHandler(DealershipTeapot.class)
	public ResponseEntity<Object> handleRecordNotFound(DealershipTeapot ex) {

		Map<String, String> errors = new HashMap<>();
		ApiError apiError = new ApiError(HttpStatus.I_AM_A_TEAPOT, ex, ex.getMessage(), errors);

		return new ResponseEntity<>(apiError, apiError.getHttpStatus());

	}

	// Gestiamo l'eccezione nel caso in cui la Dealership da inserire esista gia' nel DB
	@ExceptionHandler(DealershipAlreadyExistsException.class)
	public ResponseEntity<Object> HandleDealershipAlreadyExists(DealershipAlreadyExistsException ex) {

		Map<String, String> errors = new HashMap<>();
		ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex, ex.getMessage(), errors);

		return new ResponseEntity<>(apiError, apiError.getHttpStatus());

	}

	// Gestiamo l'eccezione nel caso in cui la regex non corrisponde

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(ConstraintViolationException ex, WebRequest request) {
		String errorMessage = "Errore di violazione del vincolo: " + ex.getMessage();
		return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/*
	 * Metodo che cattura l'eccezione MethodArgumentNotValidException e customizza la response
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value()); // Codice di eccezione che si verifica (Bad request = 400) che metto ANCHE
		// nel body
		body.put("message", ex.getMessage()); // il messaggio dell'eccezione
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}

/*
 * OPPURE, USANDO LA CLASSE DI TEMPLATE ApiError:
 */

//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//		Map<String, String> errors = new HashMap<>();
//
//		ex.getBindingResult().getAllErrors().forEach(error -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//
//		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, ex.getMessage(), errors);
//
//		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
//	}

/*
 * Gestione custom di altre eccezioni
 */
//	@Override
//	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//		Map<String, String> errors = new HashMap<>();
//
//		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, ex.getMessage(), errors);
//
//		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
//
//	}
//
//	@ExceptionHandler(RecordNotFoundException.class)
//	public ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex) {
//
//		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex, ex.getMessage(), ex.getErrors());
//
//		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
//
//	}
//
//	@ExceptionHandler(IncorrectServiceException.class)
//	public ResponseEntity<Object> handleIncorrectService(IncorrectServiceException ex) {
//
//		ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex, ex.getMessage(), ex.getErrors());
//
//		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
//
//	}
//
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//
//		Map<String, String> errors = new HashMap<>();
//
//		ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex, ex.getMessage(), errors);
//		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
//
//	}
