package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models;

public class ObjectifNouvelleEvaluation {
	private String akCompetence;
	
	private String akCollaborateur;
	
	public ObjectifNouvelleEvaluation() {
		super();
	}

	public ObjectifNouvelleEvaluation(String akCompetence, String akCollaborateur) {
		super();
		this.akCompetence = akCompetence;
		this.akCollaborateur = akCollaborateur;
	}

	public String getAkCompetence() {
		return akCompetence;
	}

	public void setAkCompetence(String akCompetence) {
		this.akCompetence = akCompetence;
	}

	public String getAkCollaborateur() {
		return akCollaborateur;
	}

	public void setAkCollaborateur(String akCollaborateur) {
		this.akCollaborateur = akCollaborateur;
	}
	
	

}
