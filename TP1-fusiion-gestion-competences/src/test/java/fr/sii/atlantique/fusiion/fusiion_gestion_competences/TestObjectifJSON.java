package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.request_body.ObjectifJSON;

public class TestObjectifJSON {
	private ObjectifJSON objectifJSON = new ObjectifJSON();
	
	@Test 
	public void testConstructeurVide() {
		ObjectifJSON obj = new ObjectifJSON();
		assertEquals(null, obj.getNom());
	}
	
	@Test 
	public void testConstructeurDeuxParam() {
		ObjectifJSON obj = new ObjectifJSON("nom", "description");
		assertTrue(obj.getDescription().equals("description")
				&&obj.getNom().equals("nom"));
	}
	
	@Test 
	public void testConstructeurTroisParam() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("comp", 1);
		ObjectifJSON obj = new ObjectifJSON("nom", "description", map);
		assertTrue(obj.getDescription().equals("description")
				&&obj.getNom().equals("nom")
				&&obj.getCompetences().equals(map));
	}
	
	@Test 
	public void testConstructeurQuatreParam() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("comp", 1);
		ObjectifJSON obj = new ObjectifJSON("nom", "description","badge", "type", map);
		assertTrue(obj.getDescription().equals("description")
				&& obj.getNom().equals("nom")
				&& obj.getCompetences().equals(map)
				&& obj.getBadge().equals("badge")
				&& obj.getType().equals("type"));
	}
	
	@Test 
	public void testConstructeurSetNom() {
		objectifJSON.setNom("n");
		assertEquals("n",objectifJSON.getNom());
	}
	
	@Test 
	public void testConstructeurSetBadge() {
		objectifJSON.setBadge("b");
		assertEquals("b",objectifJSON.getBadge());
	}
	
	@Test 
	public void testConstructeurSetDescription() {
		objectifJSON.setDescription("d");
		assertEquals("d",objectifJSON.getDescription());
	}
	
	@Test 
	public void testConstructeurSetType() {
		objectifJSON.setType("JPG");
		assertEquals("JPG",objectifJSON.getType());
	}
	
	@Test 
	public void testConstructeurSetMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("comp", 1);
		objectifJSON.setCompetences(map);;
		assertEquals(map,objectifJSON.getCompetences());
	}
}
