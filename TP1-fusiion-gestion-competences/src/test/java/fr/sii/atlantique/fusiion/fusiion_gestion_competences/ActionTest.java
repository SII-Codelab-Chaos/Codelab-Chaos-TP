package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes.Action;
import junit.framework.TestCase;

public class ActionTest extends TestCase {

	private Action action;
	
	public ActionTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		this.action = new Action("akCollaborateur", "NOM_ACTION");
	}

	public void testConstructeurVide() {
		Action succesQueue = new Action();
		assertTrue(succesQueue.getAkCollaborateur() == null 
				&& succesQueue.getTypeAction() == null);
	}
	
	public void testConstructeur() {
		Action action = new Action();
		assertTrue(action.getAkCollaborateur() == null 
				&& action.getTypeAction() == null);
	}
	
	public void testGetAKCollaborateur() {
		assertEquals(this.action.getAkCollaborateur(), "akCollaborateur");
	}
	
	public void testSetAKCollaborateur() {
		this.action.setAkCollaborateur("un autre ak");
		assertEquals(this.action.getAkCollaborateur(), "un autre ak");
	}
	
	public void testGetTypeAction() {
		assertEquals(this.action.getTypeAction(), "NOM_ACTION");
	}

	public void testSetTypeAction(String typeAction) {
		this.action.setTypeAction("un autre type");
		assertEquals(this.action.getTypeAction(), "un autre type");
	}
	
}
