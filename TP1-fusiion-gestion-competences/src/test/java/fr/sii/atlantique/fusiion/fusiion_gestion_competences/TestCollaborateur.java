package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;

public class TestCollaborateur{

	Collaborateur collaborateur = new Collaborateur("TestAkCollaborateur");

	@Test
	public void testConstructeurVide() {
		Collaborateur collaborateurVide = new Collaborateur();
		boolean akVide = collaborateurVide.getAkCollaborateur().equals("");
		assertTrue(akVide);
	}

	@Test
	public void testGetAkCompetence() {
		assertEquals("TestAkCollaborateur", collaborateur.getAkCollaborateur());
	}
	
	@Test
	public void testSetAkCompetence() {
		Collaborateur collab = new Collaborateur("testAk");
		collab.setAkCollaborateur("testAk2");
		assertEquals("testAk2", collab.getAkCollaborateur());
	}
	
	public void testToString() {
		assertEquals("Collaborateur [akCollaborateur=TestAkCollaborateur]",collaborateur.toString());
	}
}
