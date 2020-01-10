package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.listener.Receiver;

/**
 * Configuration du receiver pour rabbitmq
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
@Configuration
public class ServiceCompetencesConfigReceive {

	@Autowired
	private TopicExchange topicExchange;
	
	@Autowired
	private FanoutExchange fanoutExchange;

	/**
	 * Constructeur avec parametres
	 * 
	 * @param collaborateurRepository
	 * @param rabbitTemplate
	 */
	public ServiceCompetencesConfigReceive() {
		super();
	}
	
	/**
	 * 
	 * @return le topic
	 */
	@Bean
	public TopicExchange eventExchange() {
		topicExchange = new TopicExchange("eventExchange");
		return topicExchange;
	}
	
	/**
	 * 
	 * @return le fanout exchange (envoi message a toutes les queues)
	 */
	@Bean
	public FanoutExchange eventFanoutExchange() {
		fanoutExchange = new FanoutExchange("eventFanoutExchange");
		return fanoutExchange;
	}
	
	/**
	 * 
	 * @return Une queue d'ecoute
	 */
	@Bean
	public Queue queue1() {
		return new Queue("Competence.action");
	}
	
	/**
	 * 
	 * @param queue1
	 * @param eventExchange
	 * @return Un binding pour une queue
	 */
	@Bean
	public Binding binding1(Queue queue1, TopicExchange eventExchange) {
		return BindingBuilder
				.bind(queue1)
				.to(eventExchange)
				.with("Competence.action");
	}
	
	/**
	 * 
	 * @return Une queue d'ecoute
	 */
	@Bean
	public Queue queue2() {
		return new Queue("Competence.statistique");
	}
	
	/**
	 * 
	 * @param queue2
	 * @param eventExchange
	 * @return Un binding pour une queue
	 */
	@Bean
	public Binding binding2(Queue queue2, TopicExchange eventExchange) {
		return BindingBuilder
				.bind(queue2)
				.to(eventExchange)
				.with("Competence.statistique");
	}
	
	/**
	 * 
	 * @return Une queue d'ecoute
	 */
	@Bean
	public Queue queue3() {
		return new Queue("Competence.objectif");
	}
	
	/**
	 * 
	 * @param queue2
	 * @param eventExchange
	 * @return Un binding pour une queue
	 */
	@Bean
	public Binding binding3(Queue queue3, TopicExchange eventExchange) {
		return BindingBuilder
				.bind(queue3)
				.to(eventExchange)
				.with("Competence.objectif");
	}
	
	/**
	 * 
	 * @return Le receiver
	 */
	@Bean
	public Receiver eventReceiver() {
		return new Receiver();
	}

}