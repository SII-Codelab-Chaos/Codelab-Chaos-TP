package fr.sii.atlantique.fusiion.fusiion_gestion_competences.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.CalculObjectifsJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.ActionStatistique;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes.Action;

/**
 * Receiver et traitement des messages recus
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public class Receiver {
	
    private final ObjectMapper mapper = new ObjectMapper();
    
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    
    @Autowired
    private TraitementActionSucces traitementActionSucces;
    
    @Autowired
    private TraitementStatistiques traitementStatistiques;
    
    @Autowired
    private TraitementObjectifs traitementObjectifs;

    /**
     * Constructeur avec parametres
     * 
     * @param profilRepository
     * @param rabbitTemplate
     * @param topicExchange
     */
	public Receiver() {
		super();
	}

	/**
	 * Fonction d'ecoute sur Competence.action
	 * 
	 * @param in
	 * @throws IOException 
	 */
	@RabbitListener(queues="Competence.action")
	public void receiveAllActions(String in) throws IOException {
		LOGGER.info("RABBITMQ: Recu[{}] - {} ", "Competence.action", in);
		this.traitementActionSucces.receiveActionSucces(mapper.readValue(in, Action.class));
	}
	

	/**
	 * Fonction d'ecoute sur Competence.statistique
	 * 
	 * @param in
	 * @throws IOException 
	 */
	@RabbitListener(queues="Competence.statistique")
	public void receiveAllStatistiques(String in) throws IOException {
		LOGGER.info("RABBITMQ: Recu[{}] - {} ", "Competence.statistique", in);
		this.traitementStatistiques.receiveAction(mapper.readValue(in, ActionStatistique.class));
	}
	
	/**
	 * Fonction d'ecoute sur Competence.objectif
	 * 
	 * @param in
	 * @throws IOException 
	 */
	@RabbitListener(queues="Competence.objectif")
	public void receiveAllObjectifs(String in) throws IOException {
		LOGGER.info("RABBITMQ: Recu[{}] - {} ", "Competence.objectif", in);
		this.traitementObjectifs.receiveActionObjectifs(this.mapper.readValue(in, CalculObjectifsJSON.class));
		
	}
	
	
}