package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access;

import java.util.EnumSet;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.User;

/**
 * Classe permettant de vérifier les autorisations de l'utilisateur
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
@Component
public class WebSecurity {
	
	/**
	 * methode permettant de vérifier les autorisations de l'utilisateur
	 * @param authentication, le composant spring contenant les informations de l'utilisateur
	 * @return boolean, vrai si l'utilisateur est un admin, faux sinon
	 */
	public boolean estAuthorise(Authentication authentication) {
		return estAdmin(authentication);
	}
	
	/**
	 * methode permettant de vérifier les autorisations de l'utilisateur
	 * @param authentication, le composant spring contenant les informations de l'utilisateur
	 * @param nomfonction, le nom de la fonction pour laquelle on vérifiera les autorisations de l'utilisateur
	 * @param nomController, le nom du controller auquelle appartient la fonction
	 * @return boolean, vrai si l'utilisateur est autorisée, faux sinon
	 */
	public boolean estAuthorise(Authentication authentication,String nomFonction, String nomController) {
		if(estAdmin(authentication))
			return true;
		else {
			return EnumSet.allOf(Roles.class)
					.stream()
					.filter(role -> ((User) authentication.getPrincipal()).getRoles().contains(role.getNom()))
					.anyMatch(role -> role.possedeAutorisations(nomFonction, nomController));
		}
	}
	
	/**
	 * methode permettant de vérifier les autorisations de l'utilisateur
	 * @param authentication, le composant spring contenant les informations de l'utilisateur
	 * @param akCollaborateur, l'identifiant de l'utilisateur pour lequel des informations ont été demandé
	 * @param nomfonction, le nom de la fonction pour laquelle on vérifiera les autorisations de l'utilisateur
	 * @param nomController, le nom du controller auquelle appartient la fonction
	 * @return boolean, vrai si l'utilisateur est autorisée, faux sinon
	 */
	public boolean estAuthorise(Authentication authentication,String akCollaborateur, String nomFonction, String nomController) {
		if(estAdmin(authentication))
			return true;
		else {
			return EnumSet.allOf(Roles.class)
					.stream()
					.filter(role -> ((User) authentication.getPrincipal()).getRoles().contains(role.getNom()))
					.anyMatch(
							role -> role.possedeAutorisations(nomFonction, nomController, false)
									|| (role.possedeAutorisations(nomFonction, nomController, true) && verifierAkUser(authentication, akCollaborateur))
					);
		}
	}
	
	/**
	 * methode permettant de vérifier si l'utilisateur est un admin
	 * @param authentication, le composant spring contenant les informations de l'utilisateur
	 * @return boolean, vrai si l'utilisateur est ADMIN, faux sinon
	 */
	private boolean estAdmin(Authentication authentication) {
		return ((User) authentication.getPrincipal()).getRoles().contains(Roles.ADMIN.getNom());
	}
	
	/**
	 * methode permettant de vérifier si l'utilisateur correspond à l'identifiant demandé
	 * @param authentication, le composant spring contenant les informations de l'utilisateur
	 * @param akCollaborateur, l'identifiant de l'utilisateur pour lequel des informations ont été demandé
	 * @return boolean, vrai si l'utilisateur demande ses propres infos, faux sinon
	 */
	private boolean verifierAkUser(Authentication authentication, String akCollaborateur) {
		return ((User) authentication.getPrincipal()).getEmailSII().equals(akCollaborateur);
	}
	
}