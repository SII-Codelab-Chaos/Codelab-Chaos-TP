package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.CompetencesParCollaborateurJSON;

public class TestCompetencesParCollaborateurJSON {
	
	private Collaborateur collaborateur;
	private Competence competence;
	private CompetentSur relation;
	
	
	@Before
    public void setup() {
		collaborateur = new Collaborateur("email@sii.fr");
		competence = new Competence("JavaAk","JavaTest","",true, false);
		relation = new CompetentSur(collaborateur
											,competence
											,LocalDateTime.now(),3,true);
    }
	
	@Test
	public void TestConstructeurVide(){
		CompetencesParCollaborateurJSON cmps = new CompetencesParCollaborateurJSON();
		
		assertFalse(cmps.getAkCompetence() != null 
				|| cmps.getCertification() != false
				|| cmps.getPriorite() != false
				|| cmps.getValide() != false
				|| cmps.getDate() != null
				|| cmps.getNom() != null
				|| cmps.getNotation() != 0);
	}
	
	@Test
	public void TestConstructeurWithRelation(){
		
		CompetencesParCollaborateurJSON cmps 
						= new CompetencesParCollaborateurJSON(this.relation);
		
		assertTrue(cmps.getAkCompetence().equals(this.competence.getAkCompetence())
			&& cmps.getCertification() == this.relation.getCertification()
			&& cmps.getPriorite() == this.competence.getPriorite()
			&& cmps.getValide() == this.competence.isValide()
			&& cmps.getDate().equals(this.relation.getDate())
			&& cmps.getNom().equals(this.competence.getNom())
			&& cmps.getNotation() == this.relation.getNotation()
		);
	
	}
	
	@Test
	public void testGetAkCompetence() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		assertEquals(this.competence.getAkCompetence(), cmps.getAkCompetence());
	}
	
	@Test
	public void testSetAkCompetence() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		cmps.setAkCompetence("un autre ak");
		assertEquals("un autre ak", cmps.getAkCompetence());
	}
	
	@Test
	public void testGetNom() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);

		assertEquals(this.competence.getNom(), cmps.getNom());
	}
	
	@Test
	public void testSetNom() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		cmps.setNom("un autre nom");
		assertEquals("un autre nom", cmps.getNom());
	}
	
	@Test
	public void testGetValide() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		assertEquals(competence.isValide(), cmps.getValide());
	}
	
	@Test
	public void testSetValide() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		cmps.setValide(!competence.isValide());
		assertEquals(!competence.isValide(), cmps.getValide());
	}
	
	@Test
	public void testGetPriorite() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		assertEquals(competence.getPriorite(), cmps.getPriorite());
	}
	
	@Test
	public void testSetPriorite() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		cmps.setPriorite(!competence.getPriorite());
		assertEquals(!competence.getPriorite(), cmps.getPriorite());
	}
	
	@Test
	public void testGetDate() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		assertEquals(relation.getDate(), cmps.getDate());
	}
	
	@Test
	public void testSetDate() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		cmps.setDate(relation.getDate().plusDays(1));
		assertFalse(relation.getDate().isEqual(cmps.getDate()));
	}
	
	@Test
	public void testGetNotation() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		assertEquals(relation.getNotation(), cmps.getNotation());
	}
	
	@Test
	public void testSetNotation() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		cmps.setNotation(relation.getNotation()+1);
		assertEquals(relation.getNotation()+1, cmps.getNotation());
	}
	
	@Test
	public void testGetCertification() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		assertEquals(relation.getCertification(), cmps.getCertification());
	}
	
	@Test
	public void testSetCertification() {
		CompetencesParCollaborateurJSON cmps 
			= new CompetencesParCollaborateurJSON(this.relation);
		
		cmps.setCertification(!relation.getCertification());
		assertEquals(!relation.getCertification(), cmps.getCertification());
	}
	
	@Test
	public void TestEqualsTrueSameObj() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		CompetencesParCollaborateurJSON cmpsBis 
		= cmps;
		
		assertTrue(cmps.equals(cmpsBis));
	}
	
	@Test
	public void TestEqualsFalseObjNull() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		CompetencesParCollaborateurJSON cmpsBis 
		= null;
		
		assertFalse(cmps.equals(cmpsBis));
	}
	
	@Test
	public void TestEqualsFalseObjClasseDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		
		assertFalse(cmps.equals(collaborateur));
	}
	
	@Test
	public void TestEqualsFalseAkCompetenceNull() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		cmps.setAkCompetence(null);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseAkCompetenceDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		cmps.setAkCompetence("un autre ak");
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseCertificationDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);
		
		cmps.setCertification(!cmpsbis.getCertification());
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseDateNull() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		cmps.setDate(null);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseDateDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		cmps.setDate(cmps.getDate().plusDays(1));
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseNomNull() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		cmps.setNom(null);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseNomDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		cmps.setNom("un autre nom");
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseNotationDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		cmps.setNotation(cmpsbis.getNotation()+1);
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalsePrioriteDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		cmps.setPriorite(!cmpsbis.getPriorite());
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsFalseValideDiff() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		cmps.setValide(!cmpsbis.getValide());
		
		assertFalse(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestEqualsTrue() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		CompetencesParCollaborateurJSON cmpsbis 
		= new CompetencesParCollaborateurJSON(this.relation);	
		
		assertTrue(cmps.equals(cmpsbis));
	}
	
	@Test
	public void TestToString() {
		CompetencesParCollaborateurJSON cmps 
		= new CompetencesParCollaborateurJSON(this.relation);
		
		String resultat = "CompetencesParCollaborateurJSON [akCompetence="+competence.getAkCompetence()
		+ ", nom=" + competence.getNom()
		+", description=" + competence.getDescription()
		+ ", valide=" + competence.isValide()
		+ ", priorite=" + competence.getPriorite()
		+ ", date=" + relation.getDate()
		+ ", notation=" + relation.getNotation()
		+ ", certification=" + relation.getCertification()
		+ "]";
		
		assertEquals(resultat, cmps.toString());
	}
	
	
	
	
}
