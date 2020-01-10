package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


/**
 *  * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de faire passerelle entre le back et le front via des données JSON
 * C'est un model de JSON pour la création d'une nouvelle relation
 * 
 */
public class CompetentSurJSON {

	@NotBlank
	private String akCompetence;
	@Min(0) @Max(5)
	private int notation;

	private LocalDateTime dateDebut;

	/**
	 * Constructeur par defaut de la classe
	 */
	public CompetentSurJSON() {	}

	/**
	 * Constructeur de la classe CompetentSurJSON
	 * @param akCompetence
	 * @param notation
	 * @param dateDebut
	 */
	public CompetentSurJSON(String akCompetence, int notation, LocalDateTime dateDebut) {
		super();
		this.akCompetence = akCompetence;
		this.notation = notation;
		this.dateDebut = dateDebut;
	}

	/**
	 * Constructeur de la classe CompetentSurJSON
	 * @param akCompetence, l'ak de la compétence qui sera le noeud d'arrivé de la relation
	 * @param notation, la notation du collaborateur
	 */
	public CompetentSurJSON(String akCompetence, int notation) {
		super();
		this.akCompetence = akCompetence;
		this.notation = notation;
	}


	public LocalDateTime getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * getter de l'ak de la competence
	 * @return String, l'ak de la compétence
	 */
	public String getAkCompetence() {
		return akCompetence;
	}

	/**
	 * setter de l'ak de la competence
	 * @param akCompetence, le nouveau ak de la competence
	 */
	public void setAkCompetence(String akCompetence) {
		this.akCompetence = akCompetence;
	}

	/**
	 * getter de la notation
	 * @return int, la notation de la relation
	 */
	public int getNotation() {
		return notation;
	}

	/**
	 * setter de la notation
	 * @param notation, la nouvelle notation de la relation
	 */
	public void setNotation(int notation) {
		this.notation = notation;
	}

	/**
	 * permet de mettre sous forme de chaine de caractère une instance de la classe
	 * @return String, l'instance de la CompetentSurJSON mise sous forme de chaine de caractère
	 * 
	 * */
	@Override
	public String toString() {
		return "CompetentSurJSON [akCompetence=" + akCompetence
				+ ", notation=" + notation + "]";
	}


}
