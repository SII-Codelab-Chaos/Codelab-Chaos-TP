package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.MentionInteretPour;

/**
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds de la relation MentionApprendreSur
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay, Sullivan Pineau et Chesné Sarah
 */
public interface MentionInteretPourRepository extends PagingAndSortingRepository<MentionInteretPour, Long> {

	@Query("MATCH (Collaborateur { akCollaborateur: {akCollaborateur} })-[MENTION_INTERET_POUR]-(Competence { akCompetence: {akCompetence} }) RETURN *")
	List<MentionInteretPour> findAkCollaborateurAndAkCompetence(@Param("akCollaborateur") String akCollaborateur, @Param("akCompetence") String akCompetence);

	@Query("RETURN EXISTS((:Collaborateur { akCollaborateur: {akCollaborateur} })-[:MENTION_INTERET_POUR]-(:Competence { akCompetence: {akCompetence}}))")
	boolean existByAkCollaborateurAndAkCompetence(@Param("akCollaborateur") String akCollaborateur, @Param("akCompetence") String akCompetence);
	

}
