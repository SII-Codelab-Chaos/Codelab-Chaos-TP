package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.SenioriteSur;

public class TestSenioriteSur {
	private Collaborateur collaborateur = new Collaborateur("akcollab");
	private Competence competence = new Competence("akcompetence", "test", "desc", true, false);
	private LocalDateTime dateDebut  = LocalDateTime.now();
	private SenioriteSur senioriteSur;
	
	@Before
	public void setup() {
		senioriteSur = new SenioriteSur(collaborateur, competence, dateDebut);
	}
	
	@Test
	public void testInitSeniorite() {
		SenioriteSur senioriteSurBisConfirme = new SenioriteSur(collaborateur, competence, dateDebut.minusYears(3));
		SenioriteSur senioriteSurBisSenior = new SenioriteSur(collaborateur, competence, dateDebut.minusYears(150));
		
		assertTrue(senioriteSur.getSeniorite() == null);
		assertTrue(senioriteSurBisConfirme.getSeniorite() == null);
		assertTrue(senioriteSurBisSenior.getSeniorite() == null);
		
		senioriteSur.initSeniorite();
		senioriteSurBisConfirme.initSeniorite();
		senioriteSurBisSenior.initSeniorite();
		
		assertTrue(senioriteSur.getSeniorite() == "JUNIOR");
		assertTrue(senioriteSurBisConfirme.getSeniorite() == "CONFIRME");
		assertTrue(senioriteSurBisSenior.getSeniorite() == "SENIOR");
		
		
	}
	
	@Test
	public void testConstructeurVide() {
		SenioriteSur snrSur = new SenioriteSur();
		assertEquals(null, snrSur.getDateDebut());
	}
	
	@Test
	public void testConstructeur() {
		assertTrue(senioriteSur.getCollaborateur().getAkCollaborateur().equals(collaborateur.getAkCollaborateur())
				&& senioriteSur.getCompetence().getAkCompetence().equals(competence.getAkCompetence())
				&& senioriteSur.getDateDebut().equals(dateDebut));
	}
	
	@Test
	public void testGetCollaborateur() {
		assertEquals(collaborateur.getAkCollaborateur(), senioriteSur.getCollaborateur().getAkCollaborateur());
	}
	
	@Test
	public void testSetCollaborateur() {
		Collaborateur collaborateur2 = new Collaborateur("akcollab2");
		senioriteSur.setCollaborateur(collaborateur2);
		assertEquals("akcollab2", senioriteSur.getCollaborateur().getAkCollaborateur());
	}
	
	@Test
	public void testGetCompetence() {
		assertEquals(competence.getAkCompetence(), senioriteSur.getCompetence().getAkCompetence());
	}
	
	@Test
	public void testSetCompetence() {
		Competence competence2 = new Competence("akcompetence2", "test", "desc", true, false);
		senioriteSur.setCompetence(competence2);
		assertEquals("akcompetence2", senioriteSur.getCompetence().getAkCompetence());
	}
	
	@Test
	public void testGetDateDebut() {
		assertEquals(dateDebut, senioriteSur.getDateDebut());
	}
	
	@Test
	public void testSetDateDebut() {
		LocalDateTime dateDebutBefore = dateDebut.minusWeeks(12);
		senioriteSur.setDateDebut(dateDebutBefore);
		assertEquals(dateDebutBefore, senioriteSur.getDateDebut());
	}
	
	@Test
	public void testGetSeniorite() {
		assertTrue(senioriteSur.getSeniorite() == null);
		senioriteSur.initSeniorite();
		assertFalse(senioriteSur.getSeniorite().isEmpty());
	}
	
	@Test
	public void testSetSeniorite() {
		assertTrue(senioriteSur.getSeniorite() == null);
		
		senioriteSur.setSeniorite("une catégorie");
		assertTrue(senioriteSur.getSeniorite().equals("une catégorie"));
	}
	
}
