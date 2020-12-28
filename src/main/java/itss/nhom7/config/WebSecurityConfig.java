package itss.nhom7.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import itss.nhom7.filter_handler.CustomAccessDeniedHandler;
import itss.nhom7.filter_handler.JwtAuthenticationTokenFilter;
import itss.nhom7.filter_handler.RestAuthenticationEntryPoint;
import itss.nhom7.service.impl.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserService userService;
	
	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {

		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		//jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {

		return super.authenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests().antMatchers("/api/auth/**","/api/cart/**").permitAll();
		
		httpSecurity.csrf().ignoringAntMatchers("/api/**");
		httpSecurity.antMatcher("/api/**")
				.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()		
				.antMatchers(HttpMethod.GET,"/api/card/**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST,"/api/card/**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.PUT,"/api/card/**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.DELETE,"/api/card/**").access("hasRole('ROLE_USER')")
				
				.antMatchers(HttpMethod.GET,"/api/user/**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST,"/api/user/**").access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.PUT,"/api/user/**").access("hasRole('ROLE_USER')")
				
				.antMatchers(HttpMethod.GET,"/api/product/**").permitAll()
				
				.antMatchers(HttpMethod.GET,"/api/image/getImageAvatar/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
				.antMatchers(HttpMethod.POST,"/api/image/uploadImageAvatar/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
				.antMatchers(HttpMethod.GET,"/api/image/getImageProduct/**").permitAll()
				.antMatchers(HttpMethod.POST,"/api/image/uploadImageProduct/**").access("ROLE_ADMIN")
				
				.antMatchers(HttpMethod.POST,"/api/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.PUT,"/api/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers(HttpMethod.DELETE,"/api/admin/**").access("hasRole('ROLE_ADMIN')")
				
				.and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
}
