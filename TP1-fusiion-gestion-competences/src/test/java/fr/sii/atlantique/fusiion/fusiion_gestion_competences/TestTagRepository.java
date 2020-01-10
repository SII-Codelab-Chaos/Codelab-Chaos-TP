package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Tag;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CollaborateurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetenceRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.CompetentSurRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.TagRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompetencesApplication.class)
@DataNeo4jTest//permet, entre autre, de réinitialiser la base à chaque test
public class TestTagRepository {
	
	@Autowired
	CompetenceRepository competenceRepository;
	
	@Autowired
	CollaborateurRepository collaborateurRepository;
	
	@Autowired
	CompetentSurRepository competentSurRepository;
    
	@Autowired
	TagRepository tagRepository;
	
	Tag tag = new Tag("gogole", "gogole", "les competences de google");
	Tag tag2 = new Tag("brebis", "brebis", "les brebis des champs");
	
	
	@Test
	public void testFindByNom() {
		tagRepository.save(tag);
		tagRepository.save(tag2);
		
		List<Tag> result = (tagRepository.findByNom(".*gol.*"));
		
		assertTrue(result.size() == 1);
	}
}
