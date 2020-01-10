package fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions;

/**
 * 
 * CompetenceExistanteException est la classe qui représente l'exception lorsqu'une competence existe déjà en base
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class CompetenceExistanteException extends DefautException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 487627222835627691L;

	/**
	 * 
	 * Constructeur de l'exception CompetenceExistanteException
	 * 
	 * @param resourceId
	 * @param message
	 */
	public CompetenceExistanteException() {
		super("impossible de creer une nouvelle competence, la resource existe déjà. Veuillez en saisir une nouvelle. {}","CompetenceExistanteException",409);
	}
}
