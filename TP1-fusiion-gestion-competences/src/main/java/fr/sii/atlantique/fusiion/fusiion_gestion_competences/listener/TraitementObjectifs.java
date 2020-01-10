package fr.sii.atlantique.fusiion.fusiion_gestion_competences.listener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.AAtteindre;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.CalculObjectifsJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifNouveau;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifNouvelleEvaluation;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.AAtteindreRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CollaborateurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.ObjectifRepository;

/**
 * 
 * Classe permettant de traiter la réalisation d'une action par un collaborateur au niveau des objectifs
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 */
@Component
public class TraitementObjectifs {

	@Autowired
	ObjectifRepository objectifRepository;

	@Autowired
	AAtteindreRepository aAtteindreRepository;

	@Autowired
	CollaborateurRepository collaborateurRepository;

	private final ObjectMapper mapper = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(TraitementObjectifs.class);

	public TraitementObjectifs() {
		super();
	}

	/**
	 * permet de traiter les différentes action réalisé par un collaborateur
	 * @throws IOException 
	 */
	public void receiveActionObjectifs(CalculObjectifsJSON calculObjectifsJSON) throws IOException {
		switch(calculObjectifsJSON.getType()) {
		case "NOUVELLE_EVALUATION" :
			LOGGER.info("TraitementObjectifs[receiveActionObjectifs] - {} , {} ", "NOUVELLE_EVALUATION", calculObjectifsJSON.getDonneeJSON());
			this.traitementNouvelleEvaluation(
					this.mapper.readValue(calculObjectifsJSON.getDonneeJSON(), ObjectifNouvelleEvaluation.class)
					);
			break;
		case "NOUVEAU_OBJECTIF" :
			LOGGER.info("TraitementObjectifs[receiveActionObjectifs] - {} , {} ", "NOUVEAU_OBJECTIF", calculObjectifsJSON.getDonneeJSON());
			this.traitementNouveauObjectif(
					this.mapper.readValue(calculObjectifsJSON.getDonneeJSON(), ObjectifNouveau.class)
					);
			break;
		default : 
			LOGGER.warn("TraitementActionSucces[receiveActionObjectifs] - {} , {} ", "Action non traitée ", calculObjectifsJSON);
			break;
		}
	}


	/**
	 * permet de traiter le cas d'une nouvelle evaluation
	 * @param objectifNouvelleEvaluation
	 * @throws JsonProcessingException 
	 */
	private void traitementNouvelleEvaluation(ObjectifNouvelleEvaluation objectifNouvelleEvaluation) {
		this.objectifRepository.findObjectifsByAkCompetenceToMap(objectifNouvelleEvaluation.getAkCompetence())
		.forEach(objectif -> {
			List<Map<String, Object>> objectifCompetences = objectifExtraireCompetences(objectif);
			List<Map<String, Map<String, Object>>> relationsCollaborateur =  (List<Map<String, Map<String, Object>>>) this.collaborateurRepository.findRelationByAkcollaborateurToMap(
					objectifNouvelleEvaluation.getAkCollaborateur(), 
					objectifCompetences
					.stream()
					.map(competence ->
					(String) competence.get("akCompetence")
							)
					.collect(Collectors.toList())
					);
			if(objectifCompetences
					.stream()
					.allMatch(predicate -> 
					objectifVerifierCompetenceCollaborateur(relationsCollaborateur, predicate)
							)
					) {
				LOGGER.info("Objectifs atteint - {} , {} ", objectifNouvelleEvaluation.getAkCollaborateur(), objectif.get("objectif").get("akObjectif"));
				AAtteindre aAtteindre = this.aAtteindreRepository
						.findOneByAkCollaborateurAndByAkObjectif(objectifNouvelleEvaluation.getAkCollaborateur(), (String) objectif.get("objectif").get("akObjectif"))
						.orElse(
								new AAtteindre(
										this.collaborateurRepository.findOneByAkCollaborateur(objectifNouvelleEvaluation.getAkCollaborateur()).orElseThrow(() -> new RuntimeException("le collaborateur n'existe pas"))
										, this.objectifRepository.findOneByAkObjectif((String) objectif.get("objectif").get("akObjectif")).orElseThrow(() -> new RuntimeException("l'objectif n'existe pas"))
										, LocalDateTime.now()
										, true
										)
								);
				aAtteindre.setValide(true);
				this.aAtteindreRepository.save(aAtteindre);
			}else {
				LOGGER.info("Objectifs non atteint - {} , {} ", objectifNouvelleEvaluation.getAkCollaborateur(), objectif.get("objectif").get("akObjectif"));
				this.aAtteindreRepository
				.findOneByAkCollaborateurAndByAkObjectif(objectifNouvelleEvaluation.getAkCollaborateur(), (String) objectif.get("objectif").get("akObjectif"))
				.ifPresent(consumer -> { 
					consumer.setValide(false);
					this.aAtteindreRepository.save(consumer);
				});

			}
		});
	}

	/**
	 * permet de vérifier qu'une competence d'un collaborateur est superieur à la note minimale d'un objectif
	 * @param relationsCollaborateur
	 * @param objectifCompetence
	 * @return
	 */
	private boolean objectifVerifierCompetenceCollaborateur(
			List<Map<String, Map<String, Object>>> relationsCollaborateur, Map<String, Object> objectifCompetence) {
		return relationsCollaborateur
				.stream()
				.filter(predicate -> relationExtraireAkCompetence(predicate).equals(objectifCompetence.get("akCompetence")) )
				.allMatch(predicate -> 
				collaborateurExtraireNotation(relationExtraireRelations(predicate)
						.stream()
						.sorted((e1, e2) -> (
								collaborateurExtraireDate(e2).compareTo(collaborateurExtraireDate(e1))
								))
						.findFirst()
						.orElseThrow(() -> new RuntimeException())
						)
				>=
				(long) objectifCompetence.get("notation")
						);


	}

	/**
	 * permet de recuperer l'akcompetence
	 * @param predicate
	 * @return
	 */
	private String relationExtraireAkCompetence(Map<String, Map<String, Object>> predicate) {
		return (String) predicate.get("competence").get("akCompetence");
	}

	/**
	 * permet de récupérer la liste des relations
	 * @param predicate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> relationExtraireRelations(Map<String, Map<String, Object>> predicate) {
		return (List<Map<String, Object>>) predicate.get("competence").get("relations");
	}

	/**
	 * permet de traiter le cas de l'ajout d'un nouvel objectif
	 *
	 * @throws JsonProcessingException 
	 */
	private void traitementNouveauObjectif(ObjectifNouveau objectifNouveau) {	

		collaborateurRepository
		.rechercherParCompetencesToMap(objectifNouveau.getDonnees().keySet())
		.forEach(collaborateur -> {
			if(

					collaborateurExtraireCompetences(collaborateur)
					.stream()
					.allMatch(predicate ->
					collaborateurExtraireNotation(
							collaborateurExtraireRelations(predicate)
							.stream()
							.sorted((e1, e2) -> (
									collaborateurExtraireDate(e2).compareTo(collaborateurExtraireDate(e1))
									))
							.findFirst()
							.orElseThrow(() -> new RuntimeException())
							)
					>= 
					(long) objectifNouveau
					.getDonnees()
					.get(collaborateurExtraireAkCompetence(predicate))
							)
					) {
				this.aAtteindreRepository.save(
						new AAtteindre(
								this.collaborateurRepository.findOneByAkCollaborateur((String) collaborateur.get("collaborateur").get("akCollaborateur")).orElseThrow(() -> new RuntimeException("le collaborateur n'existe pas"))
								, this.objectifRepository.findOneByAkObjectif(objectifNouveau.getAkObjectif()).orElseThrow(() -> new RuntimeException("l'objectif n'existe pas"))
								, LocalDateTime.now()
								, true
								)
						);
			}
		});
	}

	/**
	 * permet de récupérer les competences
	 * @param objectif
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> objectifExtraireCompetences(Map<String, Map<String, Object>> objectif) {
		return (List<Map<String, Object>>) objectif.get("objectif").get("competences");
	}

	/**
	 * permet de récuperer les relations
	 * @param competence
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> collaborateurExtraireRelations(Map<String, Object> competence) {
		return (List<Map<String, Object>>) competence.get("relations");
	}

	/**
	 * permet de récupérer la liste des comeptences
	 * @param collaborateur
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> collaborateurExtraireCompetences(Map<String, Map<String, Object>> collaborateur) {
		return (List<Map<String, Object>>) collaborateur.get("collaborateur").get("competences");
	}

	/**
	 * permet de récuprer la date 
	 * @param relation
	 * @return
	 */
	private LocalDateTime collaborateurExtraireDate(Map<String, Object> relation) {
		return LocalDateTime.parse((CharSequence) relation.get("date"));
	}

	/**
	 * permet de récuperer l'akCompetence
	 * @param competence
	 * @return
	 */
	private String collaborateurExtraireAkCompetence(Map<String, Object> competence) {
		return (String) competence.get("akCompetence");
	}

	/**
	 * permet de récupérer la notation
	 * @param relation
	 * @return
	 */
	private long collaborateurExtraireNotation(Map<String, Object> relation) {
		return (long) relation.get("notation");
	}




}
