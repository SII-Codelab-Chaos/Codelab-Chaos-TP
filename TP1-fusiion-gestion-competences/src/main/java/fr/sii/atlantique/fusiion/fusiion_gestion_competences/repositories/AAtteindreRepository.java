package fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.AAtteindre;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifRetourJSON;

/**
 *  @author Esteban Launay, Sébastien Vallée, Sullivan Pineau, François Hallereau
 *  
 * Classe permettant de manipuler la base de donnée neo4j et de requêter dessus, particulièrement sur l'ensemble des noeuds de la relation AAtteindre 
 * 
 */
public interface AAtteindreRepository extends PagingAndSortingRepository<AAtteindre,Long> {
	
	/**
	 * Recherche de relation à AAtteindre d'après un akcollaborateur et akobjectif
	 * @param akCollaborateur
	 * @param akObjectif
	 * @return Optionnal AAtteindre
	 */
	@Query("MATCH (Collaborateur { akCollaborateur: {akCollaborateur} })-[A_ATTEINDRE]-(Objectif { akObjectif: {akObjectif} }) RETURN * LIMIT 1")
	Optional<AAtteindre> findOneByAkCollaborateurAndByAkObjectif(@Param("akCollaborateur") String akCollaborateur, @Param("akObjectif") String akObjectif);

	/**
	 * Recherche la liste ObjectifRetourJSON des objectifs atteint et non par le collaborateur
	 * @param akCollaborateur
	 * @return List<ObjectifRetourJSON>
	 */
	@Query("MATCH (collaborateur:Collaborateur { akCollaborateur: {akCollaborateur} })-[atteindre:A_ATTEINDRE]->(obj:Objectif) WITH obj.akObjectif as akObjectif , obj.nom as nom, obj.description as description, obj.badge as badge, atteindre.valide as valide return akObjectif, nom, description, badge, valide")
	List<ObjectifRetourJSON> findByAkCollaborateur(@Param("akCollaborateur") String akCollaborateur);
	
	/**
	 * Recherche la liste ObjectifRetourJSON des objectifs atteint ou non par le collaborateur
	 * @param akCollaborateur
	 * @param valide
	 * @return List<ObjectifRetourJSON>
	 */
	@Query("MATCH (collaborateur:Collaborateur { akCollaborateur: {akCollaborateur} })-[atteindre:A_ATTEINDRE {valide: {valide}}]->(obj:Objectif) WITH obj.akObjectif as akObjectif , obj.nom as nom, obj.description as description, obj.badge as badge, atteindre.valide as valide return akObjectif, nom, description, badge, valide")
	List<ObjectifRetourJSON> findByAkCollaborateur(@Param("akCollaborateur") String akCollaborateur, @Param("valide") boolean valide);
}
