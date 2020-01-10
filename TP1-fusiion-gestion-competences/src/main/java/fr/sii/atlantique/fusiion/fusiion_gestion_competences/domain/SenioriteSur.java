package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.PostLoad;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.annotation.Transient;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
@RelationshipEntity(type="SENIORITE_SUR")
public class SenioriteSur {
	@Id @GeneratedValue private Long id;
	
	@StartNode private Collaborateur collaborateur;
	@EndNode private Competence competence;
	
	@Property private LocalDateTime dateDebut;
	
	@Transient private String seniorite; //non persisté en base
	
	/**
	 * permet d'intialiser des valeurs après la récupération et le chargement de l'objet
	 */
	@PostLoad
	public void initSeniorite() {
		
		Long annees = ChronoUnit.YEARS.between(this.dateDebut,LocalDateTime.now());
		
		this.seniorite = (annees < 0) ? "ERREUR"
					   : (0 <= annees && annees < 2) ? "JUNIOR"
					   : (2 <= annees && annees < 5) ? "CONFIRME"
				       : "SENIOR";
	}
	
	/**
	 * Constructeur par defaut de la classe SenioriteSur
	 */
	public SenioriteSur() {
		this.collaborateur = null;
		this.competence = null;
		this.dateDebut = null;
	}

	/**
	 * Constructeur d'une relation seniorite de Collaborateur -> Competence
	 * @param collaborateur, la collaborateur qui sera le noeud de départ de la relation
	 * @param competence, la compétence qui sera le noeud d'arrivée
	 * @param dateDebut, la date de la séniorité
	 */
	public SenioriteSur(Collaborateur collaborateur, Competence competence, LocalDateTime dateDebut) {
		super();
		this.collaborateur = collaborateur;
		this.competence = competence;
		this.dateDebut = dateDebut;
	}

	/**
	 * getter du collaborateur
	 * @return Collaborateur, le collaborateur qui est le point de départ de la relation
	 */
	public Collaborateur getCollaborateur() {
		return collaborateur;
	}
	
	/**
	 * setter du collaborateur
	 * @param collaborateur, le nouveau collaborateur qui sera le nouveau noeud de départ de la relation
	 */
	public void setCollaborateur(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}
	
	/**
	 * getter de la compétence
	 * @return Competence, la compétence qui est le noeud d'arrivée de la relation
	 */
	public Competence getCompetence() {
		return competence;
	}

	/**
	 * setter de la competence
	 * @param competence, la nouvelle competence qui sera le nouveau noeud d'arrivé de la relation
	 */
	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
	
	/**
	 * getter de la dateDebut
	 * @return LocalDateTime, la date de la relation
	 */
	public LocalDateTime getDateDebut() {
		return dateDebut;
	}
	
	/**
	 * setter de la dateDebut
	 * @param date, la nouvelle date de la relation
	 */
	public void setDateDebut(LocalDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getSeniorite() {
		return seniorite;
	}

	public void setSeniorite(String seniorite) {
		this.seniorite = seniorite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collaborateur == null) ? 0 : collaborateur.hashCode());
		result = prime * result + ((competence == null) ? 0 : competence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SenioriteSur other = (SenioriteSur) obj;
		if (collaborateur == null) {
			if (other.collaborateur != null)
				return false;
		} else if (!collaborateur.equals(other.collaborateur))
			return false;
		if (competence == null) {
			if (other.competence != null)
				return false;
		} else if (!competence.equals(other.competence))
			return false;
		return true;
	}
	
	

}
