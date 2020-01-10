package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body;

import java.util.Map;

import javax.validation.constraints.NotBlank;

/**
 *  * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de faire passerelle entre le back et le front via des données JSON
 * C'est un model de JSON pour la création d'un nouveau objectif
 * 
 */
public class ObjectifJSON {
	@NotBlank
	private String nom;
	
	@NotBlank
	private String description;
	
	private String badge;
	
	private String type;
	
	private Map<String,Integer> competences;
	
	/**
	 * Constructeur par defaut de la classe
	 */
	public ObjectifJSON() {
		super();
	}

	/**
	 * Constructeur de la classe ObjectifJSON
	 * @param nom
	 * @param description
	 * @param badge
	 * @param competences
	 */
	public ObjectifJSON(String nom, String description, String badge, String type, Map<String,Integer> competences) {
		super();
		this.nom = nom;
		this.description = description;
		this.badge = badge;
		this.competences = competences;
		this.type = type;
	}
	
	/**
	 * Constructeur de la classe ObjectifJSON
	 * @param akObjectif
	 * @param nom
	 * @param description
	 * @param competences
	 */
	public ObjectifJSON(String nom, String description, Map<String,Integer> competences) {
		super();
		this.nom = nom;
		this.description = description;
		this.competences = competences;
	}
	
	/**
	 * Constructeur de la classe ObjectifJSON
	 * @param akObjectif
	 * @param nom
	 * @param description
	 */
	public ObjectifJSON(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
	}

	/**
	 * getter du nom de l'objectif
	 * @return String, nom de l'objectif
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * setter du nom de l'objectif
	 * @param description, la nouveau nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * getter de la description de l'objectif
	 * @return String, la description de l'objectif
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * setter de la description de l'objectif
	 * @param description, la nouvelle description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * getter du badge de l'objectif
	 * @return String, le badge de l'objectif
	 */
	public String getBadge() {
		return badge;
	}
	
	/**
	 * setter du badge de l'objectif
	 * @param badge, le nouveau badge
	 */
	public void setBadge(String badge) {
		this.badge = badge;
	}
	
	/**
	 * getter des competences de l'objectif
	 * @return  Map<String, Integer>, la liste des ak compétences et de la notation minimal
	 */
	public Map<String, Integer> getCompetences() {
		return competences;
	}
	
	/**
	 * setter des competences de l'objectif
	 * @param competences, la nouvelle liste des ak compétences et de la notation minimal
	 */
	public void setCompetences(Map<String, Integer> competences) {
		this.competences = competences;
	}
	
	/**
	 * getter du type du badge de l'objectif
	 * @return String, le type du badge de l'objectif
	 */
	public String getType() {
		return type;
	}
	/**
	 * setter du type du badge de l'objectif
	 * @param type, le nouveau type du badge
	 */
	public void setType(String type) {
		this.type = type;
	}
}
