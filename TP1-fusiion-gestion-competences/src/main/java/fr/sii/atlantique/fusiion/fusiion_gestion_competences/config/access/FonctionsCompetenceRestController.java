package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access;

/**
 * Classe permettant de lister les fonctions du controller REST CompetenceRestController
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public enum FonctionsCompetenceRestController implements Fonctions {
	GET_ALL("getAll")
	,GET_DETAIL_COMPETENCE("getDetailCompetence")
	,TEST_ALL_RELATION("testAllRelation")
	,CREATE_COMPETENCE("createCompetence")
	,CREATE_RELATION_COLLAB_COMP("createRelationCollabComp")
	,COMPETENCES_UN_COLLABORATEUR("competencesUnCollaborateur")
	,FIND_LIKE_COMPETENCE("findLikeCompetence")
	,GET_SENIORITE_COMPETENCE("getSenioriteCompetence")
	,FIND_TAGS_BY_AK_COMPETENCE("findTagsByAkCompetence")
	,MODIFIER_COMPETENCE("modifierCompetence")
	,DELETE_BY_AK_COMPETENCE("deleteByAkCompetence")
	,GET_MENTION_APPRENDRE_COMPETENCE("getMentionApprendreCompetence")
	,MODIFIER_APPRENDRE_COMPETENCE("modifierApprendreCompetence")
	,CREER_APPRENDRE_COMPETENCE("creerApprendreCompetence")
	,GET_MENTION_INTERET_COMPETENCE("getMentionInteretCompetence")
	,MODIFIER_INTERET_COMPETENCE("modifierInteretCompetence")
	,CREER_INTERET_COMPETENCE("creerInteretCompetence")
	;
	
	private String nom;
	private String restController;
	
	FonctionsCompetenceRestController(String nom) {
		this.nom = nom;
		this.restController = Controllers.CompetenceRestController.toString();
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
