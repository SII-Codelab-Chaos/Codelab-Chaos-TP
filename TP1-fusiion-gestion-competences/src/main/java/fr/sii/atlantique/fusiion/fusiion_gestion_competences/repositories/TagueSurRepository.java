package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.TagueSur;

/**
 * 
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *
 */
public interface TagueSurRepository  extends PagingAndSortingRepository<TagueSur,Long>{

	/**
	 * 
	 * @param akCompetence
	 * @return Une liste de relation tagueSur liées a une competence
	 */
	@Query(value = "MATCH (Competence { akCompetence: {akCompetence} })-[TAGUE_SUR]-(Tag) RETURN *")
	List<TagueSur> findByAkCompetence(@Param("akCompetence") String akCompetence);

	/**
	 * 
	 * @param akTag
	 * @return une liste de relation TagueSur liées à un tag
	 */
	@Query(value = "MATCH (Tag { akTag: {akTag} })-[TAGUE_SUR]-(Competence) RETURN *")
	List<TagueSur> findByAkTag(@Param("akTag") String akTag);
	
	/**
	 * Permet de savoir si une relation Tag / competence existe
	 *
	 * @param akTag la cle du tag
	 * @param akCompetence la cle de la competence
	 */
	@Query(value = "MATCH((Tag { akTag: {akTag} })-[TAGUE_SUR]-(Competence { akCompetence: {akCompetence}})) return *")
	TagueSur findByAkTagAndByAkCompetence(@Param("akTag") String akTag, @Param("akCompetence") String akCompetence);
}
