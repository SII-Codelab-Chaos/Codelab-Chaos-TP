package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import javax.validation.constraints.NotBlank;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Systeme de donnée des compétences
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
@NodeEntity(label = "Competence")
public class Competence implements Comparable<Competence>{
	@Id @GeneratedValue
	private Long id;
	
	@Id @Index @NotBlank
	private String akCompetence;
	
	@NotBlank
	private String nom;
	private String description;
	private boolean valide;
	private boolean priorite;
	

	/**
	 * constructeur par defaut de la classe Competence
	 */
	public Competence() {
		this.akCompetence="";
		this.nom = "";
		this.description = "";
		this.valide = false;
		this.priorite = false;
	}

	/**
	 * Constructeur de la classe Competence
	 * @param ak_competence, l'ak de la competence
	 * @param nom, le nom de la competence
	 * @param description, la description de la competence
	 * @param valide, un booléen permettant de savoir si la competence à été validée ou non
	 * @param priorite, un booléen permettant de savoir si la compétence est prioritaire ou non
	 */
	public Competence(String akCompetence, String nom, String description, boolean valide, boolean priorite) {
		super();
		this.akCompetence = akCompetence;
		this.nom = nom;
		this.description = description;
		this.valide = valide;
		this.priorite = priorite;
	}

	
	
	/**
	 * getter de l'akCompetence
	 * @return String, l'ak de la compétence
	 */
	public String getAkCompetence() {
		return akCompetence;
	}

	/**
	 * setter de l'akCompetence
	 * @param akCompetence, le nouvel ak de la compétence
	 */
	public void setAkCompetence(String akCompetence) {
		this.akCompetence = akCompetence;
	}

	/**
	 * getter du nom de la compétence
	 * @return String, le nom de la compétence
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * setter du nom de la compétence
	 * @param nom, le nouveau nom de la compétence
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * getter de la description de la compétence
	 * @return String, la description de la compétence
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * setter de la description de la compétence
	 * @param description, la nouvelle description de la compétence
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * getter de la validité de la compétence
	 * @return booléen, vraie si la compétence est validée, faux sinon
	 */
	public boolean isValide() {
		return valide;
	}

	/**
	 * setter de la validité de la compétence
	 * @param valide, la nouvelle valeur de la validité de la compétence
	 */
	public void setValide(boolean valide) {
		this.valide = valide;
	}

	/**
	 * getter de la priorité de la compétence
	 * @return booléen, vraie si la compétence est prioritaire, faux sinon
	 */
	public boolean getPriorite() {
		return priorite;
	}

	/**
	 * setter de la priorité de la compétence
	 * @param priorite, la nouvelle valeur de la priorité
	 */
	public void setPriorite(boolean priorite) {
		this.priorite = priorite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((akCompetence == null) ? 0 : akCompetence.hashCode());
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
		Competence other = (Competence) obj;
		if (akCompetence == null) {
			if (other.akCompetence != null)
				return false;
		} else if (!akCompetence.equals(other.akCompetence))
			return false;
		return true;
	}

	/**
	 * permet de mettre sous forme de chaine de caractère une instance de la classe
	 * @return String, l'instance de la compétence mise sous forme de chaine de caractère
	 * */
	@Override
	public String toString() {
		return "Competence [akCompetence=" + akCompetence + ", nom=" + nom + ", description="
				+ description + ", valide=" + valide + ", priorite=" + priorite + "]";
	}
	
	@Override
	public int compareTo(Competence o) {
		return this.nom.toLowerCase().compareTo(o.getNom().toLowerCase());
	}

	
}
