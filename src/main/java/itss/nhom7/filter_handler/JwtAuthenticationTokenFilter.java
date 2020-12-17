package itss.nhom7.filter_handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import itss.nhom7.jwt.JwtService;
import itss.nhom7.service.IUserService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter{
	
	private final static String TOKEN_HEADER = "Authorization";

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private IUserService userService;
	
	@SneakyThrows
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		
		if(jwtService.validateToken(authToken)) {
			
			String email = jwtService.getEmailToken(authToken);
			
			itss.nhom7.entities.User user = userService.findByEmail(email);
			
			if(user!=null) {
				
				boolean enable = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				UserDetails userDetail = new User(email, user.getPassword(),enable,accountNonExpired,credentialsNonExpired,accountNonExpired, user.getAuthorities());
			
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			
			}
			
		}
		
		chain.doFilter(request, response);
	}
}
