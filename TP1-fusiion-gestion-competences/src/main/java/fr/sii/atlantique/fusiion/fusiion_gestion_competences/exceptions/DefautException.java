package fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions;

/**
 * 
 * DefautException est la classe qui représente l'exception par défaut et qui
 * étend RuntimeException
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class DefautException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3519969880408324766L;

	private final String resourceId;
	private final int codeErreur;

	/**
	 * 
	 * Constructeur de l'exception DefautException
	 * 
	 * @param message
	 */
	protected DefautException(String message) {
		super(message);
		this.resourceId = "DefautException";
		this.codeErreur = 500;
	}

	/**
	 * Constructeurs avec parametres
	 * @param message
	 * @param resourceId
	 * @param codeErreur
	 */
	protected DefautException(String message,String resourceId, int codeErreur) {
		super(message);
		this.resourceId = resourceId;
		this.codeErreur = codeErreur;
	}

	/**
	 * Constructeur basic
	 */
	public DefautException() {
		super("Une erreur est survenue.");
		this.resourceId = "DefautException";
		this.codeErreur = 500;
	}


	public int getCodeErreur() {
		return codeErreur;
	}

	public String getResourceId() {
		return resourceId;
	}
}
