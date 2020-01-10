package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques;


/**
 * 
 * Classe permettant de modeliser la réalisation d'une action statistique sur l'application
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 */
public class ActionStatistique {
	
	private String akStatistique;
	private String typeAction;
	
	public ActionStatistique() {
		super();
	}
	
	public ActionStatistique(String akStatistique, String typeAction) {
		super();
		this.akStatistique = akStatistique;
		this.typeAction = typeAction;
	}

	public String getAkStatistique() {
		return akStatistique;
	}

	public void setAkStatistique(String akStatistique) {
		this.akStatistique = akStatistique;
	}

	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	@Override
	public String toString() {
		return "Action [akStatistique=" + akStatistique + ", typeAction=" + typeAction + "]";
	}
}
