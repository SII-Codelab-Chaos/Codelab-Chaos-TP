package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models;

public class CalculObjectifsJSON {
	
	private String type;
	private String donneeJSON;
	
	public CalculObjectifsJSON() {
		super();
	}

	public CalculObjectifsJSON(String type, String donneeJSON) {
		super();
		this.type = type;
		this.donneeJSON = donneeJSON;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDonneeJSON() {
		return donneeJSON;
	}

	public void setDonneeJSON(String donneeJSON) {
		this.donneeJSON = donneeJSON;
	}

}
