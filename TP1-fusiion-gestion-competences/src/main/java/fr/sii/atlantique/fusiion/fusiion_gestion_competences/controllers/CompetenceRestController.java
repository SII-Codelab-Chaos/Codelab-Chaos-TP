package fr.sii.atlantique.fusiion.fusiion_gestion_competences.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.MentionApprendreSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.MentionInteretPour;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Tag;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.DefautException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.NotFoundException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.CompetentSurJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.CompetencesParCollaborateurJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.services.CompetenceService;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 * Classe permettant d'effectuer des appels commencant par /competence sur le service
 */
@RestController
@RequestMapping("/competences")
@Validated
@PreAuthorize("@webSecurity.estAuthorise(authentication)")
public class CompetenceRestController {

	@Autowired
	private CompetenceService service;

	/**
	 * Constructor 
	 * @param service,  le service pour la gestion des données
	 */
	@Autowired
	public CompetenceRestController() {
	}

	/**
	 * permet de recuperer toutes les competences du service
	 * 
	 * @return ResponseEntity<List<Competence>>, la liste de toutes les compétences sous forme de reponse http 200
	 */
	@GetMapping
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'getAll' ,'CompetenceRestController')")
	public ResponseEntity<List<Competence>> getAll(){
		return ResponseEntity.ok(service.getAllCompetence());
	}

	/**
	 * permet de recuperer le détail d'une competence
	 * 
	 * @param akCompetence, la clef de la competence
	 * @return ResponseEntity<Competence>, la compétence sous forme de reponse http 200
	 */
	@GetMapping(value = "{akCompetence}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'getDetailCompetence' ,'CompetenceRestController')")
	public ResponseEntity<Competence> getDetailCompetence(@PathVariable @NotNull String akCompetence) throws NotFoundException{
		return ResponseEntity.ok(service.getDetailCompetence(akCompetence));
	}

	/**
	 * permet de tester la recuperation de toutes les relation du service
	 * 
	 * @return ResponseEntity<List<CompetentSur>>, toutes les relations CompetentSur sous forme de reponse http 200
	 */
	@GetMapping(value = "/testallrelation")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'testAllRelation' ,'CompetenceRestController')")
	public ResponseEntity<List<CompetentSur>> testAllRelation(){
		return ResponseEntity.ok(
				service.getAllRelation());
	}

	/**
	 * permet de creer une nouvelle competence dans le service
	 * 
	 * @param competence, la competence à ajouter
	 * @return ResponseEntity<Competence>, la reponse http contenant en body la competence ajouté
	 * @throws JsonProcessingException 
	 * @throws AmqpException 
	 */
	@PostMapping(consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'createCompetence' ,'CompetenceRestController')")
	public ResponseEntity<Competence> createCompetence(@RequestBody @Validated Competence competence) throws DefautException, AmqpException, JsonProcessingException{
		return 	ResponseEntity.ok(service.createCompetence(competence));		
	}

	/**
	 * permet de modifer une competence dans le service
	 * 
	 * @param competence, la compétence à modifier
	 * @return ResponseEntity<Competence>, la reponse http contenant en body la compétence modifié
	 */
	@PutMapping(value = "/{akCompetence}", consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'modifierCompetence' ,'CompetenceRestController')")
	public ResponseEntity<Competence> modifierCompetence(@RequestBody @Validated Competence competence) throws DefautException{
		return 	ResponseEntity.ok(service.modifierCompetence(competence));		
	}

	/**
	 * permet de creer une nouvelle relation CompetentSur entre une compétence et un collaborateur dans le service
	 * 
	 * @param akCollaborateur, l' ak du collaborateur qui sera le noeud de départ de la relation à ajouter
	 * @param competenceSurJSON, la classe qui sera extraite suite au parsage du body JSON
	 * @return ResponseEntity<CompetentSur>, la reponse http contenant en body la relation ajouté
	 * @throws JsonProcessingException 
	 * @throws AmqpException 
	 */
	@PostMapping(consumes = {"application/json"}, value = "/collaborateur/{akCollaborateur}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'createRelationCollabComp' ,'CompetenceRestController')")
	public ResponseEntity<CompetentSur> createRelationCollabComp(@PathVariable @Email String akCollaborateur, @RequestBody @Validated CompetentSurJSON competentSurJson)
			throws DefautException, AmqpException, JsonProcessingException{

		return ResponseEntity.ok(service.ajouterCompetentSur(akCollaborateur, competentSurJson));
	}

	/**
	 * permet de recupérer toutes les compétences d'un collaborateur
	 * 
	 * @param akCollaborateur, l' ak du collaborateur recherché
	 * @return ResponseEntity<List<CompetencesParCollaborateurJSON>>, la reponse http contenant en body la liste des compétences de ce collaborateur
	 */
	@GetMapping(value = "/collaborateur/{akCollaborateur}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'competencesUnCollaborateur' ,'CompetenceRestController')")
	public ResponseEntity<List<CompetencesParCollaborateurJSON>> competencesUnCollaborateur(@PathVariable @Email String akCollaborateur){
		return ResponseEntity.ok(service.competencesUnCollaborateur(akCollaborateur));
	}

	/**
	 * Permet de connaitre les competences ayant pour prefix like
	 * 
	 * @param like, la chaine de caractère recherchée
	 * @return liste de competences correspondantes
	 */
	@GetMapping(params = {"like", "limit"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'findLikeCompetence' ,'CompetenceRestController')")
	public ResponseEntity<List<Competence>> findLikeCompetence(@RequestParam(value="like", required=true) String like, @RequestParam(value="limit", required=true) int limit){
		return ResponseEntity.ok(service.findLikeCompetence(like, limit));
	}

	/**
	 * Permet de connaitre le nombre de semaine de séniorité sur une compétence d'un collaborateur
	 * 
	 * @param akCompetence, la clef de la competence
	 * @param akCollaborateur, la clef du collaborateur
	 * @return le nombre de semaine que possède un collaborateur sur une competence
	 */
	@GetMapping(value = "{akCompetence}/seniorite/collaborateur/{akCollaborateur}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'getSenioriteCompetence' ,'CompetenceRestController')")
	public ResponseEntity<Long> getSenioriteCompetence(@PathVariable @NotNull String akCompetence, @PathVariable @Email String akCollaborateur) 
			throws NotFoundException{
		return ResponseEntity.ok(service.senioriteSemaine(akCollaborateur, akCompetence));
	}

	/**
	 * liste les tags pour une competence
	 * 
	 * @param akCompetence, la clef de la competence
	 * @return la liste des tags liés a cette competence
	 */
	@GetMapping(value = "/{akCompetence}/tags")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'findTagsByAkCompetence' ,'CompetenceRestController')")
	public ResponseEntity<List<Tag>> findTagsByAkCompetence(@PathVariable String akCompetence){
		return ResponseEntity.ok(service.findByAkCompetence(akCompetence));
	}

	/**
	 * permet de supprimer une compétence
	 * 
	 * @param akCompetence, l'identifiant de la competence
	 * @return la competence supprimée
	 */
	@DeleteMapping(value = "/{akCompetence}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'deleteByAkCompetence' ,'CompetenceRestController')")
	public ResponseEntity<Competence> deleteByAkCompetence(@PathVariable String akCompetence) throws DefautException{
		return ResponseEntity.ok(service.deleteByAkCompetence(akCompetence));
	}

	/**
	 * Permet de savoir si un collaborateur souhaite apprendre une compétence
	 * 
	 * @param akCompetence, la clef de la competence
	 * @param akCollaborateur, la clef du collaborateur
	 * @return un booléen, true, si le collaborateur souhaite apprendre une compétence
	 */
	@GetMapping(value = "{akCompetence}/apprendre/collaborateur/{akCollaborateur}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'getMentionApprendreCompetence' ,'CompetenceRestController')")
	public ResponseEntity<Integer> getMentionApprendreCompetence(@PathVariable @NotNull String akCompetence, @PathVariable @Email String akCollaborateur) 
			throws NotFoundException{
		return ResponseEntity.ok(service.findApprendreCompetence(akCollaborateur, akCompetence));
	}


	/**
	 * permet de modifier une la mention ApprendreSur dans le service
	 * Modification de la relation
	 * @param competence, la compétence concernée
	 * @param collaborateur, le collaborateur concerné
	 * @param mention, true si le collaborateur souhaite apprendre une nouvelle compétence, false si non
	 * @return ResponseEntity<Boolean>, la reponse http contenant en body true si tout c'est bien passé
	 */
	@PutMapping(value = "{akCompetence}/apprendre/collaborateur/{akCollaborateur}", consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'modifierApprendreCompetence' ,'CompetenceRestController')")
	public ResponseEntity<MentionApprendreSur> modifierApprendreCompetence(@PathVariable @NotNull String akCompetence, @PathVariable @NotNull String akCollaborateur, @RequestBody @Validated MentionApprendreSur mention) throws DefautException{
		return ResponseEntity.ok(service.modifierApprendreCompetence(akCollaborateur, akCompetence, mention));		
	}


	/**
	 * permet de modifier une la mention ApprendreSur dans le service
	 * @param competence, la compétence concernée
	 * @param collaborateur, le collaborateur concerné
	 * @param mention, true si le collaborateur souhaite apprendre une nouvelle compétence, false si non
	 * @return ResponseEntity<Boolean>, la reponse http contenant en body true si tout c'est bien passé
	 */
	@PostMapping(value = "{akCompetence}/apprendre/collaborateur/{akCollaborateur}", consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'creerApprendreCompetence' ,'CompetenceRestController')")
	public ResponseEntity<MentionApprendreSur> creerApprendreCompetence(@PathVariable @NotNull String akCompetence,@PathVariable @NotNull String akCollaborateur, @RequestBody @Validated MentionApprendreSur mention) throws DefautException{
		return 	ResponseEntity.ok(service.creerApprendreCompetence(akCollaborateur, akCompetence, mention));		
	}

	/**
	 * Permet de savoir si un collaborateur est intéressé par une compétence
	 * Creation de la relation
	 * @param akCompetence, la clef de la competence
	 * @param akCollaborateur, la clef du collaborateur
	 * @return un booléen, true, si le collaborateur est intéressé par une compétence
	 */
	@GetMapping(value = "{akCompetence}/interet/collaborateur/{akCollaborateur}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'getMentionInteretCompetence' ,'CompetenceRestController')")
	public ResponseEntity<Integer> getMentionInteretCompetence(@PathVariable @NotNull String akCompetence, @PathVariable @Email String akCollaborateur) 
			throws NotFoundException{
		return ResponseEntity.ok(service.findInteretCompetence(akCollaborateur, akCompetence));
	}

	/**
	 * permet de modifier une la mention InteretPour dans le service
	 * Modification de la relation
	 * @param competence, la compétence concernée
	 * @param collaborateur, le collaborateur concerné
	 * @param mention, true si le collaborateur souhaite apprendre une nouvelle compétence, false si non
	 * @return ResponseEntity<Boolean>, la reponse http contenant en body true si tout c'est bien passé
	 */
	@PutMapping(value = "{akCompetence}/interet/collaborateur/{akCollaborateur}", consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'modifierInteretCompetence' ,'CompetenceRestController')")
	public ResponseEntity<MentionInteretPour> modifierInteretCompetence(@PathVariable @NotNull String akCompetence,@PathVariable @NotNull String akCollaborateur, @RequestBody @Validated MentionInteretPour mention) throws DefautException{
		return 	ResponseEntity.ok(service.modifierInteretCompetence(akCollaborateur, akCompetence, mention));
	}

	/**
	 * permet de modifier une la mention InteretPour dans le service
	 * @param competence, la compétence concernée
	 * @param collaborateur, le collaborateur concerné
	 * @param mention, true si le collaborateur souhaite apprendre une nouvelle compétence, false si non
	 * @return ResponseEntity<Boolean>, la reponse http contenant en body true si tout c'est bien passé
	 */
	@PostMapping(value = "{akCompetence}/interet/collaborateur/{akCollaborateur}", consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, #akCollaborateur, 'creerInteretCompetence' ,'CompetenceRestController')")
	public ResponseEntity<MentionInteretPour> creerInteretCompetence(@PathVariable @NotNull String akCompetence,@PathVariable @NotNull String akCollaborateur, @RequestBody @Validated MentionInteretPour mention) throws DefautException{
		return 	ResponseEntity.ok(service.creerInteretCompetence(akCollaborateur, akCompetence, mention));	
	}


}
