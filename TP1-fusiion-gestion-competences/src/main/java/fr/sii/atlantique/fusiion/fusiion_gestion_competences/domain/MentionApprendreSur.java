package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Classe représentant la relation pour un collaborateur de vouloir apprendre sur une compétence
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay, Sullivan Pineau et Chesné Sarah
 */
@RelationshipEntity(type="MENTION_APPRENDRE_SUR")
public class MentionApprendreSur {

	//Propriétés
	@Id @GeneratedValue private Long id_MentionApprendre;
	@StartNode Collaborateur collaborateur;
	@EndNode Competence competence;
	@Property int isMentionApprendre;
	
		
	/**Constructeur par défaut*/
	public MentionApprendreSur() {
		this.collaborateur = null;
		this.competence = null;	
		this.isMentionApprendre=0;
	}
		
	/**Constructeur pour une relation donnée
	 * Le collaborateur met une mention sur une compétence
	 * @param coll : collaborateur qui souhaite est à l'origine de la relation
	 * @param comp : compétence ciblée par le collaborateur
	 * @param isMention : si true, coll souhaite apprendre la compétence comp. Si non false.
	 */
	public MentionApprendreSur(Collaborateur coll, Competence comp, int isMention) {
		this.collaborateur=coll;
		this.competence=comp;
		this.isMentionApprendre=isMention;
	}

	
	/**getter du collaborateur
	 * @return le collaborateur issue de la relation MentionApprendreSur*/	
	public Collaborateur getCollaborateur() {
		return this.collaborateur;
	}
	/**setter du collaborateur
	 *@param coll : le collaborateur qu'on souhaite ajouter ou modifier*/
	public void setCollaborateur(Collaborateur coll) {
		 this.collaborateur=coll;
	}
	
	/**getter de la compétence
	 * @return la compétence issue de la relation MentionApprendreSur*/
	public Competence getCompetence() {
		return this.competence;
	}
	
	/**setter de la compétence
	 *@param comp : la compétence qu'on souhaite ajouter ou modifier*/
	public void setCompetence(Competence comp) {
		 this.competence=comp;
	}

	/**getter de isMentionApprendre
	 * @return la valeur de la relation MentionApprendreSur, true si le collaborateur souhaite apprendre, false si non*/
	public int getIsMentionApprendre() {
		return this.isMentionApprendre;
	}
	
	/**setter de isMentionApprendre
	 *@param isMention: la mention qu'on souhaite ajouter ou modifier*/
	public void setIsMentionApprendre(int isMention) {
		this.isMentionApprendre=isMention;
	}
}
