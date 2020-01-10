package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.DoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.DoitEtreEvalueRepository;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.ObjectifRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompetencesApplication.class)
@DataNeo4jTest//permet, entre autre, de réinitialiser la base à chaque test
public class TestObjectifRepository {
	@Autowired
	ObjectifRepository objectifRepository;
	
	@Autowired
	DoitEtreEvalueRepository doitEtreEvalueRepository;
	
	@Test
	public void testFindByAkObjectif() {
		Objectif objectif = new Objectif("ak_Objectif", "nom", "description");
		objectifRepository.save(objectif);
		
		Objectif resultat = (objectifRepository.findOneByAkObjectif("ak_Objectif").get());
		
		assertTrue(objectif.getAkObjectif().equals(resultat.getAkObjectif()));
	}

	@Test
	public void testFindObjectifsByAkCompetence() {
		Objectif objectif = new Objectif("ak_Objectif", "nom", "description");
		Objectif objectif_autre = new Objectif("ak_autre_Objectif", "un autre nom", "description");
		Objectif objectif_out = new Objectif("ak_out_objectif", "un autre nom", "description");
		Competence competence = new Competence("ak_competence", "test", "test_desc", true, false);
		Competence autre_competence = new Competence("ak_autre_competence", "test", "test_desc", true, false);
		
		DoitEtreEvalue doitEtreEvalue = new DoitEtreEvalue(objectif, competence, 5);
		this.doitEtreEvalueRepository.save(doitEtreEvalue);
		doitEtreEvalue = new DoitEtreEvalue(objectif, autre_competence, 5);
		this.doitEtreEvalueRepository.save(doitEtreEvalue);
		
		doitEtreEvalue = new DoitEtreEvalue(objectif_autre, competence, 3);
		this.doitEtreEvalueRepository.save(doitEtreEvalue);
		
		doitEtreEvalue = new DoitEtreEvalue(objectif_out, autre_competence, 3);
		this.doitEtreEvalueRepository.save(doitEtreEvalue);
		
		List<Map<String, Map<String, Object>>> objectifs = (List<Map<String, Map<String, Object>>>) this.objectifRepository.findObjectifsByAkCompetenceToMap("ak_competence");
		System.out.println(objectifs.toString());
		assertTrue(objectifs.size() == 2);
		
		assertTrue(
			(objectifs.get(0).get("objectif").get("akObjectif").equals("ak_Objectif")) && (objectifs.get(1).get("objectif").get("akObjectif").equals("ak_autre_Objectif"))
			||
			(objectifs.get(0).get("objectif").get("akObjectif").equals("ak_autre_Objectif")) && (objectifs.get(1).get("objectif").get("akObjectif").equals("ak_Objectif"))
		);
		
		List<Map<String, Object>> competences;
		if(objectifs.get(0).get("objectif").get("akObjectif").equals("ak_Objectif")) {
			competences = (List<Map<String, Object>>) objectifs.get(0).get("objectif").get("competences");
			assertTrue(competences.size() == 2);
			
			competences = (List<Map<String, Object>>) objectifs.get(1).get("objectif").get("competences");
			assertTrue(competences.size() == 1);
		}else {
			competences = (List<Map<String, Object>>) objectifs.get(0).get("objectif").get("competences");
			assertTrue(competences.size() == 1);
			
			competences = (List<Map<String, Object>>) objectifs.get(1).get("objectif").get("competences");
			assertTrue(competences.size() == 2);
		}
		
		
	}
}
