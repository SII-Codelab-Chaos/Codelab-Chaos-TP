package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import java.time.LocalDateTime;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="A_ATTEINDRE")
public class AAtteindre {
	
	@Id @GeneratedValue 
	private Long id;
	
	@StartNode 
	private Collaborateur collaborateur;
	@EndNode 
	private Objectif objectif;
	
	@Property @Index private LocalDateTime date;
	@Property private boolean valide;
	
	public AAtteindre() {
		super();
	}

	public AAtteindre(Collaborateur collaborateur, Objectif objectif, LocalDateTime date, boolean valide) {
		super();
		this.collaborateur = collaborateur;
		this.objectif = objectif;
		this.date = date;
		this.valide = valide;
	}

	public Collaborateur getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}

	public Objectif getObjectif() {
		return objectif;
	}

	public void setObjectif(Objectif objectif) {
		this.objectif = objectif;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public boolean getValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

}
