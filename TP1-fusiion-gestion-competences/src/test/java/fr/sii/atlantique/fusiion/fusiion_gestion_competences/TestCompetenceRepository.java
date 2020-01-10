package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CollaborateurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetenceRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetentSurRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompetencesApplication.class)
@DataNeo4jTest//permet, entre autre, de réinitialiser la base à chaque test
public class TestCompetenceRepository {
	
	@Autowired
	CompetenceRepository competenceRepository;
	
	@Autowired
	CollaborateurRepository collaborateurRepository;
	
	@Autowired
	CompetentSurRepository competentSurRepository;
	
	Competence competence = new Competence("aktest", "nomtest", "desctest", true, false);
	Competence competence2 = new Competence("aktest2", "nomtest2", "desctest2", false, true);
	
	@Test
	public void testFindByNomFind() {
		competenceRepository.save(competence);
		competenceRepository.save(competence2);
		
		List<Competence> result = (competenceRepository.findByNom("nomtest"));
		
		assertTrue(result.size() == 1);
	}
	
	@Test
	public void testFindByNomNotFind() {
		competenceRepository.save(competence2);
		
		List<Competence> result = (competenceRepository.findByNom("nomtest"));
		
		assertTrue(result.size() == 0);
	}
	
	@Test
	public void testFindByAkCompetenceFind() {
		competenceRepository.save(competence);
		competenceRepository.save(competence2);
		
		Optional<Competence> result = (competenceRepository.findOneByAkCompetence("aktest"));
		assertTrue(competence.equals(result.get()));
	}
	
	@Test
	public void testFindByAkCompetenceNotFind() {
		competenceRepository.save(competence2);
		
		Optional<Competence> result = (competenceRepository.findOneByAkCompetence("aktest"));
		assertTrue(!result.isPresent());
	}
	
	@Test
	public void testFindLikeCompetenceFindOne() {
		
		competenceRepository.save(competence);
		competenceRepository.save(competence2);
		
		List<Competence> result = (competenceRepository.findLikeCompetence("nomtest2"));
		
		assertTrue(result.size() ==1);
	}
	
	@Test
	public void testFindLikeCompetenceFindTwo() {
		
		competenceRepository.save(competence);
		competenceRepository.save(competence2);
		
		List<Competence> result = (competenceRepository.findLikeCompetence("nom.*"));
		
		assertTrue(result.size() ==2);
	}
	
	@Test
	public void testDeleteCompetenceAndRelation() {
		competenceRepository.save(competence);
		competenceRepository.save(competence2);
		
		Collaborateur collaborateur = new Collaborateur("akcollab");
		collaborateurRepository.save(collaborateur);
		
		LocalDateTime date = LocalDateTime.now();
		CompetentSur relation = new CompetentSur(collaborateur, competence, date, 3, false);
		CompetentSur relation2 = new CompetentSur(collaborateur, competence, date, 1, true);
		
		competentSurRepository.save(relation);
		competentSurRepository.save(relation2);
		
		competenceRepository.deleteCompetenceAndRelation(competence.getAkCompetence());
		
		
		assertFalse((competenceRepository.findOneByAkCompetence(competence.getAkCompetence())).isPresent());
		assertTrue((competentSurRepository.findByAkCollaborateur(collaborateur.getAkCollaborateur())).count() ==0);
		assertTrue((collaborateurRepository.findOneByAkCollaborateur(collaborateur.getAkCollaborateur())).isPresent());
	}

}
