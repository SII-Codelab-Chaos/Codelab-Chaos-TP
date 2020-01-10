package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models;

import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ObjectifRetourJSON {
	@Property(name = "akObjectif")
	private String akObjectif;
	
	@Property(name = "nom")
	private String nom;
	
	@Property(name = "description")
	private String description;
	
	@Property(name = "badge")
	private String badge;
	
	@Property(name = "valide")
	private boolean valide;

	public ObjectifRetourJSON() {
		super();
	}
	
	public ObjectifRetourJSON(String akObjectif, String nom, String description, String badge, boolean valide) {
		super();
		this.akObjectif = akObjectif;
		this.nom = nom;
		this.description = description;
		this.badge = badge;
		this.valide = valide;
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

	public boolean isValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}
	
	
}
