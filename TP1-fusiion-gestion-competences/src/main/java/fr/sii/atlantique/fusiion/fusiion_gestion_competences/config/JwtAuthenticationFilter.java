package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.sii.atlantique.fusiion.fusiion_gestion_competences.models.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * Classe Component permettant de dechiffrer le HTTP header afin de verifier sa validité
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
@Component
class JwtAuthenticationFilter extends GenericFilterBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	// Cle de chiffrage
	@Value("${security.jwt.secret:secret}")
	private String secret;

	// signature du header
	@Value("${security.jwt.header.startWith:JWT}")
	private String headerStartWith;
	
	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Appel les fonctions de dechiffrage du token
	 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = getAuthentication((HttpServletRequest) request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
    
    /**
     * Lis la requette et recupere le Token
     * @param request
     * @return Les autorisations de la requette, 200 si ok et 401 sinon
     */
	private Authentication getAuthentication(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.filter(s -> s.startsWith(headerStartWith))
				.map(s -> s.replaceFirst(headerStartWith, ""))
				.map(String::trim)
				.map(this::parseToken)
				.map(person -> new UsernamePasswordAuthenticationToken(person, null, person.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())))
				.orElse(null);
	}
	
	/**
	 * Dechiffre et lis le token
	 * @param token
	 * @return La validité du token
	 */
	private User parseToken(String token) {
		try {
			String subject = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			return mapper.readValue(subject, User.class);
		} catch (ExpiredJwtException e) {
			LOGGER.warn("Error token expired", e);
		} catch (IOException e) {
			LOGGER.error("Error while parsing token", e);
		}
		return null;
	} 
    
}

