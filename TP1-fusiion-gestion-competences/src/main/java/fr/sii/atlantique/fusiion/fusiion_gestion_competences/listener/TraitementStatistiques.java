package fr.sii.atlantique.fusiion.fusiion_gestion_competences.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.ActionStatistique;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.services.CompetenceService;

/**
 * 
 * Classe permettant de traiter la réalisation d'une action demandée par le service statistique
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 */
@Component
public class TraitementStatistiques {

	@Autowired
    private RabbitTemplate rabbitTemplate;
	@Autowired
    private TopicExchange topicExchange;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TraitementStatistiques.class);
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	private final String STATISTIQUE_ENREGISTRER_QUEUE = "Statistique.enregistrer";
	
	@Autowired
	private CompetenceService competenceService;
    /**
     * Constructeur vide
     * 
     */
	public TraitementStatistiques() {
		super();
	}
	
	/**
	 * permet de traiter les différentes action demandé par le service statistiques
	 *
	 * @throws JsonProcessingException 
	 */
	public void receiveAction(ActionStatistique action) throws JsonProcessingException {
		switch(action.getTypeAction()) {
			case "Actualiser" :
				competenceService.actualiserStatistique(action.getAkStatistique());
				break;
			default : 
				LOGGER.info("WARN: TraitementStatistiques[receiveActionSucces] - {} , {} ", "Action non traitée ", action);
				break;
		}
	}
}
