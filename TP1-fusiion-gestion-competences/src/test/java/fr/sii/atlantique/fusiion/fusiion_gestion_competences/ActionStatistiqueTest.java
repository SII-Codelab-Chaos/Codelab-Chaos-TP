package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.ActionStatistique;
import junit.framework.TestCase;

public class ActionStatistiqueTest extends TestCase {
	
	private ActionStatistique actionStatistique;
	
	public ActionStatistiqueTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		this.actionStatistique = new ActionStatistique("akStatistique", "NOM_ACTION");
	}
	
	@Test
	public void testConstructeurVide() {
		ActionStatistique act = new ActionStatistique();
		assertTrue(act.getAkStatistique() == null 
				&& act.getTypeAction() == null);
	}
	
	@Test
	public void testGetAKStatistique() {
		assertEquals(this.actionStatistique.getAkStatistique(), "akStatistique");
	}
	
	@Test
	public void testSetAKStatistique() {
		this.actionStatistique.setAkStatistique("un autre ak");
		assertEquals(this.actionStatistique.getAkStatistique(), "un autre ak");
	}
	
	@Test
	public void testGetTypeAction() {
		assertEquals(this.actionStatistique.getTypeAction(), "NOM_ACTION");
	}
	
	@Test
	public void testSetTypeAction() {
		this.actionStatistique.setTypeAction("un autre type");
		assertEquals(this.actionStatistique.getTypeAction(), "un autre type");
	}
	
	@Test
	public void testToString() {
		ActionStatistique act = new ActionStatistique("ak","act");
		assertTrue(act.toString().equals("Action [akStatistique=ak, typeAction=act]"));
	}
}
