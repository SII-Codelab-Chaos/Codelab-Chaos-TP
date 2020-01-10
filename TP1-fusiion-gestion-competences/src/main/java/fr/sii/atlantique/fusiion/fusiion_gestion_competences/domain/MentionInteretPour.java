package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Classe représentant la relation pour un collaborateur de vouloir travailler sur une compétence
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay, Sullivan Pineau et Chesné Sarah
 */
@RelationshipEntity(type="MENTION_INTERET_POUR")
public class MentionInteretPour {

	//Propriétés
	@Id @GeneratedValue private Long id_MentionInteret;
	@StartNode Collaborateur collaborateur;
	@EndNode Competence competence;
	@Property int isMentionInteret;
	
		
	/**Constructeur par défaut*/
	public MentionInteretPour() {
		this.collaborateur = null;
		this.competence = null;	
		this.isMentionInteret=0;
	}
		
	/**Constructeur pour une relation donnée
	 * Le collaborateur met une mention sur une compétence
	 * @param coll : collaborateur qui souhaite est à l'origine de la relation
	 * @param comp : compétence ciblée par le collaborateur
	 * @param isMention : si true, coll a de l'intérêt pour la compétence comp. Si non false.
	 */
	public MentionInteretPour(Collaborateur coll, Competence comp, int isMention) {
		this.collaborateur=coll;
		this.competence=comp;
		this.isMentionInteret=isMention;
	}

	
	/**getter du collaborateur
	 * @return le collaborateur issue de la relation MentionInteretPour*/	
	public Collaborateur getCollaborateur() {
		return this.collaborateur;
	}
	/**setter du collaborateur
	 *@param coll : le collaborateur qu'on souhaite ajouter ou modifier*/
	public void setCollaborateur(Collaborateur coll) {
		 this.collaborateur=coll;
	}
	
	/**getter de la compétence
	 * @return la compétence issue de la relation MentionInteretPour*/
	public Competence getCompetence() {
		return this.competence;
	}
	
	/**setter de la compétence
	 *@param comp : la compétence qu'on souhaite ajouter ou modifier*/
	public void setCompetence(Competence comp) {
		 this.competence=comp;
	}

	/**getter de isMentionInteret
	 * @return la valeur de la relation MentionInteretPour, true si le collaborateur a de l'intérêt pour la compétence, false si non*/
	public int getIsMentionInteret() {
		return this.isMentionInteret;
	}
	
	/**setter de isMentionInteret
	 *@param isMention: la mention qu'on souhaite ajouter ou modifier, true si le collaborateur a de l'intérêt pour la compétence, false si non*/
	public void setIsMentionInteret(int isMention) {
		this.isMentionInteret=isMention;
	}
}
