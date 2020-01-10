package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.notification.Notification;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.notification.Type;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests unitaires du domain collaborateur
 */
public class NotificationTest extends TestCase {

	private Notification notification = new Notification(Type.TOUS, null, "message");

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public NotificationTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(NotificationTest.class);
	}

	/**
	 * test du Constructeur Vide
	 */
	public void testConstructeurVide() {
		Notification notificationVide = new Notification();
		assertNull(notificationVide.getType());
		assertNull(notificationVide.getDestinataire());
		assertNull(notificationVide.getMessage());
	}
	
	/**
	 * test du Constructeur
	 */
	public void testConstructeur() {
		Notification notificationTest = new Notification(Type.PERSO, "ak_collaborateur", "message");
		
		assertEquals(notificationTest.getType(), Type.PERSO);
		assertEquals(notificationTest.getDestinataire(), "ak_collaborateur");
		assertEquals(notificationTest.getMessage(), "message");
	}

	/**
	 * test get du type
	 */
	public void testGetType() {
		assertEquals(notification.getType(), Type.TOUS);
	}

	/**
	 * test set du type
	 */
	public void testSetType() {
		assertEquals(notification.getType(), Type.TOUS);
		
		notification.setType(Type.PERSO);
		assertEquals(notification.getType(), Type.PERSO);
	}
	
	/**
	 * test get du destinataire
	 */
	public void testGetDestinataire() {
		assertNull(notification.getDestinataire());
	}

	/**
	 * test set du destinataire
	 */
	public void testSetDestinataire() {
		assertNull(notification.getDestinataire());
		
		notification.setDestinataire("destinataire");
		assertEquals(notification.getDestinataire(), "destinataire");
	}
	
	/**
	 * test get du message
	 */
	public void testGetMessage() {
		assertEquals(notification.getMessage(), "message");
	}

	/**
	 * test set du message
	 */
	public void testSetMessage() {
		assertEquals(notification.getMessage(), "message");
		
		notification.setMessage("un autre message");
		assertEquals(notification.getMessage(), "un autre message");
	}

}
