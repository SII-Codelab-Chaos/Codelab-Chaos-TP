package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access;

/**
 * Classe permettant de lister les fonctions du controller REST ObjectifRestController
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public enum FonctionsObjectifRestController implements Fonctions {
	 GET_ALL("getAll"),
	 CREATE_OBJECTIF("createCompetence"),
	 GET_DETAIL_OBJECTIF("getDetailObjectif"),
	 GET_DETAIL_OBJECTIF_RELATION("getDetailObjectifRealtion");
	
	private String nom;
	private String restController;
	
	FonctionsObjectifRestController(String nom) {
		this.nom = nom;
		this.restController = Controllers.ObjectifRestController.toString();
	}
	
	/*
	 * getter du nom
	 * @return String, le nom
	 * */
	public String getNom() {
		return this.nom;
	}
	
	/*
	 * getter du restController
	 * @return String, le restController
	 * */
	public String getRestController() {
		return this.restController;
	}
}
