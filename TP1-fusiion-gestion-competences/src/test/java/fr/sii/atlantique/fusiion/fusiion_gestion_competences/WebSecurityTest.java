package fr.sii.atlantique.fusiion.fusiion_gestion_competences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.Roles;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.WebSecurity;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.CollaborateurAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.config.access.autorisations.DIOAutorisations;
import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.User;


@RunWith(SpringRunner.class)
public class WebSecurityTest {
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private User user;
	
	@InjectMocks
	private WebSecurity webSecurity;
	
	@Before
	public void setup() {
		when(authentication.getPrincipal()).thenReturn(user);
		when(user.getEmailSII()).thenReturn("test@sii.fr");
	}
	@Test
	public void estAuthoriseAdminTRUE() {
		when(user.getRoles()).thenReturn(Collections.singletonList("ADMIN"));

		assertTrue(webSecurity.estAuthorise(authentication));
	}
	
	@Test
	public void estAuthoriseAdminFALSE() {
		List<String> rolesUser = new ArrayList<>();
		rolesUser.add("DIO");
		rolesUser.add("HAHAHA");
		rolesUser.add("ADMI");
		
		when(user.getRoles()).thenReturn(rolesUser);

		assertFalse(webSecurity.estAuthorise(authentication));
	}
	
	////////////// DIO //////////////	
	@Test
	public void estAuthoriseDIO_COLLAB_RECHERCHER_PAR_COMPETENCE() {
		when(user.getRoles()).thenReturn(Collections.singletonList(Roles.DIO.getNom()));
		
		assertTrue(webSecurity.estAuthorise(authentication,DIOAutorisations.COLLAB_RECHERCHER_PAR_COMPETENCE.getNom(),DIOAutorisations.COLLAB_RECHERCHER_PAR_COMPETENCE.getRestController()));
	}
	
	@Test
	public void estAuthoriseDIO_COMP_COMPETENCES_UN_COLLABORATEUR() {
		when(user.getRoles()).thenReturn(Collections.singletonList(Roles.DIO.getNom()));
		
		assertTrue(webSecurity.estAuthorise(authentication,"test@sii.fr", DIOAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getNom(),DIOAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getRestController()));
		assertTrue(webSecurity.estAuthorise(authentication,"dio@sii.fr", DIOAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getNom(),DIOAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getRestController()));
	}
	
    //////////////COLLABORATEUR//////////////
	@Test
	public void estAuthoriseCOLLABORATEUR_COMP_COMPETENCES_UN_COLLABORATEUR() {
		when(user.getRoles()).thenReturn(Collections.singletonList(Roles.COLLABORATEUR.getNom()));
		
		
		assertFalse(webSecurity.estAuthorise(authentication,"dio@sii.fr", CollaborateurAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getNom(),CollaborateurAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getRestController()));
		assertTrue(webSecurity.estAuthorise(authentication,"test@sii.fr", CollaborateurAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getNom(),CollaborateurAutorisations.COMP_COMPETENCES_UN_COLLABORATEUR.getRestController()));
	}
	
	@Test
	public void estAuthoriseCOLLABORATEUR_COMP_GET_ALL() {
		when(user.getRoles()).thenReturn(Collections.singletonList(Roles.COLLABORATEUR.getNom()));
		
		assertTrue(webSecurity.estAuthorise(authentication, CollaborateurAutorisations.COMP_GET_ALL.getNom(),CollaborateurAutorisations.COMP_GET_ALL.getRestController()));
	}
}
