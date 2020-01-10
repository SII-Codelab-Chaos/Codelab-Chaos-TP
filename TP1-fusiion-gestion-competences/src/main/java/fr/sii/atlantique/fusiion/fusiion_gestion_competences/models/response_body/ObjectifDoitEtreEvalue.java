package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body;

import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ObjectifDoitEtreEvalue {
	@Property(name = "akObjectif")
	private String akObjectif;
	
	@Property(name = "notation")
	private Long notation;
	
	@Property(name = "akCompetence")
	private String akCompetence;
	
	public ObjectifDoitEtreEvalue() {
		super();
	}
	
	public ObjectifDoitEtreEvalue(String akObjectif, Long notation, String akCompetence) {
		super();
		this.akObjectif = akObjectif;
		this.notation = notation;
		this.akCompetence = akCompetence;
	}
	
	
	public String getAkObjectif() {
		return akObjectif;
	}
	public void setAkObjectif(String akObjectif) {
		this.akObjectif = akObjectif;
	}
	public Long getNotation() {
		return notation;
	}
	public void setNotation(Long notation) {
		this.notation = notation;
	}
	public String getAkCompetence() {
		return akCompetence;
	}
	public void setAkCompetence(String akCompetence) {
		this.akCompetence = akCompetence;
	}

}
