package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import java.util.HashMap;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.DonneeStatistique;
import junit.framework.TestCase;

public class DonneeStatistiqueTest extends TestCase {
	private DonneeStatistique donneeStatistique;
	
	public DonneeStatistiqueTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		this.donneeStatistique = new DonneeStatistique("nomDonnee", "akdonnee", new HashMap<String,Integer>());
	}
	
	@Test
	public void testConstructeurVide() {
		DonneeStatistique donnee = new DonneeStatistique();
		assertTrue(donnee.getAkDonnee() == "" 
				&& donnee.getNomDonnee() == ""
				&& donnee.getValeurs() == null);
	}

	@Test
	public void testSetAKStatistique() {
		this.donneeStatistique.setAkDonnee("un autre ak");
		assertEquals(this.donneeStatistique.getAkDonnee(), "un autre ak");
	}
	
	@Test
	public void testSetNomDonnee() {
		this.donneeStatistique.setNomDonnee("un autre nom");
		assertEquals(this.donneeStatistique.getNomDonnee(), "un autre nom");
	}
	
	@Test
	public void testAddDelValeur() {
		this.donneeStatistique.addDonnee("test", 1);
		assertTrue(this.donneeStatistique.getValeurs().get("test") == 1);
		this.donneeStatistique.delDonnee("test");
		assertTrue(this.donneeStatistique.getValeurs().get("test") == null);
	}
}
