package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;

/**
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds objectif
 * 
 */
public interface ObjectifRepository  extends PagingAndSortingRepository<Objectif, Long>{
	
	/**
	 * permet de rechercher un objectif par son ak
	 * @param akObjectif, l' ak de l'objectif recherché
	 * @return Objectif, l'objectif trouvé
	 */
	@Query("MATCH (n:Objectif{akObjectif : {akObjectif} }) RETURN n LIMIT 1")
	Optional<Objectif> findOneByAkObjectif(@Param("akObjectif") String akObjectif);
	
	/**
	 * permet de récupérer tous les objectifs correspondant à une compétence (relation DOIT_ETRE_EVALUE)
	 * @param akCompetence
	 * @return Iterable<Map<String, Map<String, Object>>>
	 */
	@Query("MATCH (objectif:Objectif)-[relation:DOIT_ETRE_EVALUE]-(competence:Competence) "
			+"WHERE (objectif)-[:DOIT_ETRE_EVALUE]-(:Competence{akCompetence: {akCompetence} }) "
			+ "WITH objectif, competence, relation.notation as notation " 
			+ "RETURN objectif{ .akObjectif , competences : collect(competence{.akCompetence, notation})}")
	Iterable<Map<String, Map<String, Object>>> findObjectifsByAkCompetenceToMap(@Param("akCompetence") String akCompetence);
	
}
