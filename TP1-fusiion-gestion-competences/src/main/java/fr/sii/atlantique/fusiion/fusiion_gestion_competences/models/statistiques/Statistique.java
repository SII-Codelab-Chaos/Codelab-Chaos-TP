package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques;

import java.util.List;

/**
 * 
 * Statistique est la classe qui représente une statistique dans le base de donnée MongoDB
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class Statistique {
	
	private String nomStatistique;
	private String akStatistique;
	List<DonneeStatistique> donnees;
	
	/**
	 * constructor par defaut de la classe Statistique l'ak de la statistique est mise à vide
	 */
	public Statistique() {
		this.akStatistique = "";
	}
	
	/**
	 * constructeur de la classe Statistique
	 * 
	 * @param nomStatistique,  le nom de la statistique
	 * @param akStatistique, l'ak de la statistique
	 * @param date, la date de la statistique
	 * @param donnees, la liste de données que possède la statistique
	 */
	public Statistique(String nomStatistique, String akStatistique, List<DonneeStatistique> donnees) {
		this.nomStatistique = nomStatistique;
		this.akStatistique = akStatistique;
		this.donnees = donnees;
	}

	/**
	 * 
	 * getter des données
	 * 
	 * @return List<DonneeStatistique>, la liste des données
	 */
	public List<DonneeStatistique> getDonnees() {
		return donnees;
	}

	/**
	 * 
	 * setter des données
	 * 
	 * @param donnees,  la nouvelle liste de données
	 */
	public void setDonnees(List<DonneeStatistique> donnees) {
		this.donnees = donnees;
	}

	/**
	 * 
	 * getter du nom de la statistique
	 * 
	 * @return String, le nom de la statistique
	 */
	public String getNomStatistique() {
		return nomStatistique;
	}

	/**
	 * 
	 * setter du nomStatistique
	 * 
	 * param nomStatistique, le nouvelle nomStatistique de la statistique
	 */
	public void setNomStatistique(String nomStatistique) {
		this.nomStatistique = nomStatistique;
	}
	
	/**
	 * 
	 * getter de akStatistique
	 * 
	 * @return String, l'akStatistique
	 */
	public String getAkStatistique() {
		return akStatistique;
	}

	/**
	 * 
	 * setter d'akStatistique
	 * 
	 * param  akStatistique, le nouvelle akStatistique de la statistique
	 */
	public void setAkStatistique(String akStatistique) {
		this.akStatistique = akStatistique;
	}

}