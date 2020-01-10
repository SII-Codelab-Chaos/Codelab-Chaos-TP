package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;

/**
 *  @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds compétence
 * 
 */
public interface CompetenceRepository extends PagingAndSortingRepository<Competence,Long> {
	
	/**
	 * permet de rechercher une compétence par son nom
	 * @param nom, le nom de la compétence recherché
	 * @return List<competence>, la liste de toutes les compétences trouvées
	 */
	List<Competence> findByNom(@Param("nom") String nom);
	
	/**
	 * permet de rechercher une compétence par son ak
	 * @param akCompetence, l' ak de la competence recherché
	 * @return Competence, la compétence trouvée
	 */
	@Query("MATCH (n:Competence{ akCompetence: {akCompetence} }) RETURN n LIMIT 1")
	Optional<Competence> findOneByAkCompetence(@Param("akCompetence") String akCompetence);
	
	/**
	 * Permet de connaitre les competences ayant pour prefix akCompetence
	 * @param akCompetence
	 * @return liste de competences correspondantes
	 */
	@Query(value = "MATCH (n:Competence) where LOWER(n.nom) =~ LOWER({nom}) return n")
	List<Competence> findLikeCompetence(@Param("nom") String nom);
	
	/**
	 * Permet de supprimer une competence par son akCompetence et toutes ses relations
	 * @param akCompetence, l'identifiant de la competence à supprimer
	 */
	@Query(value = "MATCH (n:Competence{akCompetence:{akCompetence}}) DETACH DELETE n")
	void deleteCompetenceAndRelation(@Param("akCompetence") String akCompetence);
	
	/**
	 * Permet de rechercher les relations par un akTag
	 * 
	 * @param tag la clé du tag 
	 * @return La liste des compétences liés a un tag
	 */
	@Query(value = "MATCH (n:Competence)-[TAGUE_SUR]-(Tag { akTag: {akTag} }) RETURN n")
	List<Competence> findByAkTag(@Param("akTag") String akTag);
}
