package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes;

/**
 * 
 * Classe permettant de modeliser la réalisation d'une action sur l'application
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 */
public class Action {
	
	private String akCollaborateur;
	private String typeAction;
	
	public Action() {
		super();
	}
	
	public Action(String akCollaborateur, String typeAction) {
		super();
		this.akCollaborateur = akCollaborateur;
		this.typeAction = typeAction;
	}

	public String getAkCollaborateur() {
		return akCollaborateur;
	}

	public void setAkCollaborateur(String akCollaborateur) {
		this.akCollaborateur = akCollaborateur;
	}

	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	@Override
	public String toString() {
		return "Action [akCollaborateur=" + akCollaborateur + ", typeAction=" + typeAction + "]";
	}
}
