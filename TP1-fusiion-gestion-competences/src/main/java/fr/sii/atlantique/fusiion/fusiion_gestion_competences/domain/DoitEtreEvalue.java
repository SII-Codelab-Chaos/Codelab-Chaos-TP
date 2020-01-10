package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="DOIT_ETRE_EVALUE")
public class DoitEtreEvalue {
	
	@Id @GeneratedValue 
	private Long id;
	
	@StartNode 
	private Objectif objectif;
	@EndNode 
	private Competence competence;
	
	@Property private int notation;
	
	public DoitEtreEvalue() {
		super();
	}

	public DoitEtreEvalue(Objectif objectif, Competence competence, int notation) {
		super();
		this.objectif = objectif;
		this.competence = competence;
		this.notation = notation;
	}

	public Objectif getObjectif() {
		return objectif;
	}

	public void setObjectif(Objectif objectif) {
		this.objectif = objectif;
	}

	public Competence getCompetence() {
		return competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

	public int getNotation() {
		return notation;
	}

	public void setNotation(int notation) {
		this.notation = notation;
	}

}
