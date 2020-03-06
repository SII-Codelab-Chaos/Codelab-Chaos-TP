package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.Fonctions;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsCollaborateurRestController;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsCompetenceRestController;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsTagRestController;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsObjectifRestController;

/**
 * permet de lister une suite d'autorisations
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public enum CollaborateurAutorisations implements Autorisations {
	
	COMP_GET_ALL(FonctionsCompetenceRestController.GET_ALL)
	,COMP_GET_DETAIL_COMPETENCE(FonctionsCompetenceRestController.GET_DETAIL_COMPETENCE)
	,COMP_CREATE_COMPETENCE(FonctionsCompetenceRestController.CREATE_COMPETENCE)
	,COMP_COMPETENCES_UN_COLLABORATEUR(FonctionsCompetenceRestController.COMPETENCES_UN_COLLABORATEUR)
	,COMP_FIND_LIKE_COMPETENCE(FonctionsCompetenceRestController.FIND_LIKE_COMPETENCE)
	,COMP_CREATE_RELATION_COLLAB_COMP(FonctionsCompetenceRestController.CREATE_RELATION_COLLAB_COMP)
	,COMP_GET_SENIORITE_COMPETENCE(FonctionsCompetenceRestController.GET_SENIORITE_COMPETENCE)
	,COMP_FIND_TAGS_BY_AK_COMPETENCE(FonctionsCompetenceRestController.FIND_TAGS_BY_AK_COMPETENCE)
	,COMP_GET_MENTION_APPRENDRE_COMPETENCE(FonctionsCompetenceRestController.GET_MENTION_APPRENDRE_COMPETENCE)
	,COMP_MODIFIER_APPRENDRE_COMPETENCE(FonctionsCompetenceRestController.MODIFIER_APPRENDRE_COMPETENCE)
	,COMP_CREER_APPRENDRE_COMPETENCE(FonctionsCompetenceRestController.CREER_APPRENDRE_COMPETENCE)
	,COMP_GET_MENTION_INTERET_COMPETENCE(FonctionsCompetenceRestController.GET_MENTION_APPRENDRE_COMPETENCE)
	,COMP_MODIFIER_INTERET_COMPETENCE(FonctionsCompetenceRestController.MODIFIER_INTERET_COMPETENCE)
	,COMP_GET_CREER_INTERET_COMPETENCE(FonctionsCompetenceRestController.CREER_INTERET_COMPETENCE)
	
	,TAGS_GET_ALL(FonctionsTagRestController.GET_ALL)
	,TAGS_FIND_COMPETENCES_BY_AK_TAG(FonctionsTagRestController.FIND_COMPETENCES_BY_AK_TAG)
	,TAGS_FIND_BY_AK_TAG(FonctionsTagRestController.FIND_BY_AK_TAG)
	,TAGS_FIND_LIKE_TAG(FonctionsTagRestController.FIND_LIKE_TAG)
	
	,OBJECTIF_GET_ALL(FonctionsObjectifRestController.GET_ALL)
	,OBJECTIF_GET_DETAIL_OBJECTIF(FonctionsObjectifRestController.GET_DETAIL_OBJECTIF)
	,OBJECTIF_GET_DETAIL_OBJECTIF_RELATION(FonctionsObjectifRestController.GET_DETAIL_OBJECTIF_RELATION)
	
	,COLLABORATEUR_FIND_OBJECTIFS_BY_AKCOLLABORATEUR_VALIDE(FonctionsCollaborateurRestController.FIND_OBJECTIFS_BY_AKCOLLABORATEUR_VALIDE)
	,COLLABORATEUR_FIND_OBJECTIFS_BY_AKCOLLABORATEUR(FonctionsCollaborateurRestController.FIND_OBJECTIFS_BY_AKCOLLABORATEUR)
	;
	
	Fonctions fonction;
	private boolean limiteASesInfos;
	
	CollaborateurAutorisations(Fonctions fonction, boolean limiteASesInfos) {
		this.fonction = fonction;
		this.limiteASesInfos = limiteASesInfos;
	}
	
	CollaborateurAutorisations(Fonctions fonction) {
		this.fonction = fonction;
		this.limiteASesInfos = true;
	}
	
	/*
	 * getter du nom d'une autorisation
	 * @return String, le nom de l'autorisations
	 * */
	public String getNom() {
		return this.fonction.getNom();
	}
	
	/*
	 * getter de la limite d'access
	 * @return boolean, vraie si le role est limité à ses infos personnel, faux sinon
	 * */
	public boolean getLimiteASesInfos() {
		return this.limiteASesInfos;
	}
	
	/*
	 * getter du restController
	 * @return String, le restController
	 * */
	public String getRestController() {
		return this.fonction.getRestController();
	}
}
