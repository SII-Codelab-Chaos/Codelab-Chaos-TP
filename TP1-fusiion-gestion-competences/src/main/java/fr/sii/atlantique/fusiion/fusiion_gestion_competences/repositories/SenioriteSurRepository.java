package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.SenioriteSur;

/**
 *  @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds de la relation competentSur
 * 
 */
public interface SenioriteSurRepository  extends PagingAndSortingRepository<SenioriteSur, Long>{
	
	/**
	 * 
	 * @param akCompetence, l'ak de la compétence
	 * @return Liste de séniorité
	 */
	@Query("MATCH (Collaborateur)-[SENIORITE_SUR]-(Competence { akCompetence: {akCompetence} }) RETURN *")
	Stream<SenioriteSur> findByAkCompetence(@Param("akCompetence") String akCompetence);
	
	/**
	 * 
	 * @param akCollaborateur
	 * @param akCompetence
	 * @return Liste de séniorité d'une relation Collaborateur / Competence
	 */
	@Query("MATCH (Collaborateur { akCollaborateur: {akCollaborateur} })-[SENIORITE_SUR]-(Competence { akCompetence: {akCompetence} }) RETURN *")
	List<SenioriteSur> findByAkCollaborateurAndAkCompetence(@Param("akCollaborateur") String akCollaborateur, @Param("akCompetence") String akCompetence);
	
	/**
	 * 
	 * @param akCollaborateur, l'ak du collaborateur
	 * @param akCompetence, l'ak de la compétence
	 * @return Booléen à vrai si une relation SENIORITE_SUR existe entre les deux objets recherché
	 */
	@Query("RETURN EXISTS((:Collaborateur { akCollaborateur: {akCollaborateur} })-[:SENIORITE_SUR]-(:Competence { akCompetence: {akCompetence}}))")
	boolean existByAkCollaborateurAndAkCompetence(@Param("akCollaborateur") String akCollaborateur, @Param("akCompetence") String akCompetence);
	
}
