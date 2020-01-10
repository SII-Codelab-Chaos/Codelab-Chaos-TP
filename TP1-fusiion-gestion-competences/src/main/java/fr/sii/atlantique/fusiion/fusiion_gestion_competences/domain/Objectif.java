package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import javax.validation.constraints.NotBlank;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "Objectif")
public class Objectif {
	
	@Id @GeneratedValue
	private Long id;
	
	@Id @Index @NotBlank
	private String akObjectif;
	
	@NotBlank
	private String nom;
	private String description;
	private String badge;
	
	public Objectif() {
		super();
	}

	public Objectif(String akObjectif, String nom, String description, String badge) {
		super();
		this.akObjectif = akObjectif;
		this.nom = nom;
		this.description = description;
		this.badge = badge;
	}

	public Objectif(String akObjectif, String nom, String description) {
		super();
		this.akObjectif = akObjectif;
		this.nom = nom;
		this.description = description;
	}

	public String getAkObjectif() {
		return akObjectif;
	}

	public void setAkObjectif(String akObjectif) {
		this.akObjectif = akObjectif;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((akObjectif == null) ? 0 : akObjectif.hashCode());
		result = prime * result + ((badge == null) ? 0 : badge.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		Objectif other = (Objectif) obj;
		if (akObjectif == null) {
			if (other.akObjectif != null)
				return false;
		} else if (!akObjectif.equals(other.akObjectif))
			return false;
		if (badge == null) {
			if (other.badge != null)
				return false;
		} else if (!badge.equals(other.badge))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

}
