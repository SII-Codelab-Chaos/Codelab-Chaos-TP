package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.CompetentSurJSON;

public class TestCompetenceSurJSON {

	private CompetentSurJSON competentSurJSON = new CompetentSurJSON("akcmp", 2, null);
	
	@Test
	public void testConstructeurVide() {
		CompetentSurJSON cmp = new CompetentSurJSON();
		assertEquals(null, cmp.getAkCompetence());
	}
	
	@Test
	public void testGetAkCmp() {
		assertEquals("akcmp", competentSurJSON.getAkCompetence());
	}
	
	@Test
	public void testSetAkCmp() {
		CompetentSurJSON competentSurLight2 = new CompetentSurJSON("akcmp", 2, null);
		competentSurLight2.setAkCompetence("akcomp2");
		assertEquals("akcomp2", competentSurLight2.getAkCompetence());
	}
	
	@Test
	public void testGetNotation() {
		assertEquals(2, competentSurJSON.getNotation());
	}
	@Test
	public void testSetNotation() {
		CompetentSurJSON competentSurLight2 = new CompetentSurJSON("akcmp", 2, null);
		competentSurLight2.setNotation(1);
		assertEquals(1, competentSurLight2.getNotation());
	}
	
	@Test
	public void testToString() {
		assertEquals("CompetentSurJSON [akCompetence=" + "akcmp" +", notation=" + 2 + "]", competentSurJSON.toString());
	}
}
