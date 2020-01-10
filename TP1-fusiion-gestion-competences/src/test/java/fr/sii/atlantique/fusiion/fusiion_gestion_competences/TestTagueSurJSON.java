package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.TagueSurJSON;

public class TestTagueSurJSON {

	private TagueSurJSON tagueSurJson = new TagueSurJSON("ak_test");
	
	@Test
	public void testConstructeurVide() {
		TagueSurJSON cmp = new TagueSurJSON();
		assertEquals(null, cmp.getAkTag());
	
	}
	
	@Test
	public void testGetAkTag() {
		assertEquals("ak_test", tagueSurJson.getAkTag());
	}
	
	@Test
	public void testSetAkTag() {
		tagueSurJson.setAkTag("test");
		assertEquals("test", tagueSurJson.getAkTag());
	}

}
