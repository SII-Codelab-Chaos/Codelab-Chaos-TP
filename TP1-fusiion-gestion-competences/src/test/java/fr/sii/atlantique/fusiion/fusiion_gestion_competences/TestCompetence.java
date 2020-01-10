package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import org.junit.Before;
import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.response_body.CompetencesParCollaborateurJSON;
import junit.framework.TestCase;

public class TestCompetence extends TestCase {

	private Competence cmp;
	
	public TestCompetence(String name) {
		super(name);
	}
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		cmp = new Competence("aktest", "test", "test_desc", true, false);
	}

	public void testConstructeurVide() {
		Competence cmpVide = new Competence();
		boolean akVide = cmpVide.getAkCompetence().equals("");
		assertTrue(akVide);
	}

	public void testGetAk_competence() {
		assertEquals("aktest", cmp.getAkCompetence());
	}

	public void testSetAk_competence() {
		Competence cmp2 = new Competence("Test_ak", "test", "test_desc", true, false);
		cmp2.setAkCompetence("Test_ak2");
		assertEquals("Test_ak2", cmp2.getAkCompetence());
	}

	public void testGetNom() {
		assertEquals("test", cmp.getNom());
	}

	public void testSetNom() {
		Competence cmp2 = new Competence("Test_ak", "test", "test_desc", true, false);
		cmp2.setNom("test2");
		assertEquals("test2", cmp2.getNom());
	}

	public void testGetDescription() {
		assertEquals("test_desc", cmp.getDescription());
	}

	public void testSetDescription() {
		Competence cmp2 = new Competence("Test_ak", "test", "test_desc", true, false);
		cmp2.setDescription("test_desc2");
		assertEquals("test_desc2", cmp2.getDescription());
	}

	public void testIsValide() {
		assertEquals(true, cmp.isValide());
	}

	public void testSetValide() {
		Competence cmp2 = new Competence("Test_ak", "test", "test_desc", true, false);
		cmp2.setValide(false);
		assertEquals(false, cmp2.isValide());
	}

	public void testGetPriorite() {
		assertEquals(false, cmp.getPriorite());
	}

	public void testSetPriorite() {
		Competence cmp2 = new Competence("Test_ak", "test", "test_desc", true, false);
		cmp2.setPriorite(true);
		assertEquals(true, cmp2.getPriorite());
	}

	public void testToString() {
		System.out.println();
		assertEquals("Competence [akCompetence=" + cmp.getAkCompetence()
				+ ", nom=" + cmp.getNom()
				+ ", description=" + cmp.getDescription()
				+ ", valide=" + cmp.isValide()
				+ ", priorite=" + cmp.getPriorite()
				+ "]"
			,cmp.toString());
	}
	
	@Test
	public void TestEqualsTrueSameObj() {
		Competence cmpBis  = cmp;
		
		assertTrue(cmp.equals(cmpBis));
	}
	
	@Test
	public void TestEqualsFalseObjNull() {	
		assertFalse(cmp.equals(null));
	}
	
	@Test
	public void TestEqualsFalseObjClasseDiff() {
		CompetencesParCollaborateurJSON cmps = new CompetencesParCollaborateurJSON();
		assertFalse(cmp.equals(cmps));
	}
	
	@Test
	public void TestEqualsFalseAkCompetenceNull() {
		Competence cmpBis = new Competence(
				"non nulle"
				,cmp.getNom()
				,cmp.getDescription()
				,cmp.isValide()
				,cmp.getPriorite());
		cmp.setAkCompetence(null);
		
		assertFalse(cmp.equals(cmpBis));
	}
	
	@Test
	public void TestEqualsFalseAkCompetenceDiff() {
		Competence cmpBis = new Competence(
				"un autre ak"
				,cmp.getNom()
				,cmp.getDescription()
				,cmp.isValide()
				,cmp.getPriorite());
		
		assertFalse(cmp.equals(cmpBis));
	}
	
	@Test
	public void TestEqualsTrue() {

		Competence cmpBis = new Competence(
				cmp.getAkCompetence()
				,cmp.getNom()
				,cmp.getDescription()
				,cmp.isValide()
				,cmp.getPriorite());	
		
		assertTrue(cmp.equals(cmpBis));
	}

}

