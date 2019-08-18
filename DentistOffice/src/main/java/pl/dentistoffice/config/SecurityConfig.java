package pl.dentistoffice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
		.authoritiesByUsernameQuery("SELECT username, role FROM users_role INNER JOIN users ON users_role.users_id = users.id INNER JOIN role ON users_role.roles_id = role.id WHERE username=?")
		.passwordEncoder(new BCryptPasswordEncoder());
//		.and()
//		.inMemoryAuthentication()
//		.passwordEncoder(new BCryptPasswordEncoder())
//		.withUser("owner")
//		.password(new BCryptPasswordEncoder().encode("password"))
//		.roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.mvcMatchers("/message/patient/*", "/users/patient/edit", "/visit/patient/*").hasRole("PATIENT")
		.mvcMatchers("/control/*", "/panels/admin*", "/users/admin/edit", "/users/assistant/admin/*", "/users/doctor/admin/*").hasRole("ADMIN")
		.mvcMatchers("/panels/doctor*", "/users/doctor/edit", "/users/doctor/*Patient", "/visit/doctor/*").hasRole("DOCTOR")
		.mvcMatchers("/panels/assistant*", "/users/assistant/edit", "/users/patient/assistant/*", "/visit/assistant/*").hasRole("ASSISTANT")
		.mvcMatchers("/users/admin/owner/*").hasRole("OWNER")
		.mvcMatchers("/message/employee/*", "/panels/employee*").hasAnyRole("ADMIN", "DOCTOR", "ASSISTANT", "OWNER")
		.mvcMatchers("/loginSuccess").hasAnyRole("PATIENT", "ADMIN", "DOCTOR", "ASSISTANT", "OWNER")
		.and()
		.formLogin().loginPage("/login").defaultSuccessUrl("/loginSuccess").failureUrl("/login?error")
		.and()
		.logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
		.and()
		.exceptionHandling().accessDeniedPage("/403");
	}

	
}
