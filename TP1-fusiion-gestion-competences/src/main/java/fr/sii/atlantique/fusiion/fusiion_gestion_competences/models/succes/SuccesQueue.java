package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes;

/**
 * Model envoyé et recu dans rabbitmq
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public class SuccesQueue {

	private String akCollaborateur;

	private String akSucces;

	private int value;
	
	public SuccesQueue() {
	}
	
	public SuccesQueue(String akCollaborateur, String akSucces, int value) {
		this.akCollaborateur = akCollaborateur;
		this.akSucces = akSucces;
		this.value = value;
	}
	
	public String getAkCollaborateur() {
		return akCollaborateur;
	}

	public void setAkCollaborateur(String akCollaborateur) {
		this.akCollaborateur = akCollaborateur;
	}

	public String getAkSucces() {
		return akSucces;
	}

	public void setAkSucces(String akSucces) {
		this.akSucces = akSucces;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}

