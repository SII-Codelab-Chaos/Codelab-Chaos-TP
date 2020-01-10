package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import org.junit.Before;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.domain.Objectif;
import junit.framework.TestCase;

public class TestObjectif extends TestCase{
	
	private Objectif objectif;
	
	public TestObjectif(String name) {
		super(name);
	}
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		objectif = new Objectif("aktest", "test", "test_desc", "badge");
	}
	
	public void testConstructeurVide() {
		objectif = new Objectif();
		assertNull(objectif.getAkObjectif());
		assertNull(objectif.getNom());
		assertNull(objectif.getDescription());
		assertNull(objectif.getBadge());
	}

	public void testGetAkObjectif() {
		assertTrue(objectif.getAkObjectif().equals("aktest"));
	}

	public void testSetAkObjectif() {
		objectif.setAkObjectif("un autre ak");
		assertTrue(objectif.getAkObjectif().equals("un autre ak"));
	}

	public void testGetNom() {
		assertTrue(objectif.getNom().equals("test"));
	}

	public void testSetNom() {
		objectif.setNom("un autre nom");
		assertTrue(objectif.getNom().equals("un autre nom"));
	}

	public void testGetDescription() {
		assertTrue(objectif.getDescription().equals("test_desc"));
	}

	public void testSetDescription() {
		objectif.setDescription("une autre description");
		assertTrue(objectif.getDescription().equals("une autre description"));
	}

	public void testGetBadge() {
		assertTrue(objectif.getBadge().equals("badge"));
	}

	public void testSetBadge() {
		objectif.setBadge("un autre badge");
		assertTrue(objectif.getBadge().equals("un autre badge"));
	}

}
