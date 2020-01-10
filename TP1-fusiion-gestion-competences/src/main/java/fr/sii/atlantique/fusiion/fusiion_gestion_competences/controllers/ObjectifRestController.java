package fr.sii.atlantique.fusiion.fusiion_gestion_competences.controllers;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.NotFoundException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.ObjectifJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.ObjectifDoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.services.CompetenceService;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 * Classe permettant d'effectuer des appels commencant par /objectif sur le service
 */
@RestController
@RequestMapping("/objectifs")
@Validated
@PreAuthorize("@webSecurity.estAuthorise(authentication)")
public class ObjectifRestController {

	@Autowired
	private CompetenceService service;	
	
	/**
	 * Constructor 
	 * @param service,  le service pour la gestion des données
	 */
	@Autowired
	public ObjectifRestController() {
	}
	
	/**
	 * permet de recuperer tous les objectifs du service
	 * 
	 * @return ResponseEntity<List<Objectif>>, la liste de tous les objectifs sous forme de reponse http 200
	 */
	@GetMapping
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'getAll' ,'ObjectifRestController')")
	public ResponseEntity<List<Objectif>> getAll(){
		return ResponseEntity.ok(service.getAllObjectif());
	}
	
	/**
	 * permet de creer une nouvel object dans le service
	 * 
	 * @param objectifJSON, l'objectif à ajouter
	 * @return ResponseEntity<Objectif>, la reponse http contenant en body l'objectif ajouté
	 * @throws Exception 
	 */
	@PostMapping(consumes = {"application/json"})
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'createObjectif' ,'ObjectifRestController')")
	public ResponseEntity<Objectif> createObjectif(@RequestBody @Validated ObjectifJSON objectifJSON) throws Exception{
		return 	ResponseEntity.ok(service.createObjectif(objectifJSON));		
	}
	
	/**
	 * permet de recupérer le detail d'un objectif
	 * 
	 * @param akObjectif, l'akobjectif recherché
	 * @return ResponseEntity<Objectif>, la reponse http contenant en body
	 * @throws NotFoundException 
	 */
	@GetMapping(value = "{akObjectif}")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'getDetailObjectif' ,'ObjectifRestController')")
	public ResponseEntity<Objectif> getDetailObjectif(@PathVariable @NotNull String akObjectif) throws NotFoundException{
		return ResponseEntity.ok(service.findByAkObjectif(akObjectif));
	}
	
	
	/**
	 * permet de recupérer le detail, donc toutes les relations DoitEtreEvalue d'un objectif
	 * 
	 * @param akObjectif, l'akobjectif recherché
	 * @return ResponseEntity<List<DoitEtreEvalué>>, la reponse http contenant en body
	 * @throws NotFoundException 
	 */
	@GetMapping(value = "{akObjectif}/relations")
	@PreAuthorize("@webSecurity.estAuthorise(authentication, 'getDetailObjectifRealtion' ,'ObjectifRestController')")
	public ResponseEntity<List<ObjectifDoitEtreEvalue>> getDetailObjectifRealtion(@PathVariable @NotNull String akObjectif) throws NotFoundException{
		return ResponseEntity.ok(service.findByAkObjectifRelation(akObjectif));
	}
}
