package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body;

import java.time.LocalDateTime;

/**
 * 
 * ExceptionResponse est la classe qui permet de représenter la forme des
 * réponses lors des exceptions
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class ExceptionResponse{

	/**
	 * Heure de l'exception
	 */
	private LocalDateTime timestamp;

	/**
	 * Statut de l'exception
	 */
	private int status;

	/**
	 * ErrorCode de l'exception
	 */
	private String error;

	/**
	 * ErrorMessage de l'exception
	 */
	private String errorMessage;

	/**
	 * Path de l'exception
	 */
	private String path;
	

	/**
	 * Constructeur de ExceptionResponse
	 */
	public ExceptionResponse() {
		this.timestamp = LocalDateTime.now();
	}

	/**
	 * Constructeur avec parametres
	 * @param timestamp
	 * @param status
	 * @param error
	 * @param errorMessage
	 * @param path
	 */
	public ExceptionResponse(LocalDateTime timestamp, int status, String error, String errorMessage, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.errorMessage = errorMessage;
		this.path = path;
	}

	/**
	 * getter du timestamp
	 * 
	 * @return timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * getter de l'errorCode
	 * 
	 * @return errorCode
	 */
	public String getError() {
		return error;
	}

	/**
	 * setter de l'errorCode
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * getter de errorMessage
	 * 
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * setter de errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * getter du status
	 * 
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * setter du statut
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * getter du path
	 * 
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * setter du path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return "ExceptionResponse [timestamp=" + timestamp + ", status=" + status + ", error=" + error
				+ ", errorMessage=" + errorMessage + ", path=" + path + "]";
	}
	
}