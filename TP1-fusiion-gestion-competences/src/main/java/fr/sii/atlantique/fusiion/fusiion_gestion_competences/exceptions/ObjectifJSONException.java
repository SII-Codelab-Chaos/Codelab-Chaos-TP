package fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions;

/**
 * 
 * ObjectifJSONException est la classe qui représente l'exception Objectif JSON lorsque qu'une compétence est mal renseignée
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class ObjectifJSONException extends DefautException {

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
	public ObjectifJSONException() {
		super("impossible de creer un nouveau objectif, une compétence est mal renseignée. {}","ObjectifJSONException",409);
	}
	
}