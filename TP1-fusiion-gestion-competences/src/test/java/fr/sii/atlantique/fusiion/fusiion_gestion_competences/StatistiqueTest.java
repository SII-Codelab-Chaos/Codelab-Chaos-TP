package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.DonneeStatistique;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.statistiques.Statistique;
import junit.framework.TestCase;

public class StatistiqueTest extends TestCase {
	
	private Statistique statistique;
	
	public StatistiqueTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		this.statistique = new Statistique("nomStatistique", "akStatistique", new ArrayList<DonneeStatistique>());
	}
	
	@Test
	public void testConstructeurVide() {
		Statistique stat = new Statistique();
		assertTrue(stat.getAkStatistique() == "" 
				&& stat.getNomStatistique() == null
				&& stat.getDonnees() == null);
	}
	
	@Test
	public void testSetAkStatistique() {
		this.statistique.setAkStatistique("autre");
		assertTrue(statistique.getAkStatistique() == "autre");
	}
	
	@Test
	public void testSetNomStatistique() {
		this.statistique.setNomStatistique("autre");
		assertTrue(statistique.getNomStatistique() == "autre");	
	}
	
	@Test
	public void testSetDonnees() {
		List<DonneeStatistique> list = new ArrayList<DonneeStatistique>();
		list.add(new DonneeStatistique("test","test"));
		this.statistique.setDonnees(list);
		assertTrue(statistique.getDonnees().equals(list));	
	}
}
