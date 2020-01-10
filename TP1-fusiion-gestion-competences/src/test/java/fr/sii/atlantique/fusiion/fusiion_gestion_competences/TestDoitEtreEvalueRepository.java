package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.DoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.ObjectifDoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.DoitEtreEvalueRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompetencesApplication.class)
@DataNeo4jTest//permet, entre autre, de réinitialiser la base à chaque test
public class TestDoitEtreEvalueRepository {
	
	@Autowired
	DoitEtreEvalueRepository doitEtreEvalueRepository;
		
	@Test
	public void testFindByAkObjectif() {
		Competence competence = new Competence("aktest", "test", "test_desc", true, false);
		Objectif objectif = new Objectif("aktest", "test", "test_desc", "badge");
		DoitEtreEvalue doitEtreEvalue = new DoitEtreEvalue(objectif, competence, 5);
		
		this.doitEtreEvalueRepository.save(doitEtreEvalue);
		List<ObjectifDoitEtreEvalue> result = doitEtreEvalueRepository.findByAkObjectif("aktest");
		
		assertTrue(result.size()==1);
	}
	
}
