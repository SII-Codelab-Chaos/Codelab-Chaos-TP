package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.DoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.ObjectifDoitEtreEvalue;

/**
 *  @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds de la relation DoitEtreEvalue 
 * 
 */
public interface DoitEtreEvalueRepository extends PagingAndSortingRepository<DoitEtreEvalue, Long>{
	
	/**
	 * 
	 * @param akOjectif
	 * @return Une liste de ObjectifDoitEtreEvalue liée à un Objectif
	 */
	@Query("MATCH (obj:Objectif { akObjectif: {akObjectif} })-[doit:DOIT_ETRE_EVALUE]->(comp:Competence) WITH obj.akObjectif as akObjectif ,doit.notation as notation ,comp.akCompetence as akCompetence return akObjectif, notation, akCompetence")
	List<ObjectifDoitEtreEvalue> findByAkObjectif(@Param("akObjectif") String akObjectif);
}
