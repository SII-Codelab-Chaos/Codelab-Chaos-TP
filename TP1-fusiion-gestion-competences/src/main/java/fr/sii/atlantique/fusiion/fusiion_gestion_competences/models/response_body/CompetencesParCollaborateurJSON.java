package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body;

import java.time.LocalDateTime;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;

/**
 *  * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de faire passerelle entre le back et le front via des données JSON en sortie
 * C'est un model JSON pour lister les compétences d'un collaborateur
 * 
 */
public class CompetencesParCollaborateurJSON{
	
	private String akCompetence;
	private String nom;
	private String description;
	private boolean valide;
	private boolean priorite;
	private LocalDateTime date;
	private int notation;
	private boolean certification;
	
	
	/**
	 * Constructeur par defaut de la classe
	 */
	public CompetencesParCollaborateurJSON() {
		super();
	}

	/**
	 * Constructeur de la classe CompetenceParCollaborateurJSON
	 * @param relation, la relation à partir duquel le model JSON sera construit
	 */
	public CompetencesParCollaborateurJSON(CompetentSur relation) {
		super();
		this.akCompetence = relation.getCompetence().getAkCompetence();
		this.nom = relation.getCompetence().getNom();
		this.valide = relation.getCompetence().isValide();
		this.priorite = relation.getCompetence().getPriorite();
		this.date = relation.getDate();
		this.notation = relation.getNotation();
		this.certification = relation.getCertification();
		this.description = relation.getCompetence().getDescription();
	}

	/**
	 * getter de l'akCompetence
	 * @return String, l'ak de la competence dans l'instance du model JSON
	 */
	public String getAkCompetence() {
		return akCompetence;
	}

	/**
	 * setter de l'ak competence
	 * @param akCompetence, le nouvelle ak de la compétence dans l'instance du model JSON
	 */
	public void setAkCompetence(String akCompetence) {
		this.akCompetence = akCompetence;
	}

	/**
	 * getter du nom de la compétence
	 * @return String, le nom de la compétence dans l'instance du model JSON
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * setter du nom de la competence
	 * @param nom, le nouveau nom de la compétence dans l'instance du model JSON
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * getter de la validité de la compétence
	 * @return boolean, vraie si la compétence dans l'instance du model JSON est valide, faux sinon
	 */
	public boolean getValide() {
		return valide;
	}

	/**
	 * setter de la validité de la compétence
	 * @param valide, la nouvelle validité de la compétence dans l'instance du model JSON
	 */
	public void setValide(boolean valide) {
		this.valide = valide;
	}

	/**
	 * getter de la priorité de la compétence
	 * @return boolean, vraie si la compétence dans l'instance du model JSON est prioritaire, faux sinon 
	 */
	public boolean getPriorite() {
		return priorite;
	}

	/**
	 * setter de la priorité de la compétence
	 * @param priorite, la nouvelle priorité de la compétence dans l'instance du model JSON
	 */
	public void setPriorite(boolean priorite) {
		this.priorite = priorite;
	}

	/**
	 * getter de la date
	 * @return LocalDateTime, la date associée à l'évaluation du collaborateur dans l'instance du model JSON
	 */
	public LocalDateTime getDate() {
		return date;
	}
	
	/**
	 * setter de la date
	 * @param date, la nouvelle date associé à l'évaluation du collaborateur dans l'instance du model JSON
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * getter de la notation
	 * @return Entier, la notation associée à l'évaluation du collaborateur dans l'instance du model JSON
	 */
	public int getNotation() {
		return notation;
	}
	
	/**
	 * setter de la notation
	 * @param notation, la notation associée à l'évaluation du collaborateur dans l'instance du model JSON
	 */
	public void setNotation(int notation) {
		this.notation = notation;
	}

	/**
	 * getter de la certification de l'évaluation 
	 * @return boolean, vraie si l'évaluation du collaborateur est validé par un membre de la DIO, faux sinon
	 */
	public boolean getCertification() {
		return certification;
	}

	/**
	 * setter de la certification
	 * @param certification, la nouvelle certification associée à l'évaluation du collaborateur dans l'instance du model JSON
	 */
	public void setCertification(boolean certification) {
		this.certification = certification;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((akCompetence == null) ? 0 : akCompetence.hashCode());
		result = prime * result + (certification ? 1231 : 1237);
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + notation;
		result = prime * result + (priorite ? 1231 : 1237);
		result = prime * result + (valide ? 1231 : 1237);
		return result;
	}

	/**
	 * Comparateur
	 */
	@Override
	public boolean equals(Object obj) {
		
		boolean result = true;
		
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else {
			CompetencesParCollaborateurJSON other = (CompetencesParCollaborateurJSON) obj;
			if (akCompetence == null) {
				if (other.akCompetence != null)
					result = false;
			} else if (!akCompetence.equals(other.akCompetence))
				result = false;
			else if (certification != other.certification)
				result = false;
			else if (date == null) {
				if (other.date != null)
					result = false;
			} else if (!date.equals(other.date))
				result = false;
			else if (nom == null) {
				if (other.nom != null)
					result = false;
			} else if (!nom.equals(other.nom))
				result = false;
			else if (notation != other.notation || priorite != other.priorite || valide != other.valide)
				result = false;
		}
	
		return result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CompetencesParCollaborateurJSON [akCompetence=" + akCompetence + ", nom=" + nom + ", description="
				+ description + ", valide=" + valide + ", priorite=" + priorite + ", date=" + date + ", notation="
				+ notation + ", certification=" + certification + "]";
	}


	
		
}
