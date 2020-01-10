package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Competence;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Tag;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.TagueSur;

public class TestTagueSur {

	private Tag tag = new Tag("ak_Tag", "nom_tag", "desc");
	private Competence competence = new Competence("ak_competence", "nom_cmp", "desc_comp", true, false);
	private TagueSur tagueSur = new TagueSur(tag, competence);
	
	@Test
	public void TestConstructeurVide() {
		TagueSur tagS = new TagueSur();
		assertEquals(null, tagS.getTag());
	}
	
	@Test
	public void testGetTag() {
		assertEquals(tag.getAkTag(), tagueSur.getTag().getAkTag());
	}
	
	@Test
	public void testSetTag() {
		tagueSur.setTag(new Tag("ak-test", "fds", "dfs"));
		assertEquals("ak-test", tagueSur.getTag().getAkTag());
	}
	
	@Test
	public void testGetCompetence() {
		assertEquals(competence.getAkCompetence(), tagueSur.getCompetence().getAkCompetence());
	}
	
	@Test
	public void testSetCompetence() {
		tagueSur.setCompetence(new Competence("test-ak", "fsd", "fsdf", true, true));
		assertEquals("test-ak", tagueSur.getCompetence().getAkCompetence());
	}
}
