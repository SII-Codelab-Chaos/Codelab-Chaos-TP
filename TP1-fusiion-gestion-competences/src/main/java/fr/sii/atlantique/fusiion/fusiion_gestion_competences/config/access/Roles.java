package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.Autorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.DIOAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.CommercialAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.STAFFAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.RHAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.CPAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.CollaborateurAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.MTAutorisations;

/**
 * permet de lister les roles et leur autorisations
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public enum Roles {
	ADMIN("ADMIN"),
	DIO("DIO" , EnumSet.allOf(DIOAutorisations.class).stream().collect(Collectors.toSet())),
	COMMERCIAL("COMMERCIAL", EnumSet.allOf(CommercialAutorisations.class).stream().collect(Collectors.toSet())),
	STAFF("STAFF", EnumSet.allOf(STAFFAutorisations.class).stream().collect(Collectors.toSet())),
	RH("RH", EnumSet.allOf(RHAutorisations.class).stream().collect(Collectors.toSet())),
	CP("CP", EnumSet.allOf(CPAutorisations.class).stream().collect(Collectors.toSet())),
	COLLABORATEUR("COLLABORATEUR", EnumSet.allOf(CollaborateurAutorisations.class).stream().collect(Collectors.toSet())),
	MT("TEMPORAIRE", EnumSet.allOf(MTAutorisations.class).stream().collect(Collectors.toSet()));
	
	private String nom = "";
	private Set<Autorisations> autorisations;
	
	Roles(String nom) {
		this.nom = nom;
	}
	
	Roles(String nom, Set<Autorisations> autorisations) {
		this.nom = nom;
		this.autorisations =  autorisations;
	}
	
	/*
	 * getter du nom
	 * @return le nom du role
	 * */
	public String getNom() {
		return this.nom;
	}
	
	/*
	 * getter des autorisations
	 * @return les autorisations
	 * */
	public Set<Autorisations> getAutorisations() {
		return this.autorisations;
	}
	
	/*
	 * methode permettant de savoir si le role a l'autorisation d'accéder à une fonction particulière
	 * @param nom, le nom d'une fonction
	 * @param restController, le nom d'un restController
	 * @return Boolean, vraie si le role possede une autorisation, faux sinon
	 * */
	public boolean possedeAutorisations(String nom, String restController) {
		return autorisations.stream()
							.anyMatch(autorisation -> autorisation.getNom().equals(nom) && autorisation.getRestController().equals(restController));
	}
	
	/*
	 * methode permettant de savoir si le role a l'autorisation d'accéder à une fonction particulière et si l'autorisation est limité à ses infos
	 * @param nom, le nom d'une fonction
	 * @param restController, le nom d'un restController
	 * @return Boolean, vraie si le role possede une autorisation, faux sinon
	 * */
	public boolean possedeAutorisations(String nom, String restController, boolean limiteASesInfos) {
		return autorisations.stream()
							.anyMatch(
									autorisation -> 
										autorisation.getNom().equals(nom) && autorisation.getRestController().equals(restController) && autorisation.getLimiteASesInfos() ==  limiteASesInfos
							);
	}
}
