package fr.sii.atlantique.fusiion.fusiion_gestion_competences.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes.Action;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes.SuccesQueue;

/**
 * 
 * Classe permettant de traiter la réalisation d'une action par un collaborateur au niveau des succes
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 */
@Component
public class TraitementActionSucces {
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	@Autowired
    private TopicExchange topicExchange;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TraitementActionSucces.class);
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	private final String GamingQueue = "Gaming.all";

    /**
     * Constructeur vide
     * 
     */
	public TraitementActionSucces() {
		super();
	}
	
	/**
	 * permet de traiter les différentes action réalisé par un collaborateur
	 *
	 * @throws JsonProcessingException 
	 */
	public void receiveActionSucces(Action action) throws JsonProcessingException {
		switch(action.getTypeAction()) {
			case "NOUVELLE_EVALUATION" :
				String message = mapper.writeValueAsString(new SuccesQueue(action.getAkCollaborateur(), "ak_evaluation_1", 1));
				rabbitTemplate.convertAndSend(topicExchange.getName(), this.GamingQueue, message);
				LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.GamingQueue, message);
				
				message = mapper.writeValueAsString(new SuccesQueue(action.getAkCollaborateur(), "ak_evaluation_10", 1));
				rabbitTemplate.convertAndSend(topicExchange.getName(), this.GamingQueue, message);
				LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.GamingQueue, message);
				
				message = mapper.writeValueAsString(new SuccesQueue(action.getAkCollaborateur(), "ak_evaluation_100", 1));
				rabbitTemplate.convertAndSend(topicExchange.getName(), this.GamingQueue, message);
				LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.GamingQueue, message);
				
				message = mapper.writeValueAsString(new SuccesQueue(action.getAkCollaborateur(), "ak_evaluation_500", 1));
				rabbitTemplate.convertAndSend(topicExchange.getName(), this.GamingQueue, message);
				LOGGER.info("RABBITMQ: Envoye[{}] - {} ", this.GamingQueue, message);
				
				break;
			default : 
				LOGGER.warn("TraitementActionSucces[receiveActionSucces] - {} , {} ", "Action non traitée ", action);
				break;
		}
	}
}
