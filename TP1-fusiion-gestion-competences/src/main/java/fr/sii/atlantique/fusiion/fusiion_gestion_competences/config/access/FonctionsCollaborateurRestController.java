package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access;

/**
 * Classe permettant de lister les fonctions du controller REST CollaborateurRestController
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public enum FonctionsCollaborateurRestController implements Fonctions {
	
	GET_ALL("getAll")
	,RECHERCHER_PAR_COMPETENCE("rechercherParCompetence")
	,RECHERCHER_PAR_COMPETENCES("rechercherParCompetences")
	,AJOUT_OBJECTIFS_COLLABORATEUR("AjoutObjectifsCollaborateur")
	,FIND_OBJECTIFS_BY_AKCOLLABORATEUR_VALIDE("findObjectifsByAkCollaborateurValide")
	,FIND_OBJECTIFS_BY_AKCOLLABORATEUR("findObjectifsByAkCollaborateur")
	;
	
	private String nom;
	private String restController;
	
	FonctionsCollaborateurRestController(String nom) {
		this.nom = nom;
		this.restController = Controllers.CollaborateurRestController.toString();
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
