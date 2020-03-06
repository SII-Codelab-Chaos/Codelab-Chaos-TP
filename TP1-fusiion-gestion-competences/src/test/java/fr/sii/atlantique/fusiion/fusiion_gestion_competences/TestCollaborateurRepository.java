package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CollaborateurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetentSurRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GestionCompetencesApplication.class)
@DataNeo4jTest//permet, entre autre, de réinitialiser la base à chaque test
public class TestCollaborateurRepository {
	@Autowired
	CollaborateurRepository collaborateurRepository;

	@Autowired
	CompetentSurRepository competentSurRepository;
	
	@Test
	public void testFindByAkCollaborateurFind() {
		Collaborateur collaborateur = new Collaborateur("aktest");
		collaborateurRepository.save(collaborateur);
		
		Collaborateur collaborateur2 = new Collaborateur("aktest2");
		collaborateurRepository.save(collaborateur2);
		
		
		Optional<Collaborateur> resultat = (collaborateurRepository.findOneByAkCollaborateur(collaborateur.getAkCollaborateur()));
		
		assertTrue(collaborateur.getAkCollaborateur().equals(resultat.get().getAkCollaborateur()));
	}
	
	@Test
	public void testFindByAkCollaborateurNotFind() {
		Collaborateur collaborateur = new Collaborateur("aktest");
		collaborateurRepository.save(collaborateur);
		
		Collaborateur collaborateur2 = new Collaborateur("aktest2");
		collaborateurRepository.save(collaborateur2);
		
		
		Optional<Collaborateur> resultat = (collaborateurRepository.findOneByAkCollaborateur("un autre ak"));
		
		assertTrue(!resultat.isPresent());
	}
	
	@Test
	public void testRechercherParCompetences() {
		Collaborateur collaborateur = new Collaborateur("ak_collab");
		Competence competence = new Competence("akCompetence", "test", "description", true, true);
		CompetentSur competentSur = new CompetentSur(collaborateur, competence, LocalDateTime.now(), 3, true);
		competentSurRepository.save(competentSur);
		
		Collaborateur collaborateur2 = new Collaborateur("ak_autre_collab");
		collaborateurRepository.save(collaborateur2);
		
		assertEquals(singletonList(collaborateur),collaborateurRepository.rechercherParCompetences(singletonList("akCompetence")));
	}
	
	@Test
	public void testRechercherParCompetencesToMap() {
		Collaborateur collaborateur = new Collaborateur("ak_collab");
		Collaborateur collaborateur_ak_autre_collab = new Collaborateur("ak_autre_collab");
		
		Competence akCompetence = new Competence("akCompetence", "test", "description", true, true);
		Competence akCompetence2 = new Competence("akCompetence2", "test2", "description", true, true);
		
		
		CompetentSur competentSur = new CompetentSur(collaborateur, akCompetence, LocalDateTime.now(), 3, true);
		competentSurRepository.save(competentSur);
		competentSur = new CompetentSur(collaborateur, akCompetence, LocalDateTime.now(), 5, true);
		competentSurRepository.save(competentSur);
		competentSur = new CompetentSur(collaborateur, akCompetence, LocalDateTime.now(), 3, true);
		competentSurRepository.save(competentSur);
		
		
		competentSur = new CompetentSur(collaborateur, akCompetence2, LocalDateTime.now(), 3, true);
		competentSurRepository.save(competentSur);
		
		
		collaborateurRepository.save(collaborateur_ak_autre_collab);
		
		competentSur = new CompetentSur(collaborateur_ak_autre_collab, akCompetence, LocalDateTime.now(), 3, true);
		competentSurRepository.save(competentSur);
		
		List<String> recherches = new ArrayList<>();
		recherches.add("akCompetence");
		recherches.add("akCompetence2");
		
		List<Map<String, Map<String, Object>>> resultats = (List<Map<String, Map<String, Object>>>) collaborateurRepository.rechercherParCompetencesToMap(recherches);
		System.out.println(resultats.toString());
		
		assertTrue(resultats.size() == 1);
		
		assertTrue(resultats.get(0).get("collaborateur").get("akCollaborateur").equals("ak_collab"));
		List<Map<String, Object>> competences = (List<Map<String, Object>>) resultats.get(0).get("collaborateur").get("competences");
		
		assertTrue(competences.size() == 2);
		assertTrue(((List<Map<String, Object>>) resultats.get(0).get("collaborateur").get("competences")).size() == 2);
		
		assertTrue(
				(competences.get(0).get("akCompetence").equals("akCompetence") && competences.get(1).get("akCompetence").equals("akCompetence2"))
				||
				(competences.get(0).get("akCompetence").equals("akCompetence2") && competences.get(1).get("akCompetence").equals("akCompetence"))
		);
			
		List<Map<String, Object>> relations;
		if(competences.get(0).get("akCompetence").equals("akCompetence")) {
			relations = (List<Map<String, Object>>) competences.get(0).get("relations");
			assertTrue(relations.size() == 3);
			
			relations = (List<Map<String, Object>>) competences.get(1).get("relations");
			assertTrue(relations.size() == 1);
		} else {
			relations = (List<Map<String, Object>>) competences.get(0).get("relations");
			assertTrue(relations.size() == 1);
			
			relations = (List<Map<String, Object>>) competences.get(1).get("relations");
			assertTrue(relations.size() == 3);
		}
	}
	
	@Test
	public void testfindRelationByAkcollaborateurToMap() {
		Collaborateur collaborateur = new Collaborateur("ak_collab");
		LocalDateTime date = LocalDateTime.now();
		Competence akCompetence = new Competence("akCompetence", "test", "description", true, true);
		Competence akCompetence2 = new Competence("akCompetence2", "test2", "description", true, true);
		Competence akCompetence3 = new Competence("akCompetence3", "test3", "description", true, true);
		
		
		CompetentSur competentSur = new CompetentSur(collaborateur, akCompetence, date, 3, true);
		competentSurRepository.save(competentSur);
		competentSur = new CompetentSur(collaborateur, akCompetence, date.plusDays(1), 5, true);
		competentSurRepository.save(competentSur);
		competentSur = new CompetentSur(collaborateur, akCompetence, date.plusDays(3), 3, true);
		competentSurRepository.save(competentSur);
		
		
		competentSur = new CompetentSur(collaborateur, akCompetence2, date, 3, true);
		competentSurRepository.save(competentSur);
		
		competentSur = new CompetentSur(collaborateur, akCompetence3, date, 3, true);
		competentSurRepository.save(competentSur);
		
		competentSur = new CompetentSur(collaborateur, akCompetence3, date.plusDays(10), 3, true);
		competentSurRepository.save(competentSur);
		
		List<String> recherches = new ArrayList<>();
		recherches.add("akCompetence");
		recherches.add("akCompetence2");
		
		List<Map<String, Map<String, Object>>> resultats = (List<Map<String, Map<String, Object>>>) collaborateurRepository.findRelationByAkcollaborateurToMap("ak_collab", recherches);
		System.out.println(resultats.toString());
		
		assertTrue(resultats.size() == 2);
		
		assertTrue(
				(resultats.get(0).get("competence").get("akCompetence").equals("akCompetence") && resultats.get(1).get("competence").get("akCompetence").equals("akCompetence2"))
				||
				(resultats.get(0).get("competence").get("akCompetence").equals("akCompetence2") && resultats.get(1).get("competence").get("akCompetence").equals("akCompetence"))
		);
		
		if(resultats.get(0).get("competence").get("akCompetence").equals("akCompetence")) {
			List<Map<String, Object>> relations = (List<Map<String, Object>>) resultats.get(0).get("competence").get("relations");
			assertTrue(relations.size() == 3);
			
			relations = (List<Map<String, Object>>) resultats.get(1).get("competence").get("relations");
			assertTrue(relations.size() == 1);
		}else {
			List<Map<String, Object>> relations = (List<Map<String, Object>>) resultats.get(0).get("competence").get("relations");
			assertTrue(relations.size() == 1);
			
			relations = (List<Map<String, Object>>) resultats.get(1).get("competence").get("relations");
			assertTrue(relations.size() == 3);
		}
	}
}
