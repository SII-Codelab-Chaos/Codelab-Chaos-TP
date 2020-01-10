package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Tag;

public class TestTag {

	private Tag tag;
	
	@Before
	public void setUp() throws Exception {
		this.tag = new Tag("ak_test", "tag", "desc");
	}

	public void testConstructeurVide() {
		Tag tagVide = new Tag();
		boolean akVide = tagVide.getAkTag().equals("");
		assertTrue(akVide);
	}
	
	@Test
	public void testGetAkCompetence() {
		assertEquals("ak_test", tag.getAkTag());
	}
	
	@Test
	public void testSetAkCompetence() {
		Tag tag2= new Tag("ak_test", "tag", "desc");
		tag2.setAkTag("testAk2");
		assertEquals("testAk2", tag2.getAkTag());
	}
	
	@Test
	public void testGetNom() {
		assertEquals("tag", tag.getNom());
	}
	
	@Test
	public void testSetNom() {
		Tag tag2= new Tag("ak_test", "tag", "desc");
		tag2.setNom("testAk2");
		assertEquals("testAk2", tag2.getNom());
	}

	@Test
	public void testGetDescription() {
		assertEquals("desc", tag.getDescription());
	}
	
	@Test
	public void testSetDescription() {
		Tag tag2= new Tag("ak_test", "tag", "desc");
		tag2.setDescription("testAk2");
		assertEquals("testAk2", tag2.getDescription());
	}

}
