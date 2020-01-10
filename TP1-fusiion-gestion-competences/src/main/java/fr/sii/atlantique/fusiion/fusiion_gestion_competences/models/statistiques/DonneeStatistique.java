package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques;


import java.util.HashMap;
import java.util.Map;

/**
 * 
 * DonneeStatistique est la classe qui représente une donnée d'une statistique
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan
 *         Pineau
 *
 */
public class DonneeStatistique {
	private String nomDonnee;
	private String akDonnee;
	private Map<String,Integer> valeurs;

	/**
	 * constructor par defaut de la classe DonneeStatistique
	 */
	public DonneeStatistique() {
		super();
		this.nomDonnee = "";
		this.akDonnee = "";
	}

	/**
	 * constructeur de la classe DonneeStatistique
	 * 
	 * @param nomDonnee,  le nom de la donnee
	 * @param akDonnee, l'ak de la donnée
	 */
	public DonneeStatistique(String nomDonnee, String akDonnee) {
		super();
		this.nomDonnee = nomDonnee;
		this.akDonnee = akDonnee;
		this.valeurs = new HashMap<String,Integer>();
	}
	
	/**
	 * constructeur de la classe DonneeStatistique
	 * 
	 * @param nomDonnee,  le nom de la donnee
	 * @param akDonnee, l'ak de la donnée
	 * @param valeurs, une hashMap<String, Integer> de la donnée
	 */
	public DonneeStatistique(String nomDonnee, String akDonnee, Map<String, Integer> valeurs) {
		super();
		this.nomDonnee = nomDonnee;
		this.akDonnee = akDonnee;
		this.valeurs = valeurs;
	}

	/**
	 * 
	 * getter du nom de la donnée
	 * 
	 * @return String, le nom de la donnée
	 */
	public String getNomDonnee() {
		return nomDonnee;
	}
	
	/**
	 * 
	 * setter du nomDonnee
	 * 
	 * @param nomDonnee,  le nouvelle nomDonnee
	 */
	public void setNomDonnee(String nomDonnee) {
		this.nomDonnee = nomDonnee;
	}
	
	/**
	 * 
	 * getter de l'akDonnee
	 * 
	 * @return String, le nom de l'akDonnee
	 */
	public String getAkDonnee() {
		return akDonnee;
	}

	/**
	 * 
	 * setter de 'akDonnee
	 * 
	 * @param akDonnee,  le nouvelle akDonnee
	 */
	public void setAkDonnee(String akDonnee) {
		this.akDonnee = akDonnee;
	}

	/**
	 * 
	 * getter des valeurs de la donnée
	 * 
	 * @return Map<String, Integer>, les valeurs de la donnée
	 */
	public Map<String, Integer> getValeurs() {
		return valeurs;
	}

	/**
	 * 
	 * setter des valeurs
	 * 
	 * @param valeurs,  les nouvelles valeurs de la donnée
	 */
	public void setDonnees(Map<String, Integer> valeurs) {
		this.valeurs = valeurs;
	}
	
	/**
	 * 
	 * fonction permettant d'ajouter une valeur dans la liste valeur 
	 * 
	 */
	public void addDonnee(String nom, int valeur) {
		this.valeurs.put(nom, valeur);
	}
	
	/**
	 * 
	 * fonction permettant de supprimer une valeur dans la liste valeur 
	 * 
	 */
	public void delDonnee(String nom) {
		this.valeurs.remove(nom);
	}

}
