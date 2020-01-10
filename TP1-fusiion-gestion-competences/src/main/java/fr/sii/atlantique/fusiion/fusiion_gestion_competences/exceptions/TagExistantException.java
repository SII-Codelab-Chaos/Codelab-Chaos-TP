package fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions;

/**
 * 
 * TagExistantException est la classe qui représente l'exception lorsqu'un tag existe déjà en base
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public class TagExistantException extends DefautException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 487627222835627691L;

	/**
	 * 
	 * Constructeur de l'exception TagExistantException
	 * 
	 * @param resourceId
	 * @param message
	 */
	public TagExistantException() {
		super("impossible de creer un nouveau tag, la resource existe déjà. Veuillez en saisir une nouvelle. {}","TagExistantException",409);
	}
}

