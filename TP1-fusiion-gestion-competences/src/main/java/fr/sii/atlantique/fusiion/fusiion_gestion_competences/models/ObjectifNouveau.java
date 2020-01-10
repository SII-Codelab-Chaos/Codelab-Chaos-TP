package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models;

import java.util.Map;

public class ObjectifNouveau {
	private String akObjectif;
	
	private Map<String, Integer > donnees;
	
	public ObjectifNouveau() {
		super();
	}

	public ObjectifNouveau(String akObjectif, Map<String, Integer> donnees) {
		super();
		this.akObjectif = akObjectif;
		this.donnees = donnees;
	}

	public String getAkObjectif() {
		return akObjectif;
	}

	public void setAkObjectif(String akObjectif) {
		this.akObjectif = akObjectif;
	}

	public Map<String, Integer> getDonnees() {
		return donnees;
	}

	public void setDonnees(Map<String, Integer> donnees) {
		this.donnees = donnees;
	}

}
