package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import java.time.LocalDateTime;

import org.junit.Before;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.AAtteindre;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import junit.framework.TestCase;

public class testAAtteindre extends TestCase {
	
	private Collaborateur collaborateur;
	private Objectif objectif;
	private AAtteindre aAtteindre;
	
	private LocalDateTime date;
	
	public testAAtteindre(String name) {
		super(name);
	}
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		date = LocalDateTime.now();
		objectif = new Objectif("aktest", "test", "test_desc", "badge");
		collaborateur = new Collaborateur("akCollaborateur");
		aAtteindre = new AAtteindre(collaborateur, objectif, date, false);
	}
	
	public void testConstructeurVide() {
		aAtteindre = new AAtteindre();
		
		assertNull(aAtteindre.getCollaborateur());
		assertNull(aAtteindre.getObjectif());
		assertNull(aAtteindre.getDate());
		assertFalse(aAtteindre.getValide());
	}
	
	public void testConstructeur() {
		assertEquals(collaborateur, aAtteindre.getCollaborateur());
		assertEquals(objectif, aAtteindre.getObjectif());
		assertEquals(date, aAtteindre.getDate());
		assertFalse(aAtteindre.getValide());
	}
	
	public void testGetCollaborateur() {
		assertEquals(collaborateur, aAtteindre.getCollaborateur());
	}

	public void testSetCollaborateur() {
		Collaborateur collaborateur_autre = new Collaborateur("un autre ak");
		aAtteindre.setCollaborateur(collaborateur_autre);
		assertEquals(collaborateur_autre, aAtteindre.getCollaborateur());
	}

	public void testGetObjectif() {
		assertEquals(objectif, aAtteindre.getObjectif());
	}

	public void testSetObjectif() {
		Objectif objectif_autre = new Objectif("un autre ak", "un autre nom", "test_desc", "badge");
		aAtteindre.setObjectif(objectif_autre);
		assertEquals(objectif_autre, aAtteindre.getObjectif());
	}

	public void testGetDate() {
		assertEquals(date, aAtteindre.getDate());
	}

	public void testSetDate() {
		LocalDateTime date_autre= date.plusDays(1);
		aAtteindre.setDate(date_autre);
		assertEquals(date_autre, aAtteindre.getDate());
	}

	public void testGetValide() {
		assertFalse( aAtteindre.getValide());
	}

	public void testSetValide() {
		assertFalse(aAtteindre.getValide());
		aAtteindre.setValide(true);
		
		assertTrue( aAtteindre.getValide());
	}

}
