package be.iccbxl.pid.reservationsspringboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.Role;
import be.iccbxl.pid.reservationsspringboot.model.User;

import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    System.out.println("Connexion demandée pour : " + username);
    final User user = userRepository.findByLoginAndDeletedAtIsNull(username);

    if (user == null) {
      throw new UsernameNotFoundException("User " + username + " not found.");
    }

    return new org.springframework.security.core.userdetails.User(
        username,
        user.getPassword(),
        getGrantedAuthorities(user.getRoles()));

  }

  private List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {

    List<GrantedAuthority> authorities = new ArrayList<>();

    for(Role role : roles){
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
    }

    return authorities;

  }

}
