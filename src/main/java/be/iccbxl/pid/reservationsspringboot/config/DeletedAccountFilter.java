package be.iccbxl.pid.reservationsspringboot.config;

import java.io.IOException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeletedAccountFilter extends OncePerRequestFilter{

  private final UserRepository userRepository;

  public DeletedAccountFilter(UserRepository userRepository){
    this.userRepository = userRepository;
  }
  
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain) 
    throws ServletException,IOException{

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if(auth != null 
    && auth.isAuthenticated()
    && !(auth instanceof AnonymousAuthenticationToken)
    && userRepository.findByLoginAndDeletedAtIsNull(auth.getName()) == null){

      new SecurityContextLogoutHandler()
      .logout(request, response, auth);
    

    response.sendRedirect(
      request.getContextPath() + "/login?accountDeleted=true"
    );

    return;
  } 

   filterChain.doFilter(request,response);
  }
}



