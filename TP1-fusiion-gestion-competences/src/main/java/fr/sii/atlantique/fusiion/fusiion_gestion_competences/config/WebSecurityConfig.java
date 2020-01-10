package fr.sii.atlantique.fusiion.fusiion_gestion_competences.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Classe de configuration établissant la marche à suivre lors de la reception d'une requete
 * @author Sébastien Vallée, François Hallereau, Esteban Launay et Sullivan Pineau
 *
 */
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	/**
	 * Constructeur, autowired
	 * @param authenticationFilter
	 */
	public WebSecurityConfig(JwtAuthenticationFilter authenticationFilter) {
		super();
		this.authenticationFilter = authenticationFilter;
	}
	
	/**
	 * CrossOrigin
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.cors()
		.and()
		.exceptionHandling().authenticationEntryPoint((request, response, e) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
		.and()
		.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.anyRequest().authenticated();
	}

	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        List<String> list2 = new ArrayList<>();
        list2.add("HEAD");
        list2.add("GET");
        list2.add("POST");
        list2.add("PUT");
        list2.add("DELETE");
        list2.add("PATCH");
        List<String> list3 = new ArrayList<>();
        list3.add("Authorization");
        list3.add("Cache-Control");
        list3.add("Content-Type");
        configuration.setAllowedOrigins(list);
        configuration.setAllowedMethods(list2);
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(list3);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

