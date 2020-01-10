package fr.sii.atlantique.fusiion.fusiion_gestion_competences.notification;

/**
 * Classe représentant les notifications
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public class Notification {
	
	private Type type;
	private String destinataire;
	
	private String message;
	
	/**
	 * constructeur vide
	 */
	public Notification() {
		super();
	}
	
	/**
	 * constructeur de la classe Notification
	 * @param type, le type de la notification
	 * @param destinataire, le destinataire, si besoin, de la notification
	 * @param message, le message de la notification
	 */
	public Notification(Type type, String destinataire, String message) {
		super();
		this.type = type;
		this.destinataire = destinataire;
		this.message = message;
	}
	
	/**
	 * getter du message
	 * @return String,  le message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * setter du message
	 * @param name, le nouveau message
	 */
	public void setMessage(String name) {
		this.message = name;
	}
	
	/**
	 * le type
	 * @return Type, le type
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * setter du type
	 * @param type, le nouveau type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * getter du destinataire
	 * @return String, le destinataire
	 */
	public String getDestinataire() {
		return destinataire;
	}
	
	/**
	 * setter du destinataire
	 * @param destinataire, le nouveau destinataire
	 */
	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}
	
	/**
	 * permet de retourner sous forme de chaine de caractère la notification
	 */
	@Override
	public String toString() {
		return "Notification [type=" + type + ", destinataire=" + destinataire + ", message=" + message + "]";
	}
	
}
