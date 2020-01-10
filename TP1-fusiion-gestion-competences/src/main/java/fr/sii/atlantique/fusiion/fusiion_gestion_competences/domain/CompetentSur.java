package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import java.time.LocalDateTime;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
@RelationshipEntity(type="COMPETENT_SUR")
public class CompetentSur implements Comparable<CompetentSur> {
	@Id @GeneratedValue private Long id;
	@StartNode private Collaborateur collaborateur;
	@EndNode private Competence competence;
	
	@Property @Index private LocalDateTime date;
	@Property private int notation;
	@Property private boolean certification;
	
	/**
	 * Constructeur par defaut de la classe CompetentSur
	 */
	public CompetentSur() {
		this.collaborateur = null;
		this.competence = null;
		this.date = null;
		this.notation = 0;
		this.certification = false;
		this.id = null;
	}
	
	/**
	 * Constructeur d'une relation de Collaborateur -> Competence
	 * @param collaborateur, la collaborateur qui sera le noeud de départ de la relation
	 * @param competence, la compétence qui sera le noeud d'arrivée
	 * @param date, la date de la relation
	 * @param notation, la note de la relation
	 * @param validite, la validité de la relation
	 */
	public CompetentSur(Collaborateur collaborateur, Competence competence, LocalDateTime date, int notation, boolean certification) {
		this.collaborateur = collaborateur;
		this.competence = competence;
		this.date = date;
		this.notation = notation;
		this.certification = certification;
	}
	
	/**
	 * permet de mettre sous forme de chaine de caractère la classe CompetentSur
	 * @return String, la relation competentSur
	 * */
	@Override
	public String toString() {
		return "CompetentSur [collaborateur=" + collaborateur + ", competence=" + competence + ", date="
				+ date.toString() + ", notation=" + notation + ", certification=" + certification + "]";
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
	 * getter de la date
	 * @return LocalDateTime, la date de la relation
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * setter de la date
	 * @param date, la nouvelle date de la relation
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * getter de la notation
	 * @return int, la nouvelle notation de la relation
	 */
	public int getNotation() {
		return notation;
	}
	

	/**
	 * getter de la notation string
	 * @return String, la nouvelle notation de la relation
	 */
	public String getNotationString() {
		return String.valueOf(notation);
	}

	/**
	 * setter de la notation
	 * @param notation, la nouvelle notation de la relation
	 */
	public void setNotation(int notation) {
		this.notation = notation;
	}

	/**
	 * getter de la certification de la relation
	 * @return booléen, vraie si la relation est certifié, faux sinon
	 */
	public boolean getCertification() {
		return certification;
	}

	/**
	 * setter de la certification
	 * @param certification, la nouvelle certification de la relation
	 */
	public void setCertification(boolean certification) {
		this.certification = certification;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (certification ? 1231 : 1237);
		result = prime * result + ((collaborateur == null) ? 0 : collaborateur.hashCode());
		result = prime * result + ((competence == null) ? 0 : competence.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + notation;
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
		CompetentSur other = (CompetentSur) obj;
		if (certification != other.certification)
			return false;
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notation != other.notation)
			return false;
		return true;
	}

	/**
	 * permet de comparer deux relations, et de les trier selon leur date
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(CompetentSur relation) {
			if(relation.getCompetence().getAkCompetence().equals(this.getCompetence().getAkCompetence())
					&& relation.getCollaborateur().getAkCollaborateur().equals(this.getCollaborateur().getAkCollaborateur())) {
				return relation.getDate().compareTo(this.getDate());
			}
			else return 0;
	}
	
	
}
