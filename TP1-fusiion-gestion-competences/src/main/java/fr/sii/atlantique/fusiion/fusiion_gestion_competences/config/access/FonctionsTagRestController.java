package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access;

/**
 * Classe permettant de lister les fonctions du controller REST TagRestController
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public enum FonctionsTagRestController implements Fonctions {
	 GET_ALL("getAll")
	,FIND_BY_AK_TAG("findByAkTag")
	,CREATE_TAG("createTag")
	,MODIFIER_TAG("modifierTag")
	,SUPPRIMER_TAG("supprimerTag")
	,FIND_LIKE_TAG("findLikeTag")
	,CREATE_RELATION_COMP_TAG("createRelationCompTag")
	,FIND_COMPETENCES_BY_AK_TAG("findCompetencesByAkTag")
	,SUPPRIMER_RELATION_TAG("supprimerRelationTag")
	;
	
	private String nom;
	private String restController;
	
	FonctionsTagRestController(String nom) {
		this.nom = nom;
		this.restController = Controllers.TagRestController.toString();
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
