package com.storage.stockflow.security;

import com.storage.stockflow.domain.enums.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {

  @Serial
  private static final long serialVersionUID = 1L;
  private final Integer id;
  private final String name;
  private final String password;

  private final Collection<? extends GrantedAuthority> Authorities;

  public UserSS(Integer id, String name, String password, Set<Profile> profileSet) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.Authorities = profileSet.stream().map(x -> new SimpleGrantedAuthority(x.getDesc())).collect(Collectors.toSet());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Integer getId() {
    return id;
  }
}
