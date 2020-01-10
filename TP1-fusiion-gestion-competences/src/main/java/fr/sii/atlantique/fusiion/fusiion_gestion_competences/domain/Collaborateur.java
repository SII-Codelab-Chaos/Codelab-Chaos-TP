package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Systeme de données des collaborateurs
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
@NodeEntity(label = "Collaborateur")
public class Collaborateur {
	@Id @GeneratedValue
	private Long id;
	
	@Id @Index @Email @NotBlank
	private String akCollaborateur;
	
	/**
	 * Constructeur par defaut de la classe Collaborateur
	 * 
	 * */
	public Collaborateur() {
		super();
		this.akCollaborateur="";
	}
	
	/**
	 * Constructeur de la classe Collaborateur
	 * @param akCollaborateur, l'ak du collaborateur
	 * */
	public Collaborateur(String akCollaborateur) {
		super();
		this.akCollaborateur = akCollaborateur;
	}
	
	/**
	 * getter de l'akCollaborateur
	 * @return String, l'ak du collaborateur
	 * */
	public String getAkCollaborateur() {
		return this.akCollaborateur;
	}
	
	/**
	 * setter de l'akcollaborateur
	 * @param akcollaborateur, le nouvelle ak du collaborateur
	 * */
	public void setAkCollaborateur(String akCollaborateur) {
		this.akCollaborateur = akCollaborateur;
	}
	
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((akCollaborateur == null) ? 0 : akCollaborateur.hashCode());
		return result;
	}

	/**
	 * Comparaison entre collaborateurs
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collaborateur other = (Collaborateur) obj;
		if (akCollaborateur == null) {
			if (other.akCollaborateur != null)
				return false;
		} else if (!akCollaborateur.equals(other.akCollaborateur))
			return false;
		return true;
	}

	/**
	 * methode permet de mettre sous chaine de caractère l'instance d'un collaborateur
	 * @return String, les attributs du collaborateur
	 * */
	@Override
	public String toString() {
		return "Collaborateur [akCollaborateur=" + akCollaborateur + ""
				+ "]";
	}
	
}
