package fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions;

/**
 * 
 * ObjectifExistantException est la classe qui représente l'exception Objectif Existant
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class ObjectifExistantException extends DefautException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 487627222835627691L;

	/**
	 * 
	 * Constructeur de l'exception ObjectifExistantException
	 * 
	 * @param resourceId
	 * @param message
	 */
	public ObjectifExistantException() {
		super("impossible de creer un nouveau objectif, la resource existe déjà. Veuillez en saisir un nouveau. {}","ObjectifExistantException",409);
	}
}
