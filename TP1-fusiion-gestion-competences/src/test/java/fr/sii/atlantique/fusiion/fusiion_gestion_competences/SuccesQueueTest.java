package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import org.junit.Before;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.succes.SuccesQueue;
import junit.framework.TestCase;

public class SuccesQueueTest extends TestCase {

	private SuccesQueue succesQueue;
	
	public SuccesQueueTest(String name) {
		super(name);
	}
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		this.succesQueue = new SuccesQueue("akCollaborateur", "akSucces",  1);
	}

	public void testConstructeurVide() {
		SuccesQueue succesQueue = new SuccesQueue();
		assertTrue(succesQueue.getAkCollaborateur() == null 
				&& succesQueue.getAkSucces() == null
				&& succesQueue.getValue() == 0);
	}
	
	public void testConstructeur() {
		assertTrue(this.succesQueue.getAkCollaborateur() == "akCollaborateur" 
				&& this.succesQueue.getAkSucces() == "akSucces"
				&& this.succesQueue.getValue() == 1);
	}
	
	public void testGetAKCollaborateur() {
		assertEquals(this.succesQueue.getAkCollaborateur(), "akCollaborateur");
	}
	
	public void testSetAKCollaborateur() {
		this.succesQueue.setAkCollaborateur("un autre ak");
		assertEquals(this.succesQueue.getAkCollaborateur(), "un autre ak");
	}
	
	public void testGetAkSucces() {
		assertEquals(this.succesQueue.getAkSucces(), "akSucces");
	}
	
	public void testSetAkSucces() {
		this.succesQueue.setAkSucces("un autre ak");
		assertEquals(this.succesQueue.getAkSucces(), "un autre ak");
	}	
	
	public void testSetValue() {
		assertEquals(this.succesQueue.getValue(), 1);
	}
	
	public void testGetValue() {
		this.succesQueue.setValue(5);
		assertEquals(this.succesQueue.getValue(), 5);
	}	


}
