package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations;

/**
 * interface des autorisations
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public interface Autorisations {
	public String getNom();
	
	public boolean getLimiteASesInfos();
	
	public String getRestController();
}
