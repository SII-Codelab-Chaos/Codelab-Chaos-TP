package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.stream.Stream;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;

/**
 *  @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds de la relation competentSur
 * 
 */
public interface CompetentSurRepository extends PagingAndSortingRepository<CompetentSur,Long>  {

	/**
	 * 
	 * @param akCollaborateur
	 * @return Une liste de relation competentSur liées a un collaborateur
	 */
	@Query("MATCH (Collaborateur { akCollaborateur: {akCollaborateur} })-[COMPETENT_SUR]-(Competence) RETURN *")
	Stream<CompetentSur> findByAkCollaborateur(@Param("akCollaborateur") String akCollaborateur);

	/**
	 * 
	 * @param akCompetence
	 * @return Une liste de relation competentSur liées a une akCompetence
	 */
	@Query("MATCH (Collaborateur)-[COMPETENT_SUR]-(Competence { akCompetence: {akCompetence} }) RETURN *")
	Stream<CompetentSur> findByAKCompetence(@Param("akCompetence") String akCompetence);

}
