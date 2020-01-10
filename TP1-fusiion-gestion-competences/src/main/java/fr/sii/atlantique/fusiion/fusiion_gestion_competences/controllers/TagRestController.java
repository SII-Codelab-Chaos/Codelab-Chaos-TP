package fr.sii.atlantique.fusiion.fusiion_gestion_competences.controllers;

import java.util.List;

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

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Tag;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.TagueSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.DefautException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.NotFoundException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.TagueSurJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.services.CompetenceService;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 * Classe permettant d'effectuer des appels commencant par /Tags
 */
@RestController
@RequestMapping(value = "/tags")
@Validated
@PreAuthorize("@webSecurity.estAuthorise(authentication)")
public class TagRestController {

	@Autowired
	private CompetenceService service;

	/**
	 * Constructeur du controlleur REST
	 * 
	 * @param service, le service pour la gestion des données
	 */
	@Autowired
	public TagRestController() {
	}

	/**
	 * methode permettant de retourner tous les tags présent dans la base de donnée
	 * 
	 * @return ResponseEntity<List<Tag>>, la liste de tous les tags sous forme de reponse http 200
	 */
	@GetMapping
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'getAll' ,'TagRestController')")
	public ResponseEntity<List<Tag>> getAll(){
		return ResponseEntity.ok(service.getAllTag());
	}

	/**
	 * permet de récupérer un tag
	 * 
	 * @param akTag, la clef du tag
	 * @return Les details d'un tag
	 */
	@GetMapping(value = "/{akTag}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'findByAkTag' ,'TagRestController')")
	public ResponseEntity<Tag> findByAkTag(@PathVariable String akTag){
		return ResponseEntity.ok(service.findByAkTag(akTag));
	}
	
	/**
	 * permet de creer un nouveau tag dans le service
	 * 
	 * @param tag, le tag à ajouter
	 * @return ResponseEntity<tag>, la reponse http contenant en body le tag ajouté
	 */
	@PostMapping(consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'createTag' ,'TagRestController')")
	public ResponseEntity<Tag> createTag(@RequestBody @Validated Tag tag) throws DefautException{
			
		return 	ResponseEntity.ok(service.createTag(tag));		
	}
	
	/**
	 * permet de modifer un tag dans le service
	 * 
	 * @param tag, le tag à modifier
	 * @return ResponseEntity<tag>, la reponse http contenant en body le tag modifié
	 */
	@PutMapping(value = "/{akTag}", consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'modifierTag' ,'TagRestController')")
	public ResponseEntity<Tag> modifierTag(@RequestBody @Validated Tag tag) throws DefautException{
			
		return 	ResponseEntity.ok(service.modifierTag(tag));		
	}
	
	/**
	 * permet de supprimer un tag dans le service
	 * 
	 * @param tag, le tag à supprimer
	 * @return ResponseEntity<Tag> la reponse http contenant en body le tag supprimer
	 */
	@DeleteMapping(value = "/{akTag}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'supprimerTag' ,'TagRestController')")
	public ResponseEntity<Tag> supprimerTag(@PathVariable String akTag) throws DefautException{
			
		return 	ResponseEntity.ok(service.supprimerTag(akTag));		
	}
	
	/**
	 * permet de trouver tous les tags correspondant à un mot
	 * 
	 * @param like, le mot recherchée
	 * @return ResponseEntity<Tag> la reponse http contenant en body la liste des tags correspondants
	 */
	@GetMapping(params = {"like", "limit"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'findLikeTag' ,'TagRestController')")
	public ResponseEntity<List<Tag>> findLikeTag(@RequestParam(value="like", required=true) String like, @RequestParam(value="limit", required=true) int limit){
		return ResponseEntity.ok(service.tagsParNom(like, limit));
	}

     /**
	 * Permet de lié un tag a une compétence
	 * 
	 * @param akCompetence la cle de la competence
	 * @param tagueSurJson Les informations necessaires
	 * @return La relation créée
	 * @throws DefautException
	 */
	@PostMapping(consumes = {"application/json"}, value = "/competences/{akCompetence}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'createRelationCompTag' ,'TagRestController')")
	public ResponseEntity<TagueSur> createRelationCompTag(@PathVariable String akCompetence, @RequestBody @Validated TagueSurJSON tagueSurJson)
		throws DefautException{
		return ResponseEntity.ok(service.ajouterTagueSur(akCompetence, tagueSurJson));
	}
	
	/**
	 * Liste les competences par un akTag
	 * 
	 * @param akTag la cle du tag
	 * @return la liste des competences liées a ce tag
	 */
	@GetMapping(value = "/{akTag}/competences")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'findCompetencesByAkTag' ,'TagRestController')")
	public ResponseEntity<List<Competence>> findCompetencesByAkTag(@PathVariable String akTag){
		return ResponseEntity.ok(service.findCompetencesByAkTag(akTag));
	}
	
	/**
	 * Permet de supprimer une relation Tag / competence
	 * 
	 * @param akTag la cle du tag
	 * @param akCompetence la cle de la competence
	 * @return Le tag supprimé
	 * @throws NotFoundException
	 */
	@DeleteMapping(value = "/{akTag}/competences/{akCompetence}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'supprimerRelationTag' ,'TagRestController')")
	public ResponseEntity<TagueSur> supprimerRelationTag(@PathVariable String akTag, @PathVariable String akCompetence) throws DefautException{
		return 	ResponseEntity.ok(service.supprimerRelationTag(akTag, akCompetence));
	}
}
