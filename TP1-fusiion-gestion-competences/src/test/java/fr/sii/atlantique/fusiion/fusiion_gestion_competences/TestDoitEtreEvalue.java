package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import org.junit.Before;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.DoitEtreEvalue;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import junit.framework.TestCase;

public class TestDoitEtreEvalue extends TestCase {
	
	private Objectif objectif;
	private Competence competence;
	private DoitEtreEvalue doitEtreEvalue;
	
	public TestDoitEtreEvalue(String name) {
		super(name);
	}
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		objectif = new Objectif("aktest", "test", "test_desc", "badge");
		competence = new Competence("aktest", "test", "test_desc", true, false);
		doitEtreEvalue = new DoitEtreEvalue(objectif, competence, 4);
	}
	
	public void testConstructeurVide() {
		doitEtreEvalue = new DoitEtreEvalue();
		
		assertNull(doitEtreEvalue.getObjectif());
		assertNull(doitEtreEvalue.getCompetence());
		assertTrue(doitEtreEvalue.getNotation() == 0);
	}
	
	public void testConstructeur() {
		
		assertTrue(doitEtreEvalue.getObjectif().equals(objectif));
		assertTrue(doitEtreEvalue.getCompetence().equals(competence));
		assertTrue(doitEtreEvalue.getNotation() == 4);
	}
	
	public void testGetObjectif() {
		assertEquals(objectif, doitEtreEvalue.getObjectif());
	}

	public void testSetObjectif() {
		Objectif objectif_autre = new Objectif("un autre ak", "un autre nom", "test_desc", "badge");
		doitEtreEvalue.setObjectif(objectif_autre);
		
		assertEquals(objectif_autre, doitEtreEvalue.getObjectif());
	}

	public void testGetCompetence() {
		assertEquals(competence, doitEtreEvalue.getCompetence());
	}

	public void testSetCompetence() {
		Competence competence_autre = new Competence("un autre ak", "un autre nom", "test_desc", true, false);
		doitEtreEvalue.setCompetence(competence_autre);
		
		assertEquals(competence_autre, doitEtreEvalue.getCompetence());
	}

	public void testGetNotation() {
		assertTrue(doitEtreEvalue.getNotation() == 4);
	}

	public void testSetNotation() {
		doitEtreEvalue.setNotation(1);
		
		assertTrue(doitEtreEvalue.getNotation() == 1);
	}
	
	

}
