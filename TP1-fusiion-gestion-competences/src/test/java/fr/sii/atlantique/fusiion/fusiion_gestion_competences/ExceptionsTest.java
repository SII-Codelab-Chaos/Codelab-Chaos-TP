package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.CompetenceExistanteException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.DefautException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.ExceptionResponse;
import junit.framework.TestCase;

/**
 * Tests unitaires des exceptions
 */
public class ExceptionsTest extends TestCase {
	ExceptionResponse exceptionResponse = new ExceptionResponse();

	/**
	 * test du Constructeur exceptionResponse
	 */
	public void testConstructeurExceptionResponse() {
		assertFalse(exceptionResponse.equals(null));
	}

	/**
	 * test du setStatut du exceptionResponse
	 */
	public void testStatutExceptionResponse() {
		exceptionResponse.setStatus(200);
		assertTrue(exceptionResponse.getStatus() == 200);
	}

	/**
	 * test du setErrorCode du exceptionResponse
	 */
	public void testErrorCodeExceptionResponse() {
		exceptionResponse.setError("200");
		assertTrue(exceptionResponse.getError().equals("200"));
	}

	/**
	 * test du setErrorMessage du exceptionResponse
	 */
	public void testErrorMessageExceptionResponse() {
		exceptionResponse.setErrorMessage("Message");
		assertTrue(exceptionResponse.getErrorMessage().equals("Message"));
	}

	/**
	 * test du setPath du exceptionResponse
	 */
	public void testPathExceptionResponse() {
		exceptionResponse.setPath("/path");
		assertTrue(exceptionResponse.getPath().equals("/path"));
	}

	/**
	 * test du getTimestamp du exceptionResponse
	 */
	public void testTimestampExceptionResponse() {
		assertFalse(exceptionResponse.getTimestamp().equals(null));
	}

	/**
	 * test du constructeur du defautException
	 */
	public void testDefautException() {
		DefautException defautException = new DefautException();
		assertFalse(defautException.equals(null));
	}
	
	/**
	 * test du constructeur du ExisteException
	 */
	public void testExisteException() {
		CompetenceExistanteException existe = new CompetenceExistanteException();
		assertFalse(existe.equals(null));
	}
	
	/**
	 * test du constructeur du defautException get code
	 */
	public void testDefautExceptionGetCode() {
		DefautException defautException = new DefautException();
		assertTrue(defautException.getCodeErreur()==500);
	}
	
	/**
	 * test du constructeur du defautException getResourceId
	 */
	public void testDefautExceptionGetRessourceID() {
		DefautException defautException = new DefautException();
		assertTrue(defautException.getResourceId().equals("DefautException"));
	}
	
}
