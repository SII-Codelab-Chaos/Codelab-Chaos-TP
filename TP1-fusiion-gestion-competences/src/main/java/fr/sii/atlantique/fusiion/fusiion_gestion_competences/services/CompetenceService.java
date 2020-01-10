package fr.sii.atlantique.fusiion.fusiion_gestion_competences.services;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.AAtteindre;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.DoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.MentionApprendreSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.MentionInteretPour;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.SenioriteSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Tag;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.TagueSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.CompetenceExistanteException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.DefautException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.NotFoundException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.ObjectifExistantException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.ObjectifJSONException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.exceptions.TagExistantException;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.CalculObjectifsJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifNouveau;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifNouvelleEvaluation;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifRetourJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.CompetentSurJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.ObjectifJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.TagueSurJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.CompetencesParCollaborateurJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.ObjectifDoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.DonneeStatistique;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.Statistique;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes.Action;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.notification.Notification;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.notification.Type;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.AAtteindreRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CollaborateurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetenceRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetentSurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.DoitEtreEvalueRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.ObjectifRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.SenioriteSurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.TagRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.TagueSurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.MentionApprendreSurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.MentionInteretPourRepository;

/**
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 * 
 *         Classe permettant de manipuler les repository et les données qui y
 *         seront
 * 
 */
@Service
public class CompetenceService {

	@Autowired
	private CompetenceRepository competenceRepository;
	@Autowired
	private CollaborateurRepository collaborateurRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private TagueSurRepository tagueSurRepository;
	@Autowired
	private CompetentSurRepository competentSurRepository;
	@Autowired
	private SenioriteSurRepository senioriteSurRepository;
	@Autowired
	private ObjectifRepository objectifRepository;
	@Autowired
	private DoitEtreEvalueRepository doitEtreEvalueRepository;
	@Autowired
	private AAtteindreRepository aAtteindreRepository;
	@Autowired
	private MentionApprendreSurRepository mentionApprendreSurRepository;
	@Autowired
	private MentionInteretPourRepository mentionInteretPourRepository;


	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private TopicExchange topicExchange;
	@Autowired
	private FanoutExchange fanoutExchange;

	private final ObjectMapper mapper = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(CompetenceService.class);

	private static final String ACCENT_STRINGS1 = "aàáâãäå";
	private static final String ACCENT_STRINGS2 = "cç";
	private static final String ACCENT_STRINGS3 ="eèéêëẽ";
	private static final String ACCENT_STRINGS4 ="iìíîïĩ";
	private static final String ACCENT_STRINGS5 = "oðòóôõöø";
	private static final String ACCENT_STRINGS6 = "nñ";
	private static final String ACCENT_STRINGS7 = "uùúûü";
	private static final String ACCENT_STRINGS8 ="yýÿ";

	private final String COMPETENCE_ACTION_QUEUE ="Competence.action";

	private final String COMPETENCE_OBJECTIF_QUEUE ="Competence.objectif";

	private final String STATISTIQUE_ENREGISTRER_QUEUE = "Statistique.enregistrer";
	
	private final String NOTIFICATION_MESSAGE_QUEUE = "Notification.message";

	/**
	 * Constructeur de la classe compétence
	 * @param senioriteSurRepository 
	 * 
	 * @param competenceRepository,
	 *            le repository permettant de manipuler et rechercher des
	 *            compétences
	 * @param collaborateurRepository,
	 *            le repository permettant de manipuler et rechercher des
	 *            collaborateurs
	 * @param competentSurRepository,
	 *            le repository permettant de manipuler et rechercher des
	 *            collaborateurs
	 */
	public CompetenceService() {

	}

	/**
	 * permet de ressortir l'ensemble des compétences présent dans le repository
	 * 
	 * @return List<Competence>, la liste des toutes les compétences
	 */
	public List<Competence> getAllCompetence() {
		LOGGER.info("SERVICE: Appel[getAllCompetence] - {}","Aucun paramètre");
		return (List<Competence>) competenceRepository.findAll();
	}

	/**
	 * permet de ressortir les détails d'une compétence présent dans le repository
	 * 
	 * @return Competence, les détails d'une compétence
	 */
	public Competence getDetailCompetence( String akCompetence) throws NotFoundException{
		LOGGER.info("SERVICE: Appel[getDetailCompetence] - {}", akCompetence);
		Optional<Competence> competence = competenceRepository.findOneByAkCompetence(akCompetence);

		if(!competence.isPresent()) {
			throw new NotFoundException("la compétence id: "+akCompetence+" n'existe pas");		
		}
		return competence.get();
	}

	/**
	 * permet de creer une compétence dans le repository correspondant
	 * 
	 * @param competence,
	 *            la compétence à creer
	 * @return Competence, la compétence créé
	 * @throws CompetenceExistanteException 
	 * @throws JsonProcessingException 
	 * @throws AmqpException 
	 */
	public Competence createCompetence(Competence competence) throws CompetenceExistanteException, AmqpException, JsonProcessingException {
		LOGGER.info("SERVICE: Appel[createCompetence] - {}",competence);

		competence.setAkCompetence("ak_" + competence.getNom());

		competence.setAkCompetence(enleverCharSpeciaux(competence.getAkCompetence()));

		if(competenceRepository.findOneByAkCompetence(competence.getAkCompetence()).isPresent()) {
			throw new CompetenceExistanteException();
		}
		this.competenceRepository.save(competence);
		Notification notification =  new Notification(Type.TOUS, null, "Nouvelle competence dans le referentiel : " + competence.getNom());
		this.rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", mapper.writeValueAsString(notification));
		return competence;
	}

	/**
	 * permet de modifier une compétence dans le repository correspondant
	 * 
	 * @param competence,la competence à modifier
	 * @return Competence, la compétence modifiée
	 * @throws NotFoundException 
	 */
	public Competence modifierCompetence(Competence competence) throws NotFoundException {
		LOGGER.info("SERVICE: Appel[modifierCompetence] - {}",competence);
		if(competenceRepository.findOneByAkCompetence(competence.getAkCompetence()) == null) {
			throw new NotFoundException("Competence non existant");
		}
		this.competenceRepository.save(competence);
		return competence;
	}

	/**
	 * permet de creer un tag dans le repository correspondant
	 * 
	 * @param tag,
	 *            le tag à creer
	 * @return Tag, le tag créé
	 * @throws TagExistantException 
	 */
	public Tag createTag(Tag tag) throws TagExistantException {
		LOGGER.info("SERVICE: Appel[createTag] - {}", tag);
		tag.setAkTag("ak_" + tag.getNom());

		tag.setAkTag(enleverCharSpeciaux(tag.getAkTag()));

		if(tagRepository.findOneByAkTag(tag.getAkTag()) != null) {
			throw new TagExistantException();
		}
		this.tagRepository.save(tag);
		return tag;
	}

	/**
	 * permet de creer un tag dans le repository correspondant
	 * 
	 * @param tag,
	 *            le tag à modifier
	 * @return Tag, le tag modifié
	 * @throws NotFoundException 
	 */
	public Tag modifierTag(Tag tag) throws NotFoundException {
		LOGGER.info("SERVICE: Appel[modifierTag] - {}", tag);
		if(tagRepository.findOneByAkTag(tag.getAkTag()) == null) {
			throw new NotFoundException("Tag non existant");
		}
		this.tagRepository.save(tag);
		return tag;
	}

	/**
	 * permet de supprimer un tag dans le repository correspondant
	 * 
	 * @param tag,
	 *            le tag à supprimer
	 * @return Tag, le tag supprimé
	 * @throws NotFoundException 
	 */
	public Tag supprimerTag(String tag) throws NotFoundException {
		LOGGER.info("SERVICE: Appel[supprimerTag] - {}", tag);
		Tag tagTest = tagRepository.findOneByAkTag(tag);
		if(tagTest == null) {
			throw new NotFoundException("Tag non existant");
		}
		this.tagRepository.delete(tagTest);
		return tagTest;
	}

	/**
	 * permet de chercher un tag dans le repository correspondant
	 * 
	 * @param tag,
	 *            le tag a chercher
	 * @return Tag, le tag correspondant
	 */
	public Tag findByAkTag(String tag) {
		LOGGER.info("SERVICE: Appel[findByAkTag] - {}", tag);
		return this.tagRepository.findOneByAkTag(tag);
	}

	/**
	 * permet de ressortir l'ensemble des collaborateurs présent dans le repository
	 * correspondant
	 * 
	 * @return List<Collaborateur>, la liste de tous les collaborateurs
	 */
	public List<Collaborateur> getAllCollaborateur() {
		LOGGER.info("SERVICE: Appel[getAllCollaborateur] - {}","Aucun paramètre");
		return (List<Collaborateur>) collaborateurRepository.findAll();
	}

	/**
	 * permet de ressortir l'ensemble des tags présent dans le repository
	 * correspondant
	 * 
	 * @return List<Tagr>, la liste de tous les tag
	 */
	public List<Tag> getAllTag() {
		LOGGER.info("SERVICE: Appel[getAllTag] - {}","Aucun paramètre");
		return (List<Tag>) tagRepository.findAll();
	}

	/**
	 * permet de ressortir l'ensemble des relations CompetentSur du repository
	 * correspondant
	 * 
	 * @return List<CompetentSur>, la liste des relations CompetentSur
	 */
	public List<CompetentSur> getAllRelation() {
		LOGGER.info("SERVICE: Appel[getAllRelation] - {}","Aucun paramètre");
		return (List<CompetentSur>) competentSurRepository.findAll();
	}

	/**
	 * permet d'ajouter une nouvelle relation CompetentSur dans le repository entre
	 * un collaborateur et une compétence
	 * 
	 * @param akCollaborateur,
	 *            l'ak du collaborateur qui sera le noeud de départ de la relation
	 * @param competentSurJson,
	 *            l'objet crée à partir du JSON permettant de connaître le noeud
	 *            d'arrivée et les infos sur les propriété de la relation
	 * @return CompetentSur, la relation CompetentSur ajouté
	 * @throws NotFoundException 
	 * @throws JsonProcessingException 
	 * @throws AmqpException 
	 */
	public CompetentSur ajouterCompetentSur(String akCollaborateur, CompetentSurJSON competentSurJson) throws NotFoundException, JsonProcessingException {
		LOGGER.info("SERVICE: Appel[ajouterCompetentSur] - {}, {}", akCollaborateur, competentSurJson);
		Optional<Collaborateur> collab = collaborateurRepository.findOneByAkCollaborateur(akCollaborateur);

		if (!collab.isPresent()) {
			Collaborateur c = new Collaborateur(akCollaborateur);
			collab = Optional.ofNullable(c);
			collaborateurRepository.save(collab.get());
		}

		Optional<Competence> competence = competenceRepository.findOneByAkCompetence(competentSurJson.getAkCompetence());

		if (!competence.isPresent()) {
			throw new NotFoundException(competentSurJson.getAkCompetence() + " : cette compétence est inconnue !");
		}

		LocalDateTime heureCourante = LocalDateTime.now();
		boolean nouvelleEvaluation = false;
		
		//Vérifie que la séniorité n'existe pas déjà et que l'évaluation est supérieure à zéro
		if(!senioriteSurRepository.existByAkCollaborateurAndAkCompetence(akCollaborateur, competentSurJson.getAkCompetence()) && competentSurJson.getNotation() > 0 ) {
			nouvelleEvaluation = true;
			SenioriteSur senioriteSur = new SenioriteSur(collab.get(), competence.get(), heureCourante);
			if(competentSurJson.getDateDebut() != null) {
				senioriteSur.setDateDebut(competentSurJson.getDateDebut());
			}
			senioriteSurRepository.save(senioriteSur);
		}

		CompetentSur competentSur = new CompetentSur();
		competentSur.setCollaborateur(collab.get());
		competentSur.setCompetence(competence.get());
		competentSur.setNotation(competentSurJson.getNotation());
		competentSur.setDate(heureCourante);

		this.competentSurRepository.save(competentSur);
		if(nouvelleEvaluation) {
			String message = mapper.writeValueAsString(new Action(collab.get().getAkCollaborateur(), "NOUVELLE_EVALUATION"));
			
			rabbitTemplate.convertAndSend(topicExchange.getName(), this.COMPETENCE_ACTION_QUEUE, message);
			
			LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.COMPETENCE_ACTION_QUEUE, message);
		}

		ObjectifNouvelleEvaluation ObjectifNouvelleEvaluation = new ObjectifNouvelleEvaluation(competence.get().getAkCompetence(),akCollaborateur);
		String message = mapper.writeValueAsString(new CalculObjectifsJSON("NOUVELLE_EVALUATION",mapper.writeValueAsString(ObjectifNouvelleEvaluation)));
		rabbitTemplate.convertAndSend(topicExchange.getName(), this.COMPETENCE_OBJECTIF_QUEUE, message);
		LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.COMPETENCE_OBJECTIF_QUEUE, message);

		return competentSur;
	}

	/**
	 * permet de retrouver l'ensemble des compétences qui sont liés par une relation
	 * à un collaborateur
	 * 
	 * @param akCollaborateur,
	 *            l'ak du collaborateur pour lesquels on recherche ses compétences
	 * @return List<CompetencesParCollaborateurJSON>, la liste des compétences liés à ce collaborateur
	 */
	public List<CompetencesParCollaborateurJSON> competencesUnCollaborateur(String akCollaborateur) {
		//Cache misère pour le front, probleme d'ak non unique du à la version de la bdd
		if(this.collaborateurRepository.findOneByAkCollaborateur(akCollaborateur) == null) {
			Collaborateur collaborateur = new Collaborateur();
			collaborateur.setAkCollaborateur(akCollaborateur);
			this.collaborateurRepository.save(collaborateur);
			LOGGER.info("SERVICE: Appel[competencesUnCollaborateur] - {creation d'un nouveau collaborateur}", akCollaborateur);
		}
		LOGGER.info("SERVICE: Appel[competencesUnCollaborateur] - {}", akCollaborateur);
		return 
				this.competentSurRepository
				.findByAkCollaborateur(akCollaborateur)
				.collect(Collectors.groupingBy(CompetentSur::getCompetence))
				.entrySet()
				.stream()
				.map(p ->  
				new CompetencesParCollaborateurJSON(p.getValue()
						.stream()
						.sorted()
						.findFirst()
						.get()
						)
						)
				.sorted((p1,p2) -> p1.getNom().compareToIgnoreCase(p2.getNom()))
				.filter(comp -> comp.getNotation() > 0) // Filtre par défaut si l'évaluation est supérieure à zéro
				.collect(Collectors.toList());
	}

	/**
	 * Recherche de collaborateurs ayant une competence
	 * 
	 * @param akCompetence
	 * @return Une liste de collaborateur ayant cette competence
	 */
	public List<Collaborateur> rechercherParCompetence(String akCompetence) {
		LOGGER.info("SERVICE: Appel[rechercherParCompetence] - {}", akCompetence);
		return this.collaborateurRepository.rechercherParCompetence(akCompetence);
	}

	/**
	 * Permet de connaitre les competences ayant pour prefix akCompetence
	 * 
	 * @param akCompetence
	 * @return liste de competences correspondantes, trier par l'orde alphabétique du nom de la compétence
	 */
	public List<Competence> findLikeCompetence(String akCompetence, int limit) {
		LOGGER.info("SERVICE: Appel[findLikeCompetence] - {}", akCompetence);
		akCompetence = accentToRegex(akCompetence);


		List<Competence> res = this.competenceRepository.findLikeCompetence(akCompetence + ".*");

		Collections.sort(res);

		if(res.size() > limit) {
			res = res.subList(0, limit);
		}

		return res;
	}

	/**
	 * Permet de connaitre la séniorite d'un collaborateur sur une competence
	 * @param akCompetence
	 * @param akCollaborateur
	 * @return le nombre de semaines de séniorité d'un collaborateur sur une competence
	 * @throws NotFoundException 
	 */
	public Long senioriteSemaine(String akCollaborateur, String akCompetence) throws NotFoundException {
		LOGGER.info("SERVICE: Appel[senioriteSemaine] - {}", akCompetence);
		List<SenioriteSur> listSenioriteSur = senioriteSurRepository.findByAkCollaborateurAndAkCompetence(akCollaborateur, akCompetence);
		Long resultat;
		if(!listSenioriteSur.isEmpty()) {
			LocalDateTime heureCourante = LocalDateTime.now();
			SenioriteSur senioriteSur = listSenioriteSur.get(0);
			LocalDateTime dateSeniorite = senioriteSur.getDateDebut();
			resultat = ChronoUnit.WEEKS.between(dateSeniorite,heureCourante);
		}else {
			throw new NotFoundException("Ce Collaborateur ne possède pas de séniorité sur cette compétence.");
		}
		return resultat;
	}

	/**
	 * Permet de parser le texte pour prendre en compte les accents dans une requete
	 * @param text
	 * @return le texte parsé
	 */
	private String accentToRegex(String text)
	{
		text=text.toLowerCase();
		text=text.replaceAll("["+ACCENT_STRINGS1+"]", "["+ACCENT_STRINGS1+"]");
		text=text.replaceAll("["+ACCENT_STRINGS2+"]", "["+ACCENT_STRINGS2+"]");
		text=text.replaceAll("["+ACCENT_STRINGS3+"]", "["+ACCENT_STRINGS3+"]");
		text=text.replaceAll("["+ACCENT_STRINGS4+"]", "["+ACCENT_STRINGS4+"]");
		text=text.replaceAll("["+ACCENT_STRINGS5+"]", "["+ACCENT_STRINGS5+"]");
		text=text.replaceAll("["+ACCENT_STRINGS6+"]", "["+ACCENT_STRINGS6+"]");
		text=text.replaceAll("["+ACCENT_STRINGS7+"]", "["+ACCENT_STRINGS7+"]");
		text=text.replaceAll("["+ACCENT_STRINGS8+"]", "["+ACCENT_STRINGS8+"]");
		return text;
	}

	/**
	 * permet de trouver tous les tags correspondant à un mot
	 * 
	 * @param nom, le mot recherchée
	 * @return List<Tag> la liste des tags correspondants au mot
	 */
	public List<Tag> tagsParNom(String nom, int limit){
		LOGGER.info("SERVICE: Appel[tagsParNom] - {}", nom);
		List<Tag> res = this.tagRepository.findByNom(".*"+accentToRegex(nom)+ ".*");

		Collections.sort(res);

		if(res.size() > limit) {
			res = res.subList(0, limit);
		}

		return res;
	}

	/**
	 * Permet de lier un tag et une compétence
	 * 
	 * @param akTag La cle du tag
	 * @param tagueSurJson les infos necessaires à la création de la relation
	 * @return la relation crée
	 * @throws NotFoundException
	 */
	public TagueSur ajouterTagueSur(String akCompetence, TagueSurJSON tagueSurJson) throws NotFoundException {
		LOGGER.info("SERVICE: Appel[ajouterTagueSur] - {}, {}", akCompetence, tagueSurJson );
		Tag tag = tagRepository.findOneByAkTag(tagueSurJson.getAkTag());
		if (tag == null) {
			throw new NotFoundException(tagueSurJson.getAkTag() + " : ce tag est inconnue !");
		}

		Optional<Competence> competence = competenceRepository.findOneByAkCompetence(akCompetence);

		if (!competence.isPresent()) {
			throw new NotFoundException(akCompetence + " : cette compétence est inconnue !");
		}

		TagueSur tagueSur = new TagueSur();
		tagueSur.setTag(tag);
		tagueSur.setCompetence(competence.get());

		tagueSurRepository.save(tagueSur);

		return tagueSur;
	}

	/**
	 * Permet de rechercher les tags par un akCompetence
	 * 
	 * @param akCompetence la clé de la competence
	 * @return La liste des tags liés a une compétence
	 */
	public List<Tag> findByAkCompetence(String akCompetence) {
		LOGGER.info("SERVICE: Appel[findByAkCompetence] - {}", akCompetence );
		return this.tagRepository.findByAkCompetence(akCompetence);
	}

	/**
	 * Permet de rechercher les relations par tag
	 * 
	 * @param tag la clé du tag 
	 * @return La liste des compétences liés a un tag
	 */
	public List<Competence> findCompetencesByAkTag(String akTag) {
		LOGGER.info("SERVICE: Appel[findCompetencesByAkTag] - {}", akTag);
		return this.competenceRepository.findByAkTag(akTag);
	}

	/**
	 * Recherche de collaborateurs ayec une ou plusieurs competences
	 * 
	 * @param competences, la liste des ak des competences recherchée
	 * @return Une liste de collaborateur possedant une relation vers toutes ces competences
	 */
	public List<Collaborateur> rechercherParCompetences(List<String> competences) {
		LOGGER.info("SERVICE: Appel[rechercherParCompetences] - {}", competences);
		return collaborateurRepository.rechercherParCompetences(competences);
	}

	/**
	 * Permet de supprimer une relation Tag / competence
	 * 
	 * @param akTag la cle du tag
	 * @param akCompetence la cle de la competence
	 * @return Le tag supprimé
	 * @throws NotFoundException
	 */
	public TagueSur supprimerRelationTag(String akTag, String akCompetence) throws NotFoundException {
		LOGGER.info("SERVICE: Appel[supprimerRelationTag] - {}, {}", akTag, akCompetence);
		TagueSur tagueSur = tagueSurRepository.findByAkTagAndByAkCompetence(akTag, akCompetence);
		if(tagueSur == null) {
			throw new NotFoundException("cette relation n'existe pas");
		}

		tagueSurRepository.delete(tagueSur);
		return tagueSur;
	}

	/**
	 * Permet de supprimer une competence
	 * 
	 * @param akCompetence, l'identifiant de la competence
	 * @return la competence supprimée
	 * @throws NotFoundException
	 */
	public Competence deleteByAkCompetence(String akCompetence) throws NotFoundException {
		LOGGER.info("SERVICE: Appel[deleteByAkCompetence] - {}", akCompetence);
		Optional<Competence> competence = competenceRepository.findOneByAkCompetence(akCompetence);
		if( !competence.isPresent()) {
			throw new NotFoundException("Competence non existante");
		}

		competenceRepository.delete(competence.get());
		return competence.get();
	}

	/**
	 * Permet de récupérer le nombre de collaborateur par notation sur une compétence
	 * 
	 * @param akCompetence, l'identifiant de la competence
	 * @return Map<String, Integer>, retourne une Hashmap des notations et du nombre de collaborateurs notés sur la compétence avec cette notation
	 */
	public Map<String, Integer> getStatistiqueNiveauxCompetences(String akCompetence){
		return this.competentSurRepository
				.findByAKCompetence(akCompetence)
				.collect(Collectors.groupingBy(CompetentSur::getCollaborateur))
				.entrySet()
				.stream()
				.map(p -> { 
					return p.getValue()
							.stream()
							.sorted()
							.findFirst()
							.get();}
						)
				.collect(Collectors.groupingBy(CompetentSur::getNotationString, Collectors.summingInt(x -> 1)));


	}
	
	/**
	 * Permet de récupérer le nombre de collaborateur par seniorite sur une compétence
	 * 
	 * @param akCompetence, l'identifiant de la competence
	 * @return Map<String, Integer>, retourne une Hashmap des notations et du nombre de collaborateurs notés sur la compétence avec cette notation
	 */
	public Map<String, Integer> getStatistiqueSenioriteCompetences(String akCompetence){
		return this.senioriteSurRepository
				.findByAkCompetence(akCompetence)
				.distinct() // considéré égal seulement si le collaborateur et la competence sont les mêmes
				.collect(Collectors.groupingBy(SenioriteSur::getSeniorite, Collectors.summingInt(x -> 1)));
	}

	/**
	 * Permet d'actualiser les statistiques de son service et d'envoyer ses calculs au service statistique dans la queue STATISTIQUE_ENREGISTRER_QUEUE
	 * 
	 * @param akCompetence, l'identifiant de la competence
	 * @throws JsonProcessingException 
	 */
	public void actualiserStatistique(String AkStatistique) throws JsonProcessingException{
		switch(AkStatistique) {
		case "akNiveauxCompetences" :
			Statistique stat = new Statistique("Niveaux sur les compétences", AkStatistique, new ArrayList<DonneeStatistique>());
			this.getAllCompetence().stream()
			.map( comp -> new DonneeStatistique(comp.getNom(), comp.getAkCompetence()))
			.forEach( donneeStatistique -> {
				donneeStatistique.setDonnees(this.getStatistiqueNiveauxCompetences(donneeStatistique.getAkDonnee()));
				stat.getDonnees().add(donneeStatistique);
			});
			String statString = mapper.writeValueAsString(stat);
			rabbitTemplate.convertAndSend(topicExchange.getName(), this.STATISTIQUE_ENREGISTRER_QUEUE, statString);
			LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.STATISTIQUE_ENREGISTRER_QUEUE, statString);
			break;
			
		case "akSenioriteCompetences" :
			Statistique stat2 = new Statistique("Proportion Seniorite par competence", AkStatistique, new ArrayList<DonneeStatistique>());
			this.getAllCompetence().stream()
			.map( comp -> new DonneeStatistique(comp.getNom(), comp.getAkCompetence()))
			.forEach( donneeStatistique -> {
				donneeStatistique.setDonnees(this.getStatistiqueSenioriteCompetences(donneeStatistique.getAkDonnee()));
				stat2.getDonnees().add(donneeStatistique);
			});
			String stat2String = mapper.writeValueAsString(stat2);
			rabbitTemplate.convertAndSend(topicExchange.getName(), this.STATISTIQUE_ENREGISTRER_QUEUE, stat2String);
			LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.STATISTIQUE_ENREGISTRER_QUEUE, stat2String);
			break;
		default : 
			LOGGER.info("WARN: TraitementStatistiques[receiveActionSucces] - {} ", "Action non traitée ", AkStatistique);
			break;

		}
	}

	/**
	 * permet de ressortir l'ensemble des objectifs présent dans le repository
	 * 
	 * @return List<Objectif>, la liste des tous les objectifs
	 */
	public List<Objectif> getAllObjectif() {
		LOGGER.info("SERVICE: Appel[getAllObjectif] - {}","Aucun paramètre");
		return (List<Objectif>) this.objectifRepository.findAll();
	}

	/**
	 * permet d'ajouter un objectif et de nouvelles relations DoitEtreEvalue dans le repository entre
	 * un objectif et une compétence
	 * 
	 * @param objectifJSON,
	 *            l'objectifJSON a créer
	 * @return Objectif, l'objectif créée
	 * @throws Exception 
	 */
	public Objectif createObjectif(ObjectifJSON objectifJSON) throws Exception {

		if(this.objectifRepository.findOneByAkObjectif(enleverCharSpeciaux("ak_" + objectifJSON.getNom())).isPresent()) {
			throw new ObjectifExistantException();
		}

		//Vérification des données envoyées, si la notation est : 0<n<6 et que la compétence existe
		if(!objectifJSON.getCompetences().entrySet().stream()
				.allMatch(obj -> (
						obj.getValue() > 0 
						&& obj.getValue() < 6 
						&& competenceRepository.findOneByAkCompetence(obj.getKey()).isPresent())
						)
				) {
			throw new ObjectifJSONException();
		}

		Objectif objectif = new Objectif();
		objectif.setAkObjectif("ak_" + objectifJSON.getNom());

		objectif.setAkObjectif(enleverCharSpeciaux(objectif.getAkObjectif()));

		objectif.setNom(objectifJSON.getNom());
		objectif.setDescription(objectifJSON.getDescription());

		final String imgPetite;
		if(objectifJSON.getBadge()!= null && objectifJSON.getBadge() != "") {
			BufferedImage original = GestionImage.base64StringToImg(objectifJSON.getBadge());
			float ratio = (float) original.getHeight() / (float) original.getWidth();
			int type;
			if ( objectifJSON.getType().equals("jpg") || objectifJSON.getType().equals("jpeg")) {
				type = BufferedImage.TYPE_INT_RGB;
			}else {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			BufferedImage petite = GestionImage.scale(original, 100, (int) (100 * ratio), type);
			imgPetite = GestionImage.imgToBase64String(petite, objectifJSON.getType());
		}else {
			imgPetite = null;
		}


		objectif.setBadge(imgPetite);

		this.objectifRepository.save(objectif);
		objectifJSON.getCompetences()
		.forEach(
				(akComp, notation)->{
					Optional<Competence> competence = competenceRepository.findOneByAkCompetence(akComp);
					DoitEtreEvalue doitEtreEvalue = new DoitEtreEvalue();
					doitEtreEvalue.setNotation(notation);
					doitEtreEvalue.setCompetence(competence.get());
					doitEtreEvalue.setObjectif(objectif);
					this.doitEtreEvalueRepository.save(doitEtreEvalue);
				});


		ObjectifNouveau objectifNouveau = new ObjectifNouveau(objectif.getAkObjectif(), objectifJSON.getCompetences());
		String message = mapper.writeValueAsString(new CalculObjectifsJSON("NOUVEAU_OBJECTIF",mapper.writeValueAsString(objectifNouveau)));
		rabbitTemplate.convertAndSend(topicExchange.getName(), this.COMPETENCE_OBJECTIF_QUEUE, message);
		LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.COMPETENCE_OBJECTIF_QUEUE, message);

		return objectif;
	}

	/**
	 * permet de ressortir le détail d'un objectif
	 * 
	 * @param akObjectif, l'identifiant de l'objectif
	 * @return Objectif, le detail d'un objectif
	 * @throws NotFoundException 
	 */
	public Objectif findByAkObjectif(String akObjectif) throws NotFoundException{
		Optional<Objectif> objectif = this.objectifRepository.findOneByAkObjectif(akObjectif);
		if(!objectif.isPresent()) {
			throw new NotFoundException("Objectif non existant");
		}
		return this.objectifRepository.findOneByAkObjectif(akObjectif).get();
	}


	/**
	 * permet de ressortir l'ensemble des relations objectif-Compétences lié à un akObjectif
	 * 
	 * @param akObjectif, l'identifiant de l'objectif
	 * @return List<ObjectifDoitEtreEvalue>, la liste des tous les relations objectif-Compétences simplifié en format ObjectifDoitEtreEvalue
	 * @throws NotFoundException 
	 */
	public List<ObjectifDoitEtreEvalue> findByAkObjectifRelation(String akObjectif) throws NotFoundException{
		if(!this.objectifRepository.findOneByAkObjectif(akObjectif).isPresent()) {
			throw new NotFoundException("Objectif non existant");
		}
		return this.doitEtreEvalueRepository.findByAkObjectif(akObjectif);
	}

	/**
	 * permet de ressortir l'ensemble des relations objectif-Compétences lié à un akObjectif
	 * 
	 * @param akCollaborateur, l'identifiant du collaborateur
	 * @param List<String>, la Liste des akObjectifs à attribuer au collaborateur
	 * @return List<String>, la liste des attribution effectué
	 * @throws NotFoundException 
	 */
	@Transactional
	public List<String> ajoutObjectifsCollaborateur(String akCollaborateur, List<String> listModeleAttribution) throws NotFoundException{
		Optional<Collaborateur> collabTest = collaborateurRepository.findOneByAkCollaborateur(akCollaborateur);
		if (!collabTest.isPresent()) {
			collabTest = Optional.ofNullable(new Collaborateur(akCollaborateur));
			collaborateurRepository.save(collabTest.get());
		}
		final Collaborateur collab = collabTest.get();

		if(listModeleAttribution.stream().filter(akObjectif -> { 
			return !this.objectifRepository.findOneByAkObjectif(akObjectif).isPresent();
		}).count()>0) {
			throw new NotFoundException("Objectif non existant");
		}

		listModeleAttribution.stream().forEach(akObjectif -> {
			if(!this.aAtteindreRepository.findOneByAkCollaborateurAndByAkObjectif(akCollaborateur, akObjectif).isPresent()){
				AAtteindre aAtteindre = new AAtteindre();
				aAtteindre.setCollaborateur(collab);
				aAtteindre.setObjectif(this.objectifRepository.findOneByAkObjectif(akObjectif).get());
				this.aAtteindreRepository.save(aAtteindre);
			}
		});

		return listModeleAttribution;
	}

	/**
	 * permet de récupérer la liste ObjectifRetourJSON des objectifs atteint ou non par le collaborateur
	 * 
	 * @param akCollaborateur, l'identifiant du collaborateur
	 * @param valide, boolean si l'objectif est valide ou non
	 * @return List<ObjectifRetourJSON>, la liste des objectifs validé ou nonpar le collaborateur
	 */
	public List<ObjectifRetourJSON> findObjectifsByAkCollaborateurValide(String akCollaborateur, boolean valide){
		LOGGER.info("SERVICE: Appel[findObjectifsByAkCollaborateurValide] - {}", akCollaborateur);
		return this.aAtteindreRepository.findByAkCollaborateur(akCollaborateur, valide);
	}

	/**
	 * permet de récupérer la liste ObjectifRetourJSON des objectifs atteint et non par le collaborateur
	 * 
	 * @param akCollaborateur, l'identifiant du collaborateur
	 * @return List<ObjectifRetourJSON>, la liste des objectifs validé et non par le collaborateur
	 */
	public List<ObjectifRetourJSON> findObjectifsByAkCollaborateur(String akCollaborateur){
		LOGGER.info("SERVICE: Appel[findObjectifsByAkCollaborateur] - {}", akCollaborateur);
		return this.aAtteindreRepository.findByAkCollaborateur(akCollaborateur);
	}

	/**
	 * Permet de transformer les ak contenant des characteres spéciaux
	 * 
	 * @param text La chaine de characteres en entrée
	 * @return text la chaine de charactere transformée
	 */
	public String enleverCharSpeciaux(String text) {

		// Pour enlever accents
		final String ACCENT_STRINGS1 = "[aàáâãäå]";
		final String ACCENT_STRINGS2 = "[cç]";
		final String ACCENT_STRINGS3 = "[eèéêëẽ]";
		final String ACCENT_STRINGS4 = "[iìíîïĩ]";
		final String ACCENT_STRINGS5 = "[oðòóôõöø]";
		final String ACCENT_STRINGS6 = "[nñ]";
		final String ACCENT_STRINGS7 = "[uùúûü]";
		final String ACCENT_STRINGS8 = "[yýÿ]";
		final String ACCENT_STRINGS9 = "[^a-z&&[^0-9]&&[^@.]]";


		text=text.toLowerCase();
		text=text.replaceAll(ACCENT_STRINGS1, "a");
		text=text.replaceAll(ACCENT_STRINGS2, "c");
		text=text.replaceAll(ACCENT_STRINGS3, "e");
		text=text.replaceAll(ACCENT_STRINGS4, "i");
		text=text.replaceAll(ACCENT_STRINGS5, "o");
		text=text.replaceAll(ACCENT_STRINGS6, "n");
		text=text.replaceAll(ACCENT_STRINGS7, "u");
		text=text.replaceAll(ACCENT_STRINGS8, "y");
		text=text.replaceAll(ACCENT_STRINGS9, "_");

		return text;
	}


	/**
	 * Permet de savoir si un collaborateur est intéressé par une compétence 
	 * @param akCompetence
	 * @param akCollaborateur
	 * @return resultat, un integer [-1, 0, 1], retourne 0 par default
	 */
	public int findInteretCompetence(String akCollaborateur, String akCompetence) {

		LOGGER.info("SERVICE: Appel[findInteretCompetence] - {}", akCompetence);

		List<MentionInteretPour> listMentionInteretPour = mentionInteretPourRepository.findAkCollaborateurAndAkCompetence(akCollaborateur, akCompetence);

		//Par défaut le collaborateur n'a pas d'intérêt pour la compétence.
		int resultat = 0;

		if(!listMentionInteretPour.isEmpty()) {
			resultat=listMentionInteretPour.get(0).getIsMentionInteret();
		}

		return resultat;
	}

	/**
	 * Permet de savoir si un collaborateur souhaite se former ou progresser sur une compétence 
	 * @param akCompetence
	 * @param akCollaborateur
	 * @return un booléen pour savoir si le collaborateur souhaite se former
	 */
	public int findApprendreCompetence(String akCollaborateur, String akCompetence) {

		LOGGER.info("SERVICE: Appel[findApprendreCompetence] - {}", akCompetence);

		List<MentionApprendreSur> listMentionApprendreSur = mentionApprendreSurRepository.findAkCollaborateurAndAkCompetence(akCollaborateur, akCompetence);

		//Par défaut le collaborateur ne souhaite pas apprendre la compétence.
		int resultat = 0;
		
		if(!listMentionApprendreSur.isEmpty()) {
			resultat=listMentionApprendreSur.get(0).getIsMentionApprendre();
		}

		return resultat;
	}

	/**
	 * Permet de modifier le souhait de formation d'un collaborateur sur une compétence
	 * @param akCollaborateur
	 * @param akCompetence
	 * @param mention
	 * @return
	 * @throws DefautException 
	 */
	public MentionApprendreSur modifierApprendreCompetence(String akCollaborateur, String akCompetence, MentionApprendreSur mention) throws DefautException {
		LOGGER.info("SERVICE: Appel[modifierApprendreCompetence] - {}", akCompetence);
		List<MentionApprendreSur> listMentionApprendreSur = mentionApprendreSurRepository.findAkCollaborateurAndAkCompetence(akCollaborateur, akCompetence);
		MentionApprendreSur m = null;

		if(!listMentionApprendreSur.isEmpty()) {
			m = listMentionApprendreSur.get(0);
			m.setIsMentionApprendre(mention.getIsMentionApprendre());
			return this.mentionApprendreSurRepository.save(m);
		} else {
			return this.creerApprendreCompetence(akCollaborateur, akCompetence, mention);
		}

	}

	/**
	 * Permet de définir si un collaborateur souhaite se former ou progresser sur une compétence 
	 * @param akCompetence
	 * @param akCollaborateur
	 * @return un booléen pour savoir si le collaborateur souhaite se former
	 * @throws DefautException 
	 */
	public MentionApprendreSur creerApprendreCompetence(String akCollaborateur, String akCompetence, MentionApprendreSur mention) throws DefautException {

		LOGGER.info("SERVICE: Appel[creerApprendreCompetence] - {}", akCompetence);

		// pas de correspondance entre l'objet et le chemin
		if( !akCollaborateur.equals(mention.getCollaborateur().getAkCollaborateur()) || !akCompetence.equals(mention.getCompetence().getAkCompetence())) {
			throw new DefautException();
		}

		List<MentionApprendreSur> listMentionApprendreSur = mentionApprendreSurRepository.findAkCollaborateurAndAkCompetence(akCollaborateur, akCompetence);

		if(!listMentionApprendreSur.isEmpty()) {
			return this.modifierApprendreCompetence(akCollaborateur, akCompetence, mention);
		} else {
			Collaborateur coll = collaborateurRepository.findOneByAkCollaborateur(akCollaborateur).orElseThrow(() ->new NotFoundException("Utilisateur inexistante"));
			Competence comp = competenceRepository.findOneByAkCompetence(akCompetence).orElseThrow(() ->new NotFoundException("Competence inexistante"));

			MentionApprendreSur m=new MentionApprendreSur();

			m.setCollaborateur(coll);
			m.setCompetence(comp);
			m.setIsMentionApprendre(mention.getIsMentionApprendre());

			return this.mentionApprendreSurRepository.save(m);
		}


	}

	/**
	 * Permet de modifier la valeur de la relation si le collaborateur est intéressé par une compétence 
	 * @param akCompetence
	 * @param akCollaborateur
	 * @return un booléen pour savoir si le collaborateur souhaite se former
	 * @throws DefautException 
	 */
	public MentionInteretPour modifierInteretCompetence(String akCollaborateur, String akCompetence, MentionInteretPour isMention) throws DefautException {
		LOGGER.info("SERVICE: Appel[modifierInteretCompetence] - {}", akCompetence);

		List<MentionInteretPour> listMentionInteretPour = mentionInteretPourRepository.findAkCollaborateurAndAkCompetence(akCollaborateur, akCompetence);
		MentionInteretPour m = null;
		if(!listMentionInteretPour.isEmpty()) {
			m = listMentionInteretPour.get(0);
			m.setIsMentionInteret(isMention.getIsMentionInteret());
			return this.mentionInteretPourRepository.save(m);
		} else {
			return this.creerInteretCompetence(akCollaborateur, akCompetence, isMention);
		}
	}

	/**
	 * Permet de créer la relation si un collaborateur est intéressé par une compétence 
	 * @param akCompetence
	 * @param akCollaborateur
	 * @return un booléen pour savoir si le collaborateur souhaite se former
	 * @throws DefautException 
	 */
	public MentionInteretPour creerInteretCompetence(String akCollaborateur, String akCompetence, MentionInteretPour mention) throws DefautException {
		LOGGER.info("SERVICE: Appel[findInteretCompetence] - {}", akCompetence);

		// pas de correspondance entre l'objet et le chemin
		if( !akCollaborateur.equals(mention.getCollaborateur().getAkCollaborateur()) || !akCompetence.equals(mention.getCompetence().getAkCompetence())) {
			throw new DefautException();
		}

		Collaborateur coll = collaborateurRepository.findOneByAkCollaborateur(akCollaborateur).orElseThrow(() ->new NotFoundException("Utilisateur inexistante"));
		Competence comp = competenceRepository.findOneByAkCompetence(akCompetence).orElseThrow(() ->new NotFoundException("Competence inexistante"));

		List<MentionInteretPour> listMentionInteretSur = mentionInteretPourRepository.findAkCollaborateurAndAkCompetence(akCollaborateur, akCompetence);

		if(!listMentionInteretSur.isEmpty()) {
			return this.modifierInteretCompetence(akCollaborateur, akCompetence, mention);
		} else {
			MentionInteretPour m = new MentionInteretPour();

			m.setCollaborateur(coll);
			m.setCompetence(comp);
			m.setIsMentionInteret(mention.getIsMentionInteret());

			return this.mentionInteretPourRepository.save(m);
		}


	}
}

