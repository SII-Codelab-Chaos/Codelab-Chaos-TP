package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.AAtteindre;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.ObjectifRetourJSON;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.repositories.AAtteindreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionCompetencesApplication.class)
@DataNeo4jTest//permet, entre autre, de réinitialiser la base à chaque test
public class TestAAtteindreRepository {
	@Autowired
	AAtteindreRepository aAtteindreRepository;
	
	@Test
	public void findOneByAkCollaborateurAndByAkObjectif() {
		AAtteindre aAtteindre = new AAtteindre();
		Collaborateur collaborateur = new Collaborateur("TestAkCollaborateur");
		Objectif objectif = new Objectif("aktest", "test", "test_desc", "badge");
		aAtteindre.setCollaborateur(collaborateur);
		aAtteindre.setObjectif(objectif);
		aAtteindreRepository.save(aAtteindre);
		
		Optional<AAtteindre> aAtteindreTest = aAtteindreRepository.findOneByAkCollaborateurAndByAkObjectif("TestAkCollaborateur", "aktest");
		
		assertTrue(aAtteindreTest.isPresent());
	}
	
	@Test
	public void findByAkCollaborateur() {
		AAtteindre aAtteindre = new AAtteindre();
		Collaborateur collaborateur = new Collaborateur("TestAkCollaborateur");
		Objectif objectif = new Objectif("aktest", "test", "test_desc", "badge");
		aAtteindre.setCollaborateur(collaborateur);
		aAtteindre.setObjectif(objectif);
		aAtteindreRepository.save(aAtteindre);

		AAtteindre aAtteindre2 = new AAtteindre();
		Collaborateur collaborateur2 = new Collaborateur("TestAkCollaborateur2");
		Objectif objectif2 = new Objectif("aktest2", "test", "test_desc", "badge");
		aAtteindre2.setCollaborateur(collaborateur2);
		aAtteindre2.setObjectif(objectif2);
		
		aAtteindreRepository.save(aAtteindre2);
		
		List<ObjectifRetourJSON> AAtteindre = aAtteindreRepository.findByAkCollaborateur("TestAkCollaborateur");
		
		assertTrue(AAtteindre.size()==1);
	}
	
	
	@Test
	public void findByAkCollaborateurValideTrue() {
		AAtteindre aAtteindre = new AAtteindre();
		Collaborateur collaborateur = new Collaborateur("TestAkCollaborateur");
		Objectif objectif = new Objectif("aktest", "test", "test_desc", "badge");
		aAtteindre.setCollaborateur(collaborateur);
		aAtteindre.setObjectif(objectif);
		aAtteindre.setValide(true);
		aAtteindreRepository.save(aAtteindre);

		AAtteindre aAtteindre2 = new AAtteindre();
		Collaborateur collaborateur2 = new Collaborateur("TestAkCollaborateur");
		Objectif objectif2 = new Objectif("aktest2", "test", "test_desc", "badge");
		aAtteindre2.setCollaborateur(collaborateur2);
		aAtteindre2.setObjectif(objectif2);
		
		aAtteindreRepository.save(aAtteindre2);
		
		List<ObjectifRetourJSON> AAtteindre = aAtteindreRepository.findByAkCollaborateur("TestAkCollaborateur", true);
		
		assertTrue(AAtteindre.size()==1);
	}
	
	@Test
	public void findByAkCollaborateurValideFalse() {
		AAtteindre aAtteindre = new AAtteindre();
		Collaborateur collaborateur = new Collaborateur("TestAkCollaborateur");
		Objectif objectif = new Objectif("aktest", "test", "test_desc", "badge");
		aAtteindre.setCollaborateur(collaborateur);
		aAtteindre.setObjectif(objectif);
		aAtteindreRepository.save(aAtteindre);

		AAtteindre aAtteindre2 = new AAtteindre();
		Collaborateur collaborateur2 = new Collaborateur("TestAkCollaborateur");
		Objectif objectif2 = new Objectif("aktest2", "test", "test_desc", "badge");
		aAtteindre2.setCollaborateur(collaborateur2);
		aAtteindre2.setObjectif(objectif2);
		
		aAtteindreRepository.save(aAtteindre2);
		
		List<ObjectifRetourJSON> AAtteindre = aAtteindreRepository.findByAkCollaborateur("TestAkCollaborateur", false);
		
		assertTrue(AAtteindre.size()==2);
	}
}
