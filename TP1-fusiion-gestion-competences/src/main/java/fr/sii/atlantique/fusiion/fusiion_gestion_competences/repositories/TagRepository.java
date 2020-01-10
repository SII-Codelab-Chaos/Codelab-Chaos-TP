package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Tag;


/**
 * @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds tag
 * 
 */
public interface TagRepository extends PagingAndSortingRepository<Tag,Long>{

	/**
	 * permet de rechercher un tag par son AK
	 * @param akTag, l'ak du tag recherché
	 * @return Tag, le tag trouvé
	 */
	@Query("MATCH (n:Tag{ akTag: {akTag} } ) RETURN n LIMIT 1")
	Tag findOneByAkTag(@Param("akTag") String akTag);

	/**
	 * retourne la liste des tags lié contenant un pattern
	 * @param nom, le nom (pattern) recherché du tag
	 * @return List<Tag>, la liste des tags contenant le pattern
	 */
	@Query(value = "MATCH (n:Tag) WHERE toLower(n.nom) =~ {regexNom} RETURN n")
	List<Tag> findByNom(@Param("regexNom") String regexNom);
	
	/**
	 * Permet de rechercher les tags par un akCompetence
	 * 
	 * @param akCompetence la clé de la competence
	 * @return La liste des tags liés a une compétence
	 */
	@Query(value = "MATCH (Competence{ akCompetence: {akCompetence} })-[TAGUE_SUR]-(t:Tag) RETURN t")
	List<Tag> findByAkCompetence(@Param("akCompetence") String akCompetence);

}
