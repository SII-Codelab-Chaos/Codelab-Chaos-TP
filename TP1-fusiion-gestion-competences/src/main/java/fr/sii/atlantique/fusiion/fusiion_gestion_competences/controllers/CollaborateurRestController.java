package fr.sii.atlantique.fusiion.fusiion_gestion_competences.controllers;

import java.util.List;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.NotFoundException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifRetourJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.services.CompetenceService;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 * Classe permettant d'effectuer des appels commencant par /collaborateur
 */
@RestController
@RequestMapping(value = "/collaborateurs")
@Validated
@PreAuthorize("@webSecurity.estAuthorise(authentication)")
public class CollaborateurRestController {
	
	@Autowired
	private CompetenceService service;
	
	/**
	 * Constructeur du controlleur REST
	 * @param service, le service pour la gestion des données
	 */
	public CollaborateurRestController() {
	}

	/**
	 * methode permettant de retourner tous les collaborateurs présent dans la base de donnée, accessible par /collaborateur
	 * @return ResponseEntity<List<Collaborateur>>, la liste de tous les collaborateurs sous forme de reponse http 200
	 */
	@GetMapping
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'getAll' ,'CollaborateurRestController')")
	public ResponseEntity<List<Collaborateur>> getAll(){
		return ResponseEntity.ok(service.getAllCollaborateur());
	}

	/**
	 * Recherche de collaborateurs avec une competence
	 * @param competence, la clef de la competence (son akCompetence)
	 * @return Une liste de collaborateur ayant cette competence
	 */
	@GetMapping(value = "/competence", params = "competence")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'rechercherParCompetence' ,'CollaborateurRestController')")
	public ResponseEntity<List<Collaborateur>> rechercherParCompetence(@RequestParam(value="competence") String competence){
		return ResponseEntity.ok(service.rechercherParCompetence(competence));
	}

	/**
	 * Recherche de collaborateurs avec une ou plusieurs competences
	 * @param competences, la liste des ak des competences recherchée
	 * @return Une liste de collaborateur possedant une relation vers toutes les competences
	 */
	@PostMapping(value = "/competences")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'rechercherParCompetences' ,'CollaborateurRestController')")
	public ResponseEntity<List<Collaborateur>> rechercherParCompetences(@RequestBody List<String> competences){
		return ResponseEntity.ok(service.rechercherParCompetences(competences));
	}
	
	/**
	 * Permet d'attribuer des objectifs à un collaborateur
	 * 
	 * @param akCollaborateur, l'identifiant Collaborateur
	 * @param listModeleAttribution, la List ModeleAttributionObjectif qu'on souhaite attribué à un collaborateur
	 * @return List<ModeleAttributionObjectif> attribué
	 * @throws NotFoundException 
	 */
	@PostMapping(value = "/{akCollaborateur}/objectifs")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'AjoutObjectifsCollaborateur' ,'CollaborateurRestController')")
	public ResponseEntity<List<String>> AjoutObjectifsCollaborateur(@PathVariable @Email String akCollaborateur, @RequestBody List<String> listModeleAttribution) throws NotFoundException{
		return ResponseEntity.ok(service.ajoutObjectifsCollaborateur(akCollaborateur, listModeleAttribution));
	}
	
	/**
	 * Recherche la liste ObjectifRetourJSON des objectifs atteint ou non par le collaborateur
	 * @param akCollaborateur
	 * @param valide
	 * @return List<ObjectifRetourJSON>
	 */
	@GetMapping(value = "/{akCollaborateur}/objectifs", params = "valide")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'findObjectifsByAkCollaborateurValide' ,'CollaborateurRestController')")
	public ResponseEntity<List<ObjectifRetourJSON>> findObjectifsByAkCollaborateurValide(@PathVariable @Email String akCollaborateur, @RequestParam(value="valide") boolean valide){
		return ResponseEntity.ok(service.findObjectifsByAkCollaborateurValide(akCollaborateur, valide));
	}
	
	/**
	 * Recherche la liste ObjectifRetourJSON des objectifs atteint et non par le collaborateur
	 * @param akCollaborateur
	 * @return List<ObjectifRetourJSON>
	 */
	@GetMapping(value = "/{akCollaborateur}/objectifs")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'findObjectifsByAkCollaborateur' ,'CollaborateurRestController')")
	public ResponseEntity<List<ObjectifRetourJSON>> findObjectifsByAkCollaborateur(@PathVariable @Email String akCollaborateur){
		return ResponseEntity.ok(service.findObjectifsByAkCollaborateur(akCollaborateur));
	}

}
