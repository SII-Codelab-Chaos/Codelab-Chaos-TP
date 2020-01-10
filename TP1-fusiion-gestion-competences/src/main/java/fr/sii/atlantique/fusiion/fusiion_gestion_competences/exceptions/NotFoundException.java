package fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions;

/**
 * 
 * DefautException est la classe qui représente l'exception no found
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class NotFoundException extends DefautException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5752588964055152579L;

	/**
	 * 
	 * Constructeur de l'exception NoFoundException
	 * 
	 * @param resourceId
	 * @param message
	 */
	public NotFoundException(String message) {
		super(message,"NotFoundException",404);
	}
}
