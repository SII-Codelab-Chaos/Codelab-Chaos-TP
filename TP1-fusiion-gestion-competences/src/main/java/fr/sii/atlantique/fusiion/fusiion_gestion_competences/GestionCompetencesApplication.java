package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
@EnableNeo4jRepositories
@EnableTransactionManagement
@SpringBootApplication
public class GestionCompetencesApplication 
{
	/**
	 * fonction principale permettant de lancer le service de gestion des compétences avec spring boot
	 */
	public static void main(String[] args) {
		SpringApplication.run(GestionCompetencesApplication.class, args);
	}

}
