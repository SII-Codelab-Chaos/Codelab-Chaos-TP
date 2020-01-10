package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;

/**
 *  * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds Collaborateur
 * 
 */
public interface CollaborateurRepository extends PagingAndSortingRepository<Collaborateur,Long> {

	/**
	 * permet de rechercher un collaborateur par son AK
	 * @param akCollaborateur, l'ak du collaborateur recherché
	 * @return Collaborateur, le collaborateur trouvé
	 */
	
	@Query("MATCH (n:Collaborateur{ akCollaborateur: {akCollaborateur} }) RETURN n LIMIT 1")
	Optional<Collaborateur> findOneByAkCollaborateur(@Param("akCollaborateur") String akCollaborateur);

	/**
	 * Recherche de collaborateurs avec une competence
	 * @param akCompetence
	 * @return Une liste de collaborateur ayant cette competence
	 */
	@Query(value = "MATCH (Competence { akCompetence: {akCompetence} })-[COMPETENT_SUR]-(b:Collaborateur) RETURN b")
	List<Collaborateur> rechercherParCompetence(@Param("akCompetence") String akCompetence);


	/**
	 * Recherche de collaborateurs avec une ou plusieurs competences
	 * @param competences, la liste des ak des competences recherchée
	 * @return Une liste de collaborateur possedant une relation vers toutes ces competences
	 */
	@Query(value = "MATCH (c:Collaborateur)" +
			       "WHERE ALL (variable in {competences}" +
			                          "WHERE (c)-[:COMPETENT_SUR]-(:Competence{akCompetence:variable}))" +
			       "RETURN c")
	List<Collaborateur> rechercherParCompetences(@Param("competences") List<String> competences);
	
	/**
	 * permet de récuperer tous les collaborateurs possédant une évaluation sur tous la collection de compétences,
	 * et pour chacun, retourner toutes les relations de ces competences, et uniquement celle-ci
	 * @param competences
	 * @return
	 */
	@Query("MATCH (collaborateur:Collaborateur)-[relation:COMPETENT_SUR]-(competence:Competence) " + 
			"WHERE competence.akCompetence IN {competences} AND ALL (variable in {competences} WHERE (collaborateur)-[:COMPETENT_SUR]-(:Competence{akCompetence:variable}))  " + 
			"WITH collaborateur, competence, collect(relation{.date, .notation}) as relations " + 
			"RETURN collaborateur{ .akCollaborateur , competences : collect(competence{.akCompetence, relations})}")
	Iterable<Map<String, Map<String, Object>>> rechercherParCompetencesToMap(@Param("competences") Collection<String> competences);
	
	/**
	 * permet de récuperer pour un collaborateur l'ensemble des relations COMPETENT_SUR des competences passée en paramètre
	 * @param competences
	 * @return
	 */
	@Query("MATCH (Collaborateur {akCollaborateur : {akCollaborateur} })-[relation:COMPETENT_SUR]-(competence:Competence) " + 
			"WHERE competence.akCompetence IN {competences} " + 
			"WITH competence, collect(relation{.date, .notation}) as relations " + 
			"RETURN competence{ .akCompetence , relations }")
	 Iterable<Map<String, Map<String, Object>>> findRelationByAkcollaborateurToMap(@Param("akCollaborateur") String akCollaborateur, @Param("competences") Collection<String> competences);
}
