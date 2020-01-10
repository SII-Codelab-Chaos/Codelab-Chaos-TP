package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Collaborateur;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.CompetentSur;

public class TestCompetentSur {

	private Collaborateur collaborateur;
	private Competence competence;
	private LocalDateTime date;
	private CompetentSur competentSur;
	
	@Before
	public void setup() {
		collaborateur = new Collaborateur("akcollab");
		competence = new Competence("akcompetence", "test", "desc", true, false);
		date = LocalDateTime.now();
		competentSur = new CompetentSur(collaborateur, competence, date, 2, true);
	}
	
	@Test
	public void testConstructeurVide() {
		CompetentSur cmp = new CompetentSur();
		assertEquals(null, cmp.getDate());
	}
	
	@Test
	public void testGetCollaborateur() {
		assertEquals(collaborateur, competentSur.getCollaborateur());
	}
	@Test
	public void testSetCollaborateur() {
		CompetentSur cmp2 = new CompetentSur(collaborateur, competence, date, 2, true);
		Collaborateur collab2 = new Collaborateur("akcollab2");
		cmp2.setCollaborateur(collab2);
		assertEquals(collab2, cmp2.getCollaborateur());
	}
	
	@Test
	public void testGetCompetence() {
		assertEquals(competence, competentSur.getCompetence());
	}
	@Test
	public void testSetCompetence() {
		CompetentSur cmp2 = new CompetentSur(collaborateur, competence, date, 2, true);
		Competence competence2 = new Competence("akcompetence2", "test", "desc", true, false);
		cmp2.setCompetence(competence2);
		assertEquals(competence2, cmp2.getCompetence());
	}
	
	@Test
	public void testGetDate() {
		assertEquals(date, competentSur.getDate());
	}
	@Test
	public void testSetDate() {
		LocalDateTime date2 = LocalDateTime.now();
		CompetentSur cmp2 = new CompetentSur(collaborateur, competence, date, 2, true);
		cmp2.setDate(date2);
		assertEquals(date2, cmp2.getDate());
	}
	
	@Test
	public void testGetNotation() {
		assertEquals(2, competentSur.getNotation());
	}
	@Test
	public void testSetNotation() {
		CompetentSur cmp2 = new CompetentSur(collaborateur, competence, date, 2, true);
		cmp2.setNotation(3);
		assertEquals(3, cmp2.getNotation());
	}
	
	@Test
	public void testGetCertification() {
		assertEquals(true, competentSur.getCertification());
	}
	@Test
	public void testSetCertification() {
		CompetentSur cmp2 = new CompetentSur(collaborateur, competence, date, 2, true);
		cmp2.setCertification(false);
		assertEquals(false, cmp2.getCertification());
	}
	
	@Test
	public void TestCompareToSup() {
		CompetentSur competentSurBis = new CompetentSur(
				competentSur.getCollaborateur()
				,competentSur.getCompetence()
				,competentSur.getDate().plusDays(1)
				,competentSur.getNotation()
				,competentSur.getCertification()
			);
		
		assertTrue(competentSur.compareTo(competentSurBis)>0);
	}
	
	@Test
	public void TestCompareToInf() {
		CompetentSur competentSurBis = new CompetentSur(
				competentSur.getCollaborateur()
				,competentSur.getCompetence()
				,competentSur.getDate()
				,competentSur.getNotation()
				,competentSur.getCertification()
			);
		competentSur.setDate(competentSur.getDate().plusDays(1));
		
		assertTrue(competentSur.compareTo(competentSurBis)<0);
	}
	
	@Test
	public void TestCompareToEquals() {
		Collaborateur collaborateur2 = new Collaborateur("un autre ak");
		CompetentSur competentSurBis = new CompetentSur(
				collaborateur2
				,competentSur.getCompetence()
				,competentSur.getDate()
				,competentSur.getNotation()
				,competentSur.getCertification()
			);
		
		assertEquals(0,competentSur.compareTo(competentSurBis));
	}
	
	@Test
	public void testToString() {
			assertEquals("CompetentSur [collaborateur=" + collaborateur.toString() + ", competence=" + competence.toString() + ", date="
					+ date.toString() + ", notation=" + 2 + ", certification=" + true + "]", competentSur.toString());
	}
}

