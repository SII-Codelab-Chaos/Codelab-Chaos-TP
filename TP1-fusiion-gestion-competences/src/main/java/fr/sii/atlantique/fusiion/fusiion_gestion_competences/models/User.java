package fr.sii.atlantique.fusiion.fusiion_gestion_competences.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Classe d'autentification des utilisateurs
 * 
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5960994424694501708L;

	private String username;

    private String emailSII;

    private String firstName;

    private String lastName;

    private String token;

    private List<String> roles = new ArrayList<>();

    private long timeToLive;
    
    
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public String getEmailSII() {
		return emailSII;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmailSII(String emailSII) {
		this.emailSII = emailSII;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", emailSII=" + emailSII + ", firstName=" + firstName + ", lastName="
				+ lastName + ", roles=" + roles + "]";
	}
	
    
    
}
