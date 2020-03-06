package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetentSurRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GestionCompetencesApplication.class)
@DataNeo4jTest//permet, entre autre, de réinitialiser la base à chaque test
public class TestCompetentSurRepository {
	
	@Autowired
	CompetentSurRepository competentSurRepository;
	
	Competence competence = new Competence("aktest", "nomtest", "desctest", true, false);
	
	Collaborateur collaborateur = new Collaborateur("aktestCollab");
	Collaborateur collaborateur2 = new Collaborateur("aktestCollab2");
	
	@Test
	public void testFindByAkCollaborateurFind() {
		LocalDateTime date = LocalDateTime.now();
		CompetentSur relation = new CompetentSur(collaborateur, competence, date, 3, false);
		CompetentSur relation2 = new CompetentSur(collaborateur2, competence, date, 3, false);
		competentSurRepository.save(relation);
		competentSurRepository.save(relation2);
		
		
		Stream<CompetentSur> result = (competentSurRepository.findByAkCollaborateur(collaborateur.getAkCollaborateur()));
		
		assertTrue(result.count() ==1);
	}
	
	@Test
	public void testFindByAkCollaborateurNotFind() {

		Stream<CompetentSur> result = (competentSurRepository.findByAkCollaborateur(collaborateur.getAkCollaborateur()));
		
		assertTrue(result.count() ==0);
	}
	
	@Test
	public void testFindByAkCompetenceFind() {
		LocalDateTime date = LocalDateTime.now();
		CompetentSur relation = new CompetentSur(collaborateur, competence, date, 3, false);
		CompetentSur relation2 = new CompetentSur(collaborateur2, competence, date, 3, false);
		competentSurRepository.save(relation);
		competentSurRepository.save(relation2);
		
		
		Stream<CompetentSur> result = (competentSurRepository.findByAKCompetence(competence.getAkCompetence()));
		
		assertTrue(result.count() ==2);
	}
	
	@Test
	public void testFindByAkCompetenceNotFind() {

		Stream<CompetentSur> result = (competentSurRepository.findByAKCompetence(competence.getAkCompetence()));
		
		assertTrue(result.count() ==0);
	}

}
