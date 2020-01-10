package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import javax.validation.constraints.NotBlank;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Model de tag
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
@NodeEntity(label = "Tag")
public class Tag implements Comparable<Tag>{
	@Id @GeneratedValue
	private Long id;

	@Id @Index @NotBlank
	private String akTag;

	@NotBlank
	private String nom;
	
	private String description;

	/**
	 * Constructeur vide
	 */
	public Tag() {
		super();
	}

	/**
	 * Constructeur avec parametres
	 * @param akTag La clé public du tag
	 * @param nom Le nom du tag
	 * @param description La description du tag
	 */
	public Tag(String akTag,String nom, String description) {
		super();
		this.akTag = akTag;
		this.nom = nom;
		this.description = description;
	}

	public String getAkTag() {
		return akTag;
	}

	public void setAkTag(String akTag) {
		this.akTag = akTag;
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

	@Override
	public String toString() {
		return "Tag [akTag=" + akTag + ", nom=" + nom + "]";
	}

	@Override
	public int compareTo(Tag o) {
		return this.nom.toLowerCase().compareTo(o.getNom());
	}	
	
}
