package fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
@RelationshipEntity(type="TAGUE_SUR")
public class TagueSur{

	@Id @GeneratedValue private Long id;
	@StartNode private Tag tag;
	@EndNode private Competence competence;

	/**
	 * 
	 */
	public TagueSur() {
		super();
	}

	/**
	 * 
	 * @param tag
	 * @param competence
	 */
	public TagueSur(Tag tag, Competence competence) {
		super();
		this.tag = tag;
		this.competence = competence;
	}

	public Competence getCompetence() {
		return competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	
	
	
}
