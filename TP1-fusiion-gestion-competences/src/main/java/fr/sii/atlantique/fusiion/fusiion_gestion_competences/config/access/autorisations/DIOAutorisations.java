package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.Fonctions;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsCollaborateurRestController;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsCompetenceRestController;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsObjectifRestController;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.FonctionsTagRestController;

/**
 * permet de lister une suite d'autorisations
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public enum DIOAutorisations implements Autorisations { 
	COMP_TEST_ALL_RELATION(FonctionsCompetenceRestController.TEST_ALL_RELATION)
	,COMP_CREATE_RELATION_COLLAB_COMP(FonctionsCompetenceRestController.CREATE_RELATION_COLLAB_COMP,false)
	,COMP_COMPETENCES_UN_COLLABORATEUR(FonctionsCompetenceRestController.COMPETENCES_UN_COLLABORATEUR,false)
	,COMP_GET_SENIORITE_COMPETENCE(FonctionsCompetenceRestController.GET_SENIORITE_COMPETENCE,false)
	,COMP_MODIFIER_COMPETENCE(FonctionsCompetenceRestController.MODIFIER_COMPETENCE)
	,COMP_DELETE_BY_AK_COMPETENCE(FonctionsCompetenceRestController.DELETE_BY_AK_COMPETENCE)
	,COMP_GET_MENTION_APPRENDRE_COMPETENCE(FonctionsCompetenceRestController.GET_MENTION_APPRENDRE_COMPETENCE, false)
	,COMP_MODIFIER_APPRENDRE_COMPETENCE(FonctionsCompetenceRestController.MODIFIER_APPRENDRE_COMPETENCE, false)
	,COMP_CREER_APPRENDRE_COMPETENCE(FonctionsCompetenceRestController.CREER_APPRENDRE_COMPETENCE, false)
	,COMP_GET_MENTION_INTERET_COMPETENCE(FonctionsCompetenceRestController.GET_MENTION_APPRENDRE_COMPETENCE, false)
	,COMP_MODIFIER_INTERET_COMPETENCE(FonctionsCompetenceRestController.MODIFIER_INTERET_COMPETENCE, false)
	,COMP_GET_CREER_INTERET_COMPETENCE(FonctionsCompetenceRestController.CREER_INTERET_COMPETENCE, false)
	
	,TAG_CREATE_TAG(FonctionsTagRestController.CREATE_TAG)
	,TAG_MODIFIER_TAG(FonctionsTagRestController.MODIFIER_TAG)
	,TAG_SUPPRIMER_TAG(FonctionsTagRestController.SUPPRIMER_TAG)
	,TAG_CREATE_RELATION_COMP_TAG(FonctionsTagRestController.CREATE_RELATION_COMP_TAG)
	,TAG_SUPPRIMER_RELATION_TAG(FonctionsTagRestController.SUPPRIMER_RELATION_TAG)
	
	 ,COLLAB_GET_ALL(FonctionsCollaborateurRestController.GET_ALL)
	,COLLAB_RECHERCHER_PAR_COMPETENCE(FonctionsCollaborateurRestController.RECHERCHER_PAR_COMPETENCE)
	,COLLAB_RECHERCHER_PAR_COMPETENCES(FonctionsCollaborateurRestController.RECHERCHER_PAR_COMPETENCES)
	,COLLAB_AJOUT_OBJECTIFS_COLLABORATEUR(FonctionsCollaborateurRestController.AJOUT_OBJECTIFS_COLLABORATEUR)
	
	,OBJECTIF_GET_ALL(FonctionsObjectifRestController.GET_ALL)
	,OBJECTIF_CREATE_OBJECTIF(FonctionsObjectifRestController.GET_ALL)
	,OBJECTIF_GET_DETAIL_OBJECTIF(FonctionsObjectifRestController.GET_DETAIL_OBJECTIF)
	,OBJECTIF_GET_DETAIL_OBJECTIF_RELATION(FonctionsObjectifRestController.GET_DETAIL_OBJECTIF_RELATION)
	
	,COLLABORATEUR_FIND_OBJECTIFS_BY_AKCOLLABORATEUR_VALIDE(FonctionsCollaborateurRestController.FIND_OBJECTIFS_BY_AKCOLLABORATEUR_VALIDE, false)
	,COLLABORATEUR_FIND_OBJECTIFS_BY_AKCOLLABORATEUR(FonctionsCollaborateurRestController.FIND_OBJECTIFS_BY_AKCOLLABORATEUR, false)
	;
	
	
	Fonctions fonction;
	private boolean limiteASesInfos;
	
	DIOAutorisations(Fonctions fonction, boolean limiteASesInfos) {
		this.fonction = fonction;
		this.limiteASesInfos = limiteASesInfos;
	}
	
	DIOAutorisations(Fonctions fonction) {
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
